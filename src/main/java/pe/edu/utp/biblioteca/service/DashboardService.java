package pe.edu.utp.biblioteca.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import pe.edu.utp.biblioteca.dto.DashboardDto;
import pe.edu.utp.biblioteca.model.EstadoEjemplar;
import pe.edu.utp.biblioteca.model.EstadoPrestamo;
import pe.edu.utp.biblioteca.util.JpaManager;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class DashboardService {
    private final Cache<String, DashboardDto> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.SECONDS).maximumSize(1).build();

    public DashboardDto metrics() {
        DashboardDto current = cache.getIfPresent("metrics");
        if (current != null) return current;
        DashboardDto calculated = JpaManager.query(em -> new DashboardDto(
                em.createQuery("select count(l) from Lector l where l.activo = true", Long.class).getSingleResult(),
                em.createQuery("select count(l) from Libro l where l.activo = true", Long.class).getSingleResult(),
                em.createQuery("select count(e) from Ejemplar e where e.activo = true and e.estado = :available", Long.class)
                        .setParameter("available", EstadoEjemplar.DISPONIBLE).getSingleResult(),
                em.createQuery("select count(p) from Prestamo p where p.estado = :active", Long.class)
                        .setParameter("active", EstadoPrestamo.VIGENTE).getSingleResult(),
                em.createQuery("select count(p) from Prestamo p where p.estado = :overdue", Long.class)
                        .setParameter("overdue", EstadoPrestamo.VENCIDO).getSingleResult()));
        cache.put("metrics", calculated);
        return calculated;
    }

    public void clear() { cache.invalidateAll(); }
}
