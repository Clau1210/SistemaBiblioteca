package pe.edu.utp.biblioteca.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

public final class PrestamoPolicy {
    public static final int MAX_EJEMPLARES = 5;
    public static final int MAX_PRESTAMOS_ACTIVOS = 3;

    private PrestamoPolicy() {}

    public static void validarFechas(LocalDate inicio, LocalDate vencimiento) {
        if (inicio == null || vencimiento == null) throw new IllegalArgumentException("Las fechas son obligatorias");
        if (vencimiento.isBefore(inicio)) throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior al préstamo");
    }

    public static void validarEjemplares(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) throw new IllegalArgumentException("Seleccione al menos un ejemplar");
        if (new HashSet<>(ids).size() > MAX_EJEMPLARES) throw new IllegalArgumentException("Un préstamo puede contener como máximo cinco ejemplares");
    }

    public static void validarPrestamosActivos(long total) {
        if (total >= MAX_PRESTAMOS_ACTIVOS) throw new IllegalArgumentException("El lector alcanzó el límite de tres préstamos activos");
    }
}
