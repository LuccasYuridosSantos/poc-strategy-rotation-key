package com.luccas.poc_rotation_key.service;

import com.luccas.poc_rotation_key.properties.KeyVersionProperties;
import org.springframework.stereotype.Service;

@Service
public class KeyVersionManager {

    private final KeyVersionProperties keyVersionProperties;

    public KeyVersionManager(final KeyVersionProperties keyVersionProperties) {
        this.keyVersionProperties = keyVersionProperties;
    }

    public String getCurrentKeyVersion() {
        return keyVersionProperties.getActiveKeyVersion();
    }

    public String getCurrentKey() {
        return keyVersionProperties.getKeys().get(keyVersionProperties.getActiveKeyVersion());
    }

    public String getSpecificKey(String keyVersion) {
        return keyVersionProperties.getKeys().get(keyVersion);
    }
}
