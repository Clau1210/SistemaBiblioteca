package pe.edu.utp.biblioteca.dao;

import jakarta.persistence.EntityManager;
import pe.edu.utp.biblioteca.model.Usuario;
import java.util.List;
import java.util.Optional;

public class UsuarioDao extends GenericDao<Usuario> {
    public UsuarioDao() { super(Usuario.class); }

    public Optional<Usuario> findByUsername(EntityManager em, String username) {
        return em.createQuery("select u from Usuario u where lower(u.nombreUsuario) = :username", Usuario.class)
                .setParameter("username", username.toLowerCase())
                .getResultStream().findFirst();
    }

    public boolean existsUsername(EntityManager em, String username, Long excludedId) {
        String jpql = "select count(u) from Usuario u where lower(u.nombreUsuario) = :username";
        if (excludedId != null) {
            jpql += " and u.id <> :id";
        }
        var query = em.createQuery(jpql, Long.class)
                .setParameter("username", username.toLowerCase());
        if (excludedId != null) {
            query.setParameter("id", excludedId);
        }
        Long count = query.getSingleResult();
        return count > 0;
    }

    public boolean existsEmail(EntityManager em, String email, Long excludedId) {
        String jpql = "select count(u) from Usuario u where lower(u.correo) = :email";
        if (excludedId != null) jpql += " and u.id <> :id";
        var query = em.createQuery(jpql, Long.class).setParameter("email", email.toLowerCase());
        if (excludedId != null) query.setParameter("id", excludedId);
        return query.getSingleResult() > 0;
    }

    public List<Usuario> search(EntityManager em, String text) {
        String term = "%" + text.toLowerCase() + "%";
        return em.createQuery("select u from Usuario u where lower(u.nombreUsuario) like :term or lower(u.nombreCompleto) like :term order by u.nombreCompleto", Usuario.class)
                .setParameter("term", term).getResultList();
    }
}
