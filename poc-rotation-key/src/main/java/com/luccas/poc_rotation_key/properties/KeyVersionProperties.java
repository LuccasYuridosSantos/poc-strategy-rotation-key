package com.luccas.poc_rotation_key.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@ConfigurationProperties(prefix = "encryption")
public class KeyVersionProperties {
    private HashMap<String,String> keys;
    private String activeKeyVersion;

    public KeyVersionProperties() {
    }

    public KeyVersionProperties(final HashMap<String, String> keys,
                                final String activeKeyVersion) {
        this.keys = keys;
        this.activeKeyVersion = activeKeyVersion;
    }

    public HashMap<String, String> getKeys() {
        return keys;
    }

    public void setKeys(final HashMap<String, String> keys) {
        this.keys = keys;
    }

    public String getActiveKeyVersion() {
        return activeKeyVersion;
    }

    public void setActiveKeyVersion(final String activeKeyVersion) {
        this.activeKeyVersion = activeKeyVersion;
    }
}