package pe.edu.utp.biblioteca.config;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.biblioteca.model.*;
import pe.edu.utp.biblioteca.util.AppConfig;
import pe.edu.utp.biblioteca.util.JpaManager;
import pe.edu.utp.biblioteca.util.PasswordUtil;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@WebListener
public class AppLifecycleListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppLifecycleListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            JpaManager.transaction(em -> {
                Usuario admin = seedAdministrator(em);
                seedDemoData(em, admin);
            });
            LOGGER.info("Sistema Biblioteca iniciado correctamente");
        } catch (RuntimeException ex) {
            LOGGER.error("No fue posible iniciar la persistencia", ex);
            throw ex;
        }
    }

    private Usuario seedAdministrator(EntityManager em) {
        String username = AppConfig.value("BIBLIOTECA_ADMIN_USER", "admin").toLowerCase();
        Usuario existing = em.createQuery("select u from Usuario u where u.nombreUsuario = :username", Usuario.class)
                .setParameter("username", username).getResultStream().findFirst().orElse(null);
        if (existing != null) return existing;
        Usuario admin = createUser(username, "Administrador del sistema", "admin@biblioteca.local", Rol.ADMINISTRADOR);
        admin.setClaveHash(PasswordUtil.hash(AppConfig.value("BIBLIOTECA_ADMIN_PASSWORD", "Admin123*")));
        em.persist(admin);
        return admin;
    }

    private void seedDemoData(EntityManager em, Usuario admin) {
        Categoria programacion = category(em, "Programación", "Desarrollo de software y buenas prácticas");
        Categoria datos = category(em, "Bases de datos", "Diseño y administración de bases de datos");
        Categoria matematicas = category(em, "Matemáticas", "Matemática aplicada y discreta");
        Categoria ingenieria = category(em, "Ingeniería de software", "Procesos y construcción de software");

        Editorial prentice = publisher(em, "Prentice Hall", "Estados Unidos");
        Editorial mcgraw = publisher(em, "McGraw-Hill", "Estados Unidos");
        Editorial pearson = publisher(em, "Pearson", "Reino Unido");

        Autor martin = author(em, "Robert C. Martin", "Estadounidense");
        Autor silberschatz = author(em, "Abraham Silberschatz", "Estadounidense");
        Autor rosen = author(em, "Kenneth H. Rosen", "Estadounidense");
        Autor sommerville = author(em, "Ian Sommerville", "Británica");

        Libro cleanCode = book(em, "9780132350884", "Clean Code", 2008, programacion, prentice, martin);
        Libro baseDatos = book(em, "9780078022159", "Base de datos", 2019, datos, mcgraw, silberschatz);
        Libro matematica = book(em, "9786073224043", "Matemáticas discretas", 2021, matematicas, mcgraw, rosen);
        Libro software = book(em, "9780137035151", "Ingeniería de software", 2020, ingenieria, pearson, sommerville);

        Lector ana = reader(em, "72561834", "Ana", "Torres García", "ana.torres@biblioteca.edu", "987654321");
        Lector luis = reader(em, "70451236", "Luis", "Mendoza Ruiz", "luis.mendoza@biblioteca.edu", "912345678");
        Lector maria = reader(em, "76123890", "María", "Rodríguez Pérez", "maria.rodriguez@biblioteca.edu", "936789012");
        Lector carlos = reader(em, "68901234", "Carlos", "Vega Sánchez", "carlos.vega@biblioteca.edu", "998765432");

        user(em, "jlopez", "Julia López Torres", "julia.lopez@biblioteca.edu", Rol.ENCARGADO);
        user(em, "mgarcia", "Marco García Ruiz", "marco.garcia@biblioteca.edu", Rol.ENCARGADO);
        user(em, "pmunoz", "Paola Muñoz Vega", "paola.munoz@biblioteca.edu", Rol.ADMINISTRADOR);

        Ejemplar e1 = copy(em, "EJ-CC-001", cleanCode, "Estante P-01");
        Ejemplar e2 = copy(em, "EJ-BD-001", baseDatos, "Estante B-01");
        Ejemplar e3 = copy(em, "EJ-MD-001", matematica, "Estante M-01");
        Ejemplar e4 = copy(em, "EJ-IS-001", software, "Estante I-01");
        copy(em, "EJ-CC-002", cleanCode, "Estante P-01");
        copy(em, "EJ-BD-002", baseDatos, "Estante B-01");
        copy(em, "EJ-MD-002", matematica, "Estante M-01");
        copy(em, "EJ-IS-002", software, "Estante I-01");
        copy(em, "EJ-CC-003", cleanCode, "Estante P-01");
        copy(em, "EJ-BD-003", baseDatos, "Estante B-01");
        copy(em, "EJ-IS-003", software, "Estante I-01");

        Long loans = em.createQuery("select count(p) from Prestamo p", Long.class).getSingleResult();
        if (loans == 0) {
            LocalDate today = LocalDate.now();
            loan(em, ana, admin, e1, today.minusDays(2), today.plusDays(12), EstadoPrestamo.VIGENTE, null);
            loan(em, luis, admin, e2, today.minusDays(25), today.minusDays(11), EstadoPrestamo.DEVUELTO, today.minusDays(12));
            loan(em, maria, admin, e3, today.minusDays(20), today.minusDays(6), EstadoPrestamo.VENCIDO, null);
            loan(em, carlos, admin, e4, today.minusDays(1), today.plusDays(13), EstadoPrestamo.VIGENTE, null);
        }
    }

    private Categoria category(EntityManager em, String name, String description) {
        Categoria item = em.createQuery("select c from Categoria c where c.nombre = :name", Categoria.class)
                .setParameter("name", name).getResultStream().findFirst().orElse(null);
        if (item != null) return item;
        item = new Categoria();
        item.setNombre(name);
        item.setDescripcion(description);
        em.persist(item);
        return item;
    }

    private Editorial publisher(EntityManager em, String name, String country) {
        Editorial item = em.createQuery("select e from Editorial e where e.nombre = :name", Editorial.class)
                .setParameter("name", name).getResultStream().findFirst().orElse(null);
        if (item != null) return item;
        item = new Editorial();
        item.setNombre(name);
        item.setPais(country);
        em.persist(item);
        return item;
    }

    private Autor author(EntityManager em, String name, String nationality) {
        Autor item = em.createQuery("select a from Autor a where a.nombre = :name", Autor.class)
                .setParameter("name", name).getResultStream().findFirst().orElse(null);
        if (item != null) return item;
        item = new Autor();
        item.setNombre(name);
        item.setNacionalidad(nationality);
        em.persist(item);
        return item;
    }

    private Libro book(EntityManager em, String isbn, String title, int year, Categoria category,
                       Editorial publisher, Autor author) {
        Libro item = em.createQuery("select l from Libro l where l.isbn = :isbn", Libro.class)
                .setParameter("isbn", isbn).getResultStream().findFirst().orElse(null);
        if (item != null) return item;
        item = new Libro();
        item.setIsbn(isbn);
        item.setTitulo(title);
        item.setAnioPublicacion(year);
        item.setIdioma("Español");
        item.setCategoria(category);
        item.setEditorial(publisher);
        item.setAutores(new LinkedHashSet<>());
        item.getAutores().add(author);
        em.persist(item);
        return item;
    }

    private Lector reader(EntityManager em, String document, String names, String lastNames,
                          String email, String phone) {
        Lector item = em.createQuery("select l from Lector l where l.numeroDocumento = :document", Lector.class)
                .setParameter("document", document).getResultStream().findFirst().orElse(null);
        if (item != null) return item;
        item = new Lector();
        item.setTipoDocumento("DNI");
        item.setNumeroDocumento(document);
        item.setNombres(names);
        item.setApellidos(lastNames);
        item.setCorreo(email);
        item.setTelefono(phone);
        item.setDireccion("Lima");
        em.persist(item);
        return item;
    }

    private void user(EntityManager em, String username, String fullName, String email, Rol role) {
        Long count = em.createQuery("select count(u) from Usuario u where u.nombreUsuario = :username", Long.class)
                .setParameter("username", username).getSingleResult();
        if (count > 0) return;
        em.persist(createUser(username, fullName, email, role));
    }

    private Usuario createUser(String username, String fullName, String email, Rol role) {
        Usuario item = new Usuario();
        item.setNombreUsuario(username);
        item.setNombreCompleto(fullName);
        item.setCorreo(email);
        item.setClaveHash(PasswordUtil.hash("Admin123*"));
        item.setRol(role);
        item.setActivo(true);
        return item;
    }

    private Ejemplar copy(EntityManager em, String code, Libro book, String location) {
        Ejemplar item = em.createQuery("select e from Ejemplar e where e.codigo = :code", Ejemplar.class)
                .setParameter("code", code).getResultStream().findFirst().orElse(null);
        if (item != null) return item;
        item = new Ejemplar();
        item.setCodigo(code);
        item.setLibro(book);
        item.setEstado(EstadoEjemplar.DISPONIBLE);
        item.setUbicacion(location);
        em.persist(item);
        return item;
    }

    private void loan(EntityManager em, Lector reader, Usuario user, Ejemplar copy,
                      LocalDate start, LocalDate due, EstadoPrestamo state, LocalDate returned) {
        Prestamo loan = new Prestamo();
        loan.setLector(reader);
        loan.setUsuario(user);
        loan.setFechaPrestamo(start);
        loan.setFechaVencimiento(due);
        loan.setEstado(state);
        loan.setFechaDevolucion(returned);
        DetallePrestamo detail = new DetallePrestamo();
        detail.setEjemplar(copy);
        detail.setFechaDevolucion(returned);
        loan.agregarDetalle(detail);
        copy.setEstado(returned == null ? EstadoEjemplar.PRESTADO : EstadoEjemplar.DISPONIBLE);
        em.persist(loan);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        JpaManager.close();
    }
}
