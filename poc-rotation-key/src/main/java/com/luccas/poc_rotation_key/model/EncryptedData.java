package com.luccas.poc_rotation_key.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncryptedData {
    private String version;
    private Long timestamp;
}
