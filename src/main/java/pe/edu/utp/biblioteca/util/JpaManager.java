package pe.edu.utp.biblioteca.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public final class JpaManager {
    private static final EntityManagerFactory FACTORY = createFactory();

    private JpaManager() {}

    private static EntityManagerFactory createFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", AppConfig.value("BIBLIOTECA_DB_URL",
                "jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima"));
        properties.put("jakarta.persistence.jdbc.user", AppConfig.value("BIBLIOTECA_DB_USER", "root"));
        properties.put("jakarta.persistence.jdbc.password", AppConfig.value("BIBLIOTECA_DB_PASSWORD", ""));
        properties.put("hibernate.hbm2ddl.auto", AppConfig.value("BIBLIOTECA_SCHEMA_ACTION", "update"));
        return Persistence.createEntityManagerFactory("bibliotecaPU", properties);
    }

    public static <T> T query(Function<EntityManager, T> operation) {
        EntityManager em = FACTORY.createEntityManager();
        try {
            return operation.apply(em);
        } finally {
            em.close();
        }
    }

    public static <T> T transactionResult(Function<EntityManager, T> operation) {
        EntityManager em = FACTORY.createEntityManager();
        try {
            em.getTransaction().begin();
            T result = operation.apply(em);
            em.getTransaction().commit();
            return result;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public static void transaction(Consumer<EntityManager> operation) {
        transactionResult(em -> {
            operation.accept(em);
            return null;
        });
    }

    public static void close() {
        if (FACTORY.isOpen()) FACTORY.close();
    }
}
