package pe.edu.utp.biblioteca.config;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.biblioteca.model.Rol;
import pe.edu.utp.biblioteca.model.Usuario;
import pe.edu.utp.biblioteca.util.AppConfig;
import pe.edu.utp.biblioteca.util.JpaManager;
import pe.edu.utp.biblioteca.util.PasswordUtil;

@WebListener
public class AppLifecycleListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppLifecycleListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            JpaManager.transaction(this::seedAdministrator);
            LOGGER.info("Sistema Biblioteca iniciado correctamente");
        } catch (RuntimeException ex) {
            LOGGER.error("No fue posible iniciar la persistencia", ex);
            throw ex;
        }
    }

    private void seedAdministrator(EntityManager em) {
        String username = AppConfig.value("BIBLIOTECA_ADMIN_USER", "admin").toLowerCase();
        Long count = em.createQuery("select count(u) from Usuario u where u.nombreUsuario = :username", Long.class)
                .setParameter("username", username).getSingleResult();
        if (count == 0) {
            Usuario admin = new Usuario();
            admin.setNombreUsuario(username);
            admin.setNombreCompleto("Administrador del sistema");
            admin.setCorreo("admin@biblioteca.local");
            admin.setClaveHash(PasswordUtil.hash(AppConfig.value("BIBLIOTECA_ADMIN_PASSWORD", "Admin123*")));
            admin.setRol(Rol.ADMINISTRADOR);
            admin.setActivo(true);
            em.persist(admin);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        JpaManager.close();
    }
}
