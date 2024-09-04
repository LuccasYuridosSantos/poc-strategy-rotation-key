package com.luccas.poc_rotation_key.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EncryptedData {
    private String version;
    private Long timestamp;
}
