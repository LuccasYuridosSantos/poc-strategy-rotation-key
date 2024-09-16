package com.luccas.schedulerpii.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class EncryptedData {
    private String version;
    private Long timestamp;

    public EncryptedData() {
    }

    public EncryptedData(final String version,
                         final Long timestamp) {
        this.version = version;
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = timestamp;
    }
}
