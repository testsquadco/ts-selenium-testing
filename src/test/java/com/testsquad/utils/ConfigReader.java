package com.testsquad.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String BASE_PATH = "src/test/resources/config/";

    public static void initializeProperties(String environment) {
        properties = new Properties();
        try {
            String configPath = "src/test/resources/config/" + environment + ".properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties file: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        // Check system properties first (command line arguments)
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.trim().isEmpty()) {
            return systemProperty;
        }
        
        // If not found in system properties, get from properties file
        if (properties == null) {
            throw new RuntimeException("Properties not initialized. Call initializeProperties() first.");
        }
        return properties.getProperty(key);
    }
} 