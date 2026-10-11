package pe.edu.utp.biblioteca.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {
    @Test
    void normalizaCorreo() {
        assertEquals("usuario@correo.com", ValidationUtil.email(" Usuario@Correo.com "));
    }

    @Test
    void rechazaCorreoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtil.email("correo-invalido"));
    }

    @Test
    void rechazaCampoVacio() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtil.required("   ", "Nombre"));
    }
}
