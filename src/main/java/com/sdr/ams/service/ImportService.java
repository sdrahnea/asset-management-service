package com.sdr.ams.service;

import com.sdr.ams.model.core.ImportRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Handles CSV/Excel template generation and file parsing for bulk imports.
 * Uses the same field reflection strategy as ExportService.
 */
@Service
public class ImportService {

    private static final Set<String> SKIPPED_FIELDS = Set.of(
        "id", "createdAt", "createdBy", "updatedAt", "updatedBy"
    );

    private static final Set<Class<?>> IMPORTABLE_SCALAR = Set.of(
        String.class,
        Boolean.class, boolean.class,
        Integer.class, int.class,
        Long.class, long.class,
        Double.class, double.class,
        Float.class, float.class
    );

    // ── Template generation ────────────────────────────────────────────────────

    public byte[] generateTemplateCsv(Class<?> entityClass) {
        return generateTemplateCsv(buildHeaders(entityClass));
    }

    public byte[] generateTemplateCsv(List<String> headers) {
        StringBuilder sb = new StringBuilder();
        sb.append(buildCsvRow(headers));
        sb.append(buildCsvRow(Collections.nCopies(headers.size(), "")));
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public byte[] generateTemplateExcel(Class<?> entityClass, String entityName) throws IOException {
        return generateTemplateExcel(buildHeaders(entityClass), entityName);
    }

    public byte[] generateTemplateExcel(List<String> headers, String entityName) throws IOException {
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet(entityName);
            CellStyle headerStyle = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers.get(i));
                cell.setCellStyle(headerStyle);
            }
            // One empty example row
            sheet.createRow(1);
            for (int i = 0; i < Math.min(headers.size(), 30); i++) {
                sheet.autoSizeColumn(i);
            }
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                wb.write(out);
                return out.toByteArray();
            }
        }
    }

    // ── File parsing ──────────────────────────────────────────────────────────

    /**
     * Parses a CSV or Excel (.xlsx) file into a list of ImportRow objects.
     * The first row must be a header row whose column names match those produced by
     * {@link #generateTemplateCsv}.
     */
    public List<ImportRow> parseFile(MultipartFile file) throws IOException {
        String filename = Objects.requireNonNullElse(file.getOriginalFilename(), "").toLowerCase();
        if (filename.endsWith(".xlsx") || filename.endsWith(".xls")) {
            return parseExcel(file);
        }
        return parseCsv(file);
    }

    private List<ImportRow> parseCsv(MultipartFile file) throws IOException {
        List<ImportRow> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String headerLine = reader.readLine();
            if (headerLine == null) return rows;
            List<String> headers = splitCsvLine(headerLine);

            String line;
            int rowNum = 2;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) { rowNum++; continue; }
                List<String> values = splitCsvLine(line);
                Map<String, String> data = new LinkedHashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    data.put(headers.get(i).trim(), i < values.size() ? values.get(i).trim() : "");
                }
                rows.add(new ImportRow(rowNum++, data));
            }
        }
        return rows;
    }

    private List<ImportRow> parseExcel(MultipartFile file) throws IOException {
        List<ImportRow> rows = new ArrayList<>();
        try (Workbook wb = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = wb.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) return rows;

            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue().trim());
            }

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                Map<String, String> data = new LinkedHashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    data.put(headers.get(i), cell == null ? "" : getCellString(cell));
                }
                rows.add(new ImportRow(r + 1, data));
            }
        }
        return rows;
    }

    // ── Entity instantiation ──────────────────────────────────────────────────

    /**
     * Instantiates an entity of type T from a raw row map.
     * Column names in the map must match the humanized field names (as in template headers).
     * Returns the entity, or throws RuntimeException if instantiation fails.
     */
    public <T> T instantiate(Class<T> entityClass, Map<String, String> row) {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            Map<String, Field> fieldMap = buildFieldMap(entityClass);

            for (Map.Entry<String, String> entry : row.entrySet()) {
                String colName = entry.getKey().trim().toLowerCase();
                String rawValue = entry.getValue() == null ? "" : entry.getValue().trim();
                Field field = fieldMap.get(colName);
                if (field == null || rawValue.isEmpty()) continue;

                try {
                    Object converted = convert(rawValue, field.getType());
                    if (converted != null) {
                        field.set(entity, converted);
                    }
                } catch (Exception ignored) {
                    // Skip fields that can't be converted - validation will catch required fields
                }
            }
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate entity: " + e.getMessage(), e);
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    public List<String> buildHeaders(Class<?> entityClass) {
        return resolveFields(entityClass).stream()
            .map(f -> humanize(f.getName()))
            .toList();
    }

    private Map<String, Field> buildFieldMap(Class<?> entityClass) {
        Map<String, Field> result = new LinkedHashMap<>();
        for (Field f : resolveFields(entityClass)) {
            result.put(humanize(f.getName()).toLowerCase(), f);
        }
        return result;
    }

    private List<Field> resolveFields(Class<?> clazz) {
        List<Field> result = new ArrayList<>();
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            for (Field f : current.getDeclaredFields()) {
                if (isImportable(f)) {
                    f.setAccessible(true);
                    result.add(f);
                }
            }
            current = current.getSuperclass();
        }
        return result;
    }

    private boolean isImportable(Field f) {
        if (SKIPPED_FIELDS.contains(f.getName())) return false;
        Class<?> t = f.getType();
        if (t.isArray() || Collection.class.isAssignableFrom(t) || Map.class.isAssignableFrom(t)) return false;
        if (IMPORTABLE_SCALAR.contains(t)) return true;
        if (t.isEnum()) return true;
        if (Number.class.isAssignableFrom(t)) return true;
        if (t == LocalDate.class || t == LocalDateTime.class) return true;
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object convert(String value, Class<?> type) {
        if (value == null || value.isBlank()) return null;
        if (type == String.class) return value;
        if (type == Boolean.class || type == boolean.class) return Boolean.parseBoolean(value);
        if (type == Integer.class || type == int.class) return Integer.parseInt(value);
        if (type == Long.class || type == long.class) return Long.parseLong(value);
        if (type == Double.class || type == double.class) return Double.parseDouble(value);
        if (type == Float.class || type == float.class) return Float.parseFloat(value);
        if (type == BigDecimal.class) return new BigDecimal(value);
        if (type == LocalDate.class) return LocalDate.parse(value);
        if (type == LocalDateTime.class) return LocalDateTime.parse(value);
        if (type.isEnum()) {
            try {
                return Enum.valueOf((Class<Enum>) type, value.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    private String humanize(String name) {
        String spaced = name.replaceAll("([A-Z])", " $1").trim();
        return Character.toUpperCase(spaced.charAt(0)) + spaced.substring(1);
    }

    private String buildCsvRow(List<String> values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) sb.append(',');
            String v = values.get(i) == null ? "" : values.get(i);
            if (v.contains(",") || v.contains("\"") || v.contains("\n")) {
                sb.append('"').append(v.replace("\"", "\"\"")).append('"');
            } else {
                sb.append(v);
            }
        }
        sb.append('\n');
        return sb.toString();
    }

    private List<String> splitCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        return result;
    }

    private String getCellString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getLocalDateTimeCellValue().toLocalDate().toString();
                }
                double d = cell.getNumericCellValue();
                if (d == Math.floor(d) && !Double.isInfinite(d)) {
                    yield String.valueOf((long) d);
                }
                yield String.valueOf(d);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }
}

