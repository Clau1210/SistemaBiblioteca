package pe.edu.utp.biblioteca.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {
    private PasswordUtil() {}

    public static String hash(String rawPassword) {
        if (rawPassword == null || rawPassword.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
        if (rawPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72) throw new IllegalArgumentException("La contraseña excede el límite de 72 bytes");
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
    }

    public static boolean matches(String rawPassword, String passwordHash) {
        if (rawPassword == null || passwordHash == null || rawPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72) return false;
        try {
            return BCrypt.checkpw(rawPassword, passwordHash);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
