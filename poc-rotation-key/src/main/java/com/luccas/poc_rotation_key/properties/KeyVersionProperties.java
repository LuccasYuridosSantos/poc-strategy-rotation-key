package com.luccas.poc_rotation_key.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Data
@Configuration
@ConfigurationProperties(prefix = "encryption")
public class KeyVersionProperties {
    private HashMap<String,String> keys;
    private String activeKeyVersion;
}