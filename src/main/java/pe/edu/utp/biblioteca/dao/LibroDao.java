package pe.edu.utp.biblioteca.dao;

import jakarta.persistence.EntityManager;
import pe.edu.utp.biblioteca.model.Libro;
import java.util.List;

public class LibroDao extends GenericDao<Libro> {
    public LibroDao() { super(Libro.class); }

    public boolean existsIsbn(EntityManager em, String isbn, Long excludedId) {
        String jpql = "select count(l) from Libro l where l.isbn = :isbn";
        if (excludedId != null) {
            jpql += " and l.id <> :id";
        }
        var query = em.createQuery(jpql, Long.class)
                .setParameter("isbn", isbn);
        if (excludedId != null) {
            query.setParameter("id", excludedId);
        }
        Long count = query.getSingleResult();
        return count > 0;
    }

    public List<Libro> search(EntityManager em, String text) {
        String term = "%" + text.toLowerCase() + "%";
        return em.createQuery("select distinct l from Libro l left join l.autores a where lower(l.titulo) like :term or lower(l.isbn) like :term or lower(a.nombre) like :term order by l.titulo", Libro.class)
                .setParameter("term", term).getResultList();
    }
}
