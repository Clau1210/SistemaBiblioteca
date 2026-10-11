package pe.edu.utp.biblioteca.dao;

import jakarta.persistence.EntityManager;
import pe.edu.utp.biblioteca.model.Lector;
import java.util.List;

public class LectorDao extends GenericDao<Lector> {
    public LectorDao() { super(Lector.class); }

    public boolean existsDocument(EntityManager em, String document, Long excludedId) {
        String jpql = "select count(l) from Lector l where l.numeroDocumento = :document";
        if (excludedId != null) {
            jpql += " and l.id <> :id";
        }
        var query = em.createQuery(jpql, Long.class)
                .setParameter("document", document);
        if (excludedId != null) {
            query.setParameter("id", excludedId);
        }
        Long count = query.getSingleResult();
        return count > 0;
    }

    public boolean existsEmail(EntityManager em, String email, Long excludedId) {
        String jpql = "select count(l) from Lector l where lower(l.correo) = :email";
        if (excludedId != null) jpql += " and l.id <> :id";
        var query = em.createQuery(jpql, Long.class).setParameter("email", email.toLowerCase());
        if (excludedId != null) query.setParameter("id", excludedId);
        return query.getSingleResult() > 0;
    }

    public List<Lector> search(EntityManager em, String text) {
        String term = "%" + text.toLowerCase() + "%";
        return em.createQuery("select l from Lector l where lower(l.nombres) like :term or lower(l.apellidos) like :term or lower(l.numeroDocumento) like :term order by l.apellidos, l.nombres", Lector.class)
                .setParameter("term", term).getResultList();
    }
}
