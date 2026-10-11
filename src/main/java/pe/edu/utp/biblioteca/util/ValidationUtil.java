package pe.edu.utp.biblioteca.util;

import org.apache.commons.lang3.StringUtils;
import java.time.LocalDate;
import java.util.regex.Pattern;

public final class ValidationUtil {
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private ValidationUtil() {}

    public static String required(String value, String field) {
        String clean = StringUtils.trimToEmpty(value);
        if (clean.isEmpty()) throw new IllegalArgumentException(field + " es obligatorio");
        return clean;
    }

    public static String email(String value) {
        String clean = required(value, "El correo").toLowerCase();
        if (!EMAIL.matcher(clean).matches()) throw new IllegalArgumentException("El correo no tiene un formato válido");
        return clean;
    }

    public static void positivePeriod(LocalDate start, LocalDate due) {
        if (start == null || due == null) throw new IllegalArgumentException("Las fechas son obligatorias");
        if (due.isBefore(start)) throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior al préstamo");
    }
}
