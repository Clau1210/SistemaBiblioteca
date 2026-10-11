package pe.edu.utp.biblioteca.service;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PrestamoPolicyTest {
    @Test
    void aceptaPeriodoValido() {
        assertDoesNotThrow(() -> PrestamoPolicy.validarFechas(LocalDate.of(2026, 10, 10), LocalDate.of(2026, 10, 17)));
    }

    @Test
    void rechazaVencimientoAnterior() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> PrestamoPolicy.validarFechas(LocalDate.of(2026, 10, 10), LocalDate.of(2026, 10, 9)));
        assertTrue(error.getMessage().contains("vencimiento"));
    }

    @Test
    void rechazaMasDeCincoEjemplares() {
        assertThrows(IllegalArgumentException.class,
                () -> PrestamoPolicy.validarEjemplares(List.of(1L, 2L, 3L, 4L, 5L, 6L)));
    }

    @Test
    void rechazaLectorConTresPrestamosActivos() {
        assertThrows(IllegalArgumentException.class, () -> PrestamoPolicy.validarPrestamosActivos(3));
    }
}
