package com.sdr.ams.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("unused")
@ConfigurationProperties(prefix = "app.demo-data")
public class DemoDataProperties {

    private int recordCount = 25;
    private Map<String, Integer> maxRecords = new LinkedHashMap<>();

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public Map<String, Integer> getMaxRecords() {
        return maxRecords;
    }

    public void setMaxRecords(Map<String, Integer> maxRecords) {
        this.maxRecords = maxRecords == null ? new LinkedHashMap<>() : new LinkedHashMap<>(maxRecords);
    }

    public int recordCountFor(String entityKey) {
        Integer configuredValue = Objects.requireNonNullElse(maxRecords.get(entityKey), recordCount);
        return Math.max(0, configuredValue);
    }
}

