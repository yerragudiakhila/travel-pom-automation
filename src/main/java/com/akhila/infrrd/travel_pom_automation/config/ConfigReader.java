
package com.akhila.infrrd.travel_pom_automation.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties props = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getResourceAsStream("/config.properties")) {
            if (in != null) props.load(in);
        } catch (Exception ignored) {}
    }

    public static String get(String key) {
        return props.getProperty(key, "");
    }
}
