package pe.edu.utp.biblioteca.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {
    private PasswordUtil() {}

    public static String hash(String rawPassword) {
        if (rawPassword == null || rawPassword.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
    }

    public static boolean matches(String rawPassword, String passwordHash) {
        return rawPassword != null && passwordHash != null && BCrypt.checkpw(rawPassword, passwordHash);
    }
}
