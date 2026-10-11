package pe.edu.utp.biblioteca.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {
    @Test
    void generaHashYValidaClave() {
        String hash = PasswordUtil.hash("Admin123*");
        assertNotEquals("Admin123*", hash);
        assertTrue(PasswordUtil.matches("Admin123*", hash));
        assertFalse(PasswordUtil.matches("Incorrecta123*", hash));
    }

    @Test
    void rechazaClaveCorta() {
        assertThrows(IllegalArgumentException.class, () -> PasswordUtil.hash("123"));
    }
}
