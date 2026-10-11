package pe.edu.utp.biblioteca.facade;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pe.edu.utp.biblioteca.dto.DashboardDto;
import pe.edu.utp.biblioteca.dto.PrestamoRequest;
import pe.edu.utp.biblioteca.model.*;
import pe.edu.utp.biblioteca.service.*;
import java.util.List;

@ApplicationScoped
public class BibliotecaFacade {
    @Inject private LectorService lectorService;
    @Inject private CatalogoService catalogoService;
    @Inject private PrestamoService prestamoService;
    @Inject private DashboardService dashboardService;

    public List<Lector> lectoresActivos() { return lectorService.active(); }
    public List<Ejemplar> ejemplaresDisponibles() { return catalogoService.ejemplaresDisponibles(); }
    public List<Prestamo> prestamos(String search) { return prestamoService.list(search); }
    public Prestamo registrarPrestamo(PrestamoRequest request) {
        Prestamo result = prestamoService.registrar(request);
        dashboardService.clear();
        return result;
    }
    public void devolver(Long loanId, Long detailId, String observation) {
        prestamoService.devolver(loanId, detailId, observation);
        dashboardService.clear();
    }
    public DashboardDto dashboard() { return dashboardService.metrics(); }
}
