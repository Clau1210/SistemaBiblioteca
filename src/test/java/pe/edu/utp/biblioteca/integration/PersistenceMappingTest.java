package pe.edu.utp.biblioteca.integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pe.edu.utp.biblioteca.model.*;
import pe.edu.utp.biblioteca.util.PasswordUtil;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class PersistenceMappingTest {
    private static EntityManagerFactory factory;

    @BeforeAll
    static void start() {
        factory = Persistence.createEntityManagerFactory("bibliotecaTestPU");
    }

    @AfterAll
    static void stop() {
        if (factory != null) factory.close();
    }

    @Test
    void persistePrestamoConDetalleYRelaciones() {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("admin_test");
        usuario.setNombreCompleto("Administrador de prueba");
        usuario.setCorreo("admin.test@example.com");
        usuario.setClaveHash(PasswordUtil.hash("Admin123*"));
        usuario.setRol(Rol.ADMINISTRADOR);
        em.persist(usuario);

        Lector lector = new Lector();
        lector.setTipoDocumento("DNI");
        lector.setNumeroDocumento("70001001");
        lector.setNombres("María");
        lector.setApellidos("Quispe");
        lector.setCorreo("maria.test@example.com");
        em.persist(lector);

        Categoria categoria = new Categoria();
        categoria.setNombre("Tecnología de prueba");
        em.persist(categoria);

        Autor autor = new Autor();
        autor.setNombre("Autor de prueba");
        em.persist(autor);

        Libro libro = new Libro();
        libro.setTitulo("Libro de prueba");
        libro.setIsbn("978-test-1001");
        libro.setCategoria(categoria);
        libro.setAutores(Set.of(autor));
        em.persist(libro);

        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setCodigo("TEST-001");
        ejemplar.setLibro(libro);
        ejemplar.setEstado(EstadoEjemplar.PRESTADO);
        em.persist(ejemplar);

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLector(lector);
        prestamo.setFechaPrestamo(LocalDate.of(2026, 10, 11));
        prestamo.setFechaVencimiento(LocalDate.of(2026, 10, 18));
        DetallePrestamo detalle = new DetallePrestamo();
        detalle.setEjemplar(ejemplar);
        prestamo.agregarDetalle(detalle);
        em.persist(prestamo);

        em.getTransaction().commit();
        Long id = prestamo.getId();
        em.clear();

        Prestamo guardado = em.find(Prestamo.class, id);
        assertNotNull(guardado);
        assertEquals("70001001", guardado.getLector().getNumeroDocumento());
        assertEquals(1, guardado.getDetalles().size());
        assertEquals("TEST-001", guardado.getDetalles().get(0).getEjemplar().getCodigo());
        assertEquals(EstadoEjemplar.PRESTADO, guardado.getDetalles().get(0).getEjemplar().getEstado());
        em.close();
    }
}
