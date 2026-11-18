package pe.edu.utp.biblioteca.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pe.edu.utp.biblioteca.dto.DashboardDto;
import pe.edu.utp.biblioteca.model.EstadoEjemplar;
import pe.edu.utp.biblioteca.model.EstadoPrestamo;
import pe.edu.utp.biblioteca.util.JpaManager;

@ApplicationScoped
public class DashboardService {

    @Inject
    private PrestamoService prestamoService;

    public DashboardDto metrics() {
        prestamoService.actualizarVencidos();

        return JpaManager.query(em -> new DashboardDto(
                em.createQuery("select count(l) from Lector l where l.activo = true", Long.class)
                        .getSingleResult(),
                em.createQuery("select count(l) from Libro l where l.activo = true", Long.class)
                        .getSingleResult(),
                em.createQuery("select count(e) from Ejemplar e where e.activo = true and e.libro.activo = true and e.estado = :available", Long.class)
                        .setParameter("available", EstadoEjemplar.DISPONIBLE)
                        .getSingleResult(),
                em.createQuery("select count(p) from Prestamo p where p.estado = :active", Long.class)
                        .setParameter("active", EstadoPrestamo.VIGENTE)
                        .getSingleResult(),
                em.createQuery("select count(p) from Prestamo p where p.estado = :overdue", Long.class)
                        .setParameter("overdue", EstadoPrestamo.VENCIDO)
                        .getSingleResult()
        ));
    }
}