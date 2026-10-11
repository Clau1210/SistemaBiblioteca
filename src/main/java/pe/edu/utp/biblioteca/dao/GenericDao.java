package pe.edu.utp.biblioteca.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class GenericDao<T> {
    private final Class<T> type;

    public GenericDao(Class<T> type) { this.type = type; }

    public T save(EntityManager em, T entity) {
        return em.merge(entity);
    }

    public Optional<T> find(EntityManager em, Long id) {
        return Optional.ofNullable(em.find(type, id));
    }

    public List<T> findAll(EntityManager em, String orderBy) {
        String entityName = em.getMetamodel().entity(type).getName();
        TypedQuery<T> query = em.createQuery("select e from " + entityName + " e order by e." + orderBy, type);
        return query.getResultList();
    }

    public void remove(EntityManager em, T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }
}
