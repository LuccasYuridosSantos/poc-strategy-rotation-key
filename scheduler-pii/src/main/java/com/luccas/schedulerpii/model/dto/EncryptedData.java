package com.luccas.schedulerpii.model.dto;

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
