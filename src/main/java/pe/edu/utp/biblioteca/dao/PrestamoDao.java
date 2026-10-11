package pe.edu.utp.biblioteca.dao;

import jakarta.persistence.EntityManager;
import pe.edu.utp.biblioteca.model.EstadoPrestamo;
import pe.edu.utp.biblioteca.model.Prestamo;
import java.time.LocalDate;
import java.util.List;

public class PrestamoDao extends GenericDao<Prestamo> {
    public PrestamoDao() { super(Prestamo.class); }

    public List<Prestamo> findRecent(EntityManager em) {
        return em.createQuery("select distinct p from Prestamo p left join fetch p.detalles d left join fetch d.ejemplar e left join fetch e.libro order by p.id desc", Prestamo.class)
                .getResultList();
    }

    public List<Prestamo> search(EntityManager em, String text) {
        String term = "%" + text.toLowerCase() + "%";
        return em.createQuery("select distinct p from Prestamo p left join fetch p.detalles d left join fetch d.ejemplar e left join fetch e.libro where lower(p.lector.nombres) like :term or lower(p.lector.apellidos) like :term or lower(p.lector.numeroDocumento) like :term order by p.id desc", Prestamo.class)
                .setParameter("term", term).getResultList();
    }

    public List<Prestamo> overdue(EntityManager em) {
        return em.createQuery("select distinct p from Prestamo p left join fetch p.detalles d left join fetch d.ejemplar e left join fetch e.libro where p.estado <> :returned and p.fechaVencimiento < :today order by p.fechaVencimiento", Prestamo.class)
                .setParameter("returned", EstadoPrestamo.DEVUELTO)
                .setParameter("today", LocalDate.now()).getResultList();
    }

    public long countByStatus(EntityManager em, EstadoPrestamo status) {
        return em.createQuery("select count(p) from Prestamo p where p.estado = :status", Long.class)
                .setParameter("status", status).getSingleResult();
    }

    public long countActiveByReader(EntityManager em, Long readerId) {
        return em.createQuery("select count(p) from Prestamo p where p.lector.id = :readerId and p.estado <> :returned", Long.class)
                .setParameter("readerId", readerId).setParameter("returned", EstadoPrestamo.DEVUELTO).getSingleResult();
    }
}
