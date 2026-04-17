package com.sdr.ams.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class DetailPdfReportService {

    public byte[] buildEntityDetailPdf(String entityLabel, Object entity) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph(entityLabel + " Detail Report").setBold().setFontSize(16));
            document.add(new Paragraph("Generated at: " + Instant.now()).setFontSize(10));
            document.add(new Paragraph(" "));

            Table table = new Table(UnitValue.createPercentArray(new float[] { 32f, 68f })).useAllAvailableWidth();
            table.addHeaderCell(new Cell().add(new Paragraph("Field").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Value").setBold()));

            for (Field field : resolveFields(entity.getClass())) {
                Object value = readField(field, entity);
                table.addCell(new Cell().add(new Paragraph(humanize(field.getName()))));
                table.addCell(new Cell().add(new Paragraph(formatValue(value))));
            }

            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to generate PDF for " + entityLabel, ex);
        }
    }

    private List<Field> resolveFields(Class<?> type) {
        List<Class<?>> hierarchy = new ArrayList<>();
        Class<?> current = type;
        while (current != null && current != Object.class) {
            hierarchy.add(0, current);
            current = current.getSuperclass();
        }

        List<Field> fields = new ArrayList<>();
        for (Class<?> clazz : hierarchy) {
            for (Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                fields.add(f);
            }
        }
        return fields;
    }

    private Object readField(Field field, Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException ex) {
            return null;
        }
    }

    private String formatValue(Object value) {
        if (value == null) {
            return "-";
        }

        if (value instanceof String
            || value instanceof Number
            || value instanceof Boolean
            || value instanceof Enum<?>
            || value instanceof LocalDate
            || value instanceof LocalDateTime
            || value instanceof Instant
            || value instanceof OffsetDateTime
            || value instanceof ZonedDateTime) {
            return String.valueOf(value);
        }

        if (value instanceof Collection<?> collection) {
            if (collection.isEmpty()) {
                return "[]";
            }
            return "[" + collection.size() + " items]";
        }

        if (value instanceof Map<?, ?> map) {
            if (map.isEmpty()) {
                return "{}";
            }
            return "{" + map.size() + " entries}";
        }

        // For nested value objects, summarize simple fields.
        String nested = summarizeSimpleFields(value);
        if (!nested.isBlank()) {
            return nested;
        }

        return String.valueOf(value);
    }

    private String summarizeSimpleFields(Object nested) {
        List<String> parts = new ArrayList<>();
        for (Field f : nested.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                Object val = f.get(nested);
                if (val == null) {
                    continue;
                }
                if (val instanceof String || val instanceof Number || val instanceof Boolean || val instanceof Enum<?> || val instanceof LocalDate) {
                    parts.add(humanize(f.getName()) + ": " + val);
                }
            } catch (IllegalAccessException ignored) {
                // Best-effort summarization only.
            }
        }
        return String.join(", ", parts);
    }

    private String humanize(String name) {
        if (name == null || name.isBlank()) {
            return "Field";
        }
        String spaced = name.replaceAll("([A-Z])", " $1").trim();
        return spaced.substring(0, 1).toUpperCase(Locale.ROOT) + spaced.substring(1);
    }
}


