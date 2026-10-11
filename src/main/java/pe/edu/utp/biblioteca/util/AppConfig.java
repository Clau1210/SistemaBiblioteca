package pe.edu.utp.biblioteca.util;

public final class AppConfig {
    private AppConfig() {}

    public static String value(String name, String defaultValue) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) value = System.getProperty(name);
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }
}
