package pe.edu.utp.biblioteca.dao;

import jakarta.persistence.EntityManager;
import pe.edu.utp.biblioteca.model.Ejemplar;
import pe.edu.utp.biblioteca.model.EstadoEjemplar;
import java.util.List;

public class EjemplarDao extends GenericDao<Ejemplar> {
    public EjemplarDao() { super(Ejemplar.class); }

    public boolean existsCode(EntityManager em, String code, Long excludedId) {
        String jpql = "select count(e) from Ejemplar e where lower(e.codigo) = :code";
        if (excludedId != null) {
            jpql += " and e.id <> :id";
        }
        var query = em.createQuery(jpql, Long.class)
                .setParameter("code", code.toLowerCase());
        if (excludedId != null) {
            query.setParameter("id", excludedId);
        }
        Long count = query.getSingleResult();
        return count > 0;
    }

    public List<Ejemplar> available(EntityManager em) {
        return em.createQuery("select e from Ejemplar e join fetch e.libro where e.activo = true and e.estado = :state order by e.libro.titulo, e.codigo", Ejemplar.class)
                .setParameter("state", EstadoEjemplar.DISPONIBLE).getResultList();
    }

    public List<Ejemplar> search(EntityManager em, String text) {
        String term = "%" + text.toLowerCase() + "%";
        return em.createQuery("select e from Ejemplar e join fetch e.libro l where lower(e.codigo) like :term or lower(l.titulo) like :term order by l.titulo, e.codigo", Ejemplar.class)
                .setParameter("term", term).getResultList();
    }
}
