package com.sdr.ams.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Generic export service. Uses reflection to enumerate simple fields from any entity list.
 * Skips collections, nested entities, and byte arrays. Supports CSV and Excel (XLSX).
 */
@Service
public class ExportService {

    // Types considered "simple" enough to export as plain text
    private static final Set<Class<?>> SIMPLE_TYPES = Set.of(
        String.class, Boolean.class, boolean.class,
        Byte.class, byte.class, Short.class, short.class,
        Integer.class, int.class, Long.class, long.class,
        Float.class, float.class, Double.class, double.class
    );

    // ── Public API ────────────────────────────────────────────────────────────

    public byte[] toCsv(List<?> items, String entityName) {
        if (items == null || items.isEmpty()) {
            return ("# No " + entityName + " records found\n").getBytes();
        }
        List<Field> fields = resolveFields(items.get(0).getClass());
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append(buildCsvRow(fields.stream().map(f -> humanize(f.getName())).toList()));

        // Rows
        for (Object item : items) {
            sb.append(buildCsvRow(fields.stream().map(f -> extractValue(f, item)).toList()));
        }
        return sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    public byte[] toExcel(List<?> items, String entityName) throws IOException {
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet(entityName);

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            CellStyle dateStyle = wb.createCellStyle();
            CreationHelper ch = wb.getCreationHelper();
            dateStyle.setDataFormat(ch.createDataFormat().getFormat("yyyy-mm-dd hh:mm"));

            if (items == null || items.isEmpty()) {
                Row r = sheet.createRow(0);
                r.createCell(0).setCellValue("No records found");
                try (ByteArrayOutputStream out = new ByteArrayOutputStream()) { wb.write(out); return out.toByteArray(); }
            }

            List<Field> fields = resolveFields(items.get(0).getClass());

            // Header row
            Row header = sheet.createRow(0);
            for (int i = 0; i < fields.size(); i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(humanize(fields.get(i).getName()));
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowNum = 1;
            for (Object item : items) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < fields.size(); i++) {
                    Object val = getRawValue(fields.get(i), item);
                    Cell cell = row.createCell(i);
                    if (val == null) {
                        cell.setCellValue("");
                    } else if (val instanceof Number n) {
                        cell.setCellValue(n.doubleValue());
                    } else if (val instanceof Boolean b) {
                        cell.setCellValue(b);
                    } else {
                        cell.setCellValue(String.valueOf(val));
                    }
                }
            }

            // Auto-size first 20 columns (POI can be slow on wide sheets)
            for (int i = 0; i < Math.min(fields.size(), 20); i++) {
                sheet.autoSizeColumn(i);
            }

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                wb.write(out);
                return out.toByteArray();
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Collect all declared fields from the class hierarchy (up to Object), simple types only. */
    private List<Field> resolveFields(Class<?> clazz) {
        List<Field> result = new ArrayList<>();
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            for (Field f : current.getDeclaredFields()) {
                if (isExportable(f)) {
                    f.setAccessible(true);
                    result.add(f);
                }
            }
            current = current.getSuperclass();
        }
        return result;
    }

    private boolean isExportable(Field f) {
        Class<?> t = f.getType();
        if (t.isArray() || Collection.class.isAssignableFrom(t) || Map.class.isAssignableFrom(t)) return false;
        if (SIMPLE_TYPES.contains(t)) return true;
        if (t.isEnum()) return true;
        if (Number.class.isAssignableFrom(t)) return true; // BigDecimal etc.
        if (t == LocalDate.class || t == LocalDateTime.class || t == Instant.class) return true;
        if (t == java.util.Date.class) return true;
        return false;
    }

    private String extractValue(Field f, Object obj) {
        Object raw = getRawValue(f, obj);
        return raw == null ? "" : String.valueOf(raw);
    }

    private Object getRawValue(Field f, Object obj) {
        try {
            return f.get(obj);
        } catch (IllegalAccessException e) {
            return null;
        }
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

    /** Convert camelCase field name to "Human Readable" label. */
    private String humanize(String name) {
        String spaced = name.replaceAll("([A-Z])", " $1").trim();
        return Character.toUpperCase(spaced.charAt(0)) + spaced.substring(1);
    }
}

