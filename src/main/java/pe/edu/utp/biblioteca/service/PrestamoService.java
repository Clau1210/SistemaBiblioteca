package pe.edu.utp.biblioteca.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.biblioteca.dao.PrestamoDao;
import pe.edu.utp.biblioteca.dto.PrestamoRequest;
import pe.edu.utp.biblioteca.model.*;
import pe.edu.utp.biblioteca.util.JpaManager;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class PrestamoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrestamoService.class);
    private final PrestamoDao dao = new PrestamoDao();

    public List<Prestamo> list(String search) {
        actualizarVencidos();
        return JpaManager.query(em -> search == null || search.isBlank() ? dao.findRecent(em) : dao.search(em, search.trim()));
    }

    public List<Prestamo> vencidos() {
        actualizarVencidos();
        return JpaManager.query(dao::overdue);
    }

    public Prestamo registrar(PrestamoRequest request) {
        if (request.lectorId() == null) throw new IllegalArgumentException("Seleccione un lector");
        if (request.usuarioId() == null) throw new IllegalArgumentException("La sesión de usuario no es válida");
        PrestamoPolicy.validarEjemplares(request.ejemplarIds());
        Set<Long> uniqueIds = new LinkedHashSet<>(request.ejemplarIds());
        PrestamoPolicy.validarFechas(request.fechaPrestamo(), request.fechaVencimiento());
        return JpaManager.transactionResult(em -> {
            Lector reader = em.find(Lector.class, request.lectorId());
            if (reader == null || !reader.isActivo()) throw new IllegalArgumentException("El lector seleccionado no está disponible");
            PrestamoPolicy.validarPrestamosActivos(dao.countActiveByReader(em, reader.getId()));
            Usuario user = em.find(Usuario.class, request.usuarioId());
            if (user == null || !user.isActivo()) throw new IllegalArgumentException("El usuario de la sesión no está disponible");
            Prestamo loan = new Prestamo();
            loan.setLector(reader);
            loan.setUsuario(user);
            loan.setFechaPrestamo(request.fechaPrestamo());
            loan.setFechaVencimiento(request.fechaVencimiento());
            loan.setObservacion(request.observacion());
            loan.setEstado(EstadoPrestamo.VIGENTE);
            for (Long copyId : uniqueIds) {
                Ejemplar copy = em.find(Ejemplar.class, copyId, LockModeType.PESSIMISTIC_WRITE);
                if (copy == null || !copy.isActivo() || copy.getEstado() != EstadoEjemplar.DISPONIBLE) {
                    throw new IllegalArgumentException("Uno de los ejemplares seleccionados ya no se encuentra disponible");
                }
                copy.setEstado(EstadoEjemplar.PRESTADO);
                DetallePrestamo detail = new DetallePrestamo();
                detail.setEjemplar(copy);
                loan.agregarDetalle(detail);
            }
            em.persist(loan);
            LOGGER.info("Préstamo registrado para el lector {} con {} ejemplares", reader.getId(), uniqueIds.size());
            return loan;
        });
    }

    public void devolver(Long loanId, Long detailId, String observation) {
        JpaManager.transaction(em -> {
            Prestamo loan = em.find(Prestamo.class, loanId, LockModeType.PESSIMISTIC_WRITE);
            if (loan == null) throw new IllegalArgumentException("Préstamo no encontrado");
            DetallePrestamo detail = loan.getDetalles().stream().filter(item -> item.getId().equals(detailId)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Detalle de préstamo no encontrado"));
            if (detail.isDevuelto()) throw new IllegalArgumentException("El ejemplar ya fue devuelto");
            detail.setFechaDevolucion(LocalDate.now());
            detail.setObservacion(observation);
            detail.getEjemplar().setEstado(EstadoEjemplar.DISPONIBLE);
            boolean completed = loan.getDetalles().stream().allMatch(DetallePrestamo::isDevuelto);
            if (completed) {
                loan.setEstado(EstadoPrestamo.DEVUELTO);
                loan.setFechaDevolucion(LocalDate.now());
            } else if (loan.getFechaVencimiento().isBefore(LocalDate.now())) {
                loan.setEstado(EstadoPrestamo.VENCIDO);
            }
            LOGGER.info("Devolución registrada para el préstamo {} y detalle {}", loanId, detailId);
        });
    }

    public void actualizarVencidos() {
        JpaManager.transaction(em -> {
            em.createQuery("update Prestamo p set p.estado = :overdue where p.estado = :active and p.fechaVencimiento < :today")
                    .setParameter("overdue", EstadoPrestamo.VENCIDO)
                    .setParameter("active", EstadoPrestamo.VIGENTE)
                    .setParameter("today", LocalDate.now()).executeUpdate();
        });
    }
}
