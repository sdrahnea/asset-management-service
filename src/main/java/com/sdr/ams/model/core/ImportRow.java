package com.sdr.ams.model.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a single parsed row from a bulk import file.
 */
public class ImportRow {

    private final int rowNumber;
    private final Map<String, String> data; // column-name -> raw-string value
    private String error; // null = valid

    public ImportRow(int rowNumber, Map<String, String> data) {
        this.rowNumber = rowNumber;
        this.data = new LinkedHashMap<>(data);
    }

    public int getRowNumber() { return rowNumber; }
    public Map<String, String> getData() { return data; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public boolean isValid() { return error == null; }
}

