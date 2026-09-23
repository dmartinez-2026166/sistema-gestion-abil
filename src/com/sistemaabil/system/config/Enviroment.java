package com.sistemaabil.system.config;

public class Enviroment {

    protected static final String USER = getEnvOrDefault("DB_USER", "IN4AV");
    protected static final String PASSWORD = getEnvOrDefault("DB_PASSWORD", "&mnid4AV");
    protected static final String DATA_BASE = getEnvOrDefault("DB_NAME", "sistema_gestion_abil_in4av");
    protected static final String LOCATION_SERVICE = getEnvOrDefault("DB_HOST", "localhost:3306");

    private static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
