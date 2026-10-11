package pe.edu.utp.biblioteca.bean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pe.edu.utp.biblioteca.dto.PrestamoRequest;
import pe.edu.utp.biblioteca.facade.BibliotecaFacade;
import pe.edu.utp.biblioteca.model.Ejemplar;
import pe.edu.utp.biblioteca.model.Lector;
import pe.edu.utp.biblioteca.model.Prestamo;
import pe.edu.utp.biblioteca.util.FacesUtil;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class PrestamoBean implements Serializable {
    @Inject private BibliotecaFacade facade;
    @Inject private LoginBean loginBean;
    private List<Prestamo> items;
    private List<Lector> lectores;
    private List<Ejemplar> disponibles;
    private Long lectorId;
    private List<Long> ejemplarIds;
    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;
    private String observacion;
    private Map<Long, String> observacionesDevolucion;
    private String search;

    @PostConstruct
    public void init() { nuevo(); recargar(); }

    public void nuevo() {
        lectorId = null;
        ejemplarIds = new ArrayList<>();
        fechaPrestamo = LocalDate.now();
        fechaVencimiento = LocalDate.now().plusDays(7);
        observacion = null;
        observacionesDevolucion = new HashMap<>();
    }

    public void recargar() {
        items = facade.prestamos(search);
        lectores = facade.lectoresActivos();
        disponibles = facade.ejemplaresDisponibles();
    }

    public void buscar() { recargar(); }

    public void registrar() {
        try {
            PrestamoRequest request = new PrestamoRequest(lectorId, loginBean.getUsuario().getId(),
                    fechaPrestamo, fechaVencimiento, ejemplarIds, observacion);
            Prestamo result = facade.registrarPrestamo(request);
            FacesUtil.success("Préstamo " + result.getCodigo() + " registrado correctamente");
            nuevo();
            recargar();
        } catch (RuntimeException ex) { FacesUtil.error(ex); }
    }

    public void devolver(Long prestamoId, Long detalleId) {
        try {
            facade.devolver(prestamoId, detalleId, observacionesDevolucion.get(detalleId));
            FacesUtil.success("Devolución registrada correctamente");
            observacionesDevolucion.remove(detalleId);
            recargar();
        } catch (RuntimeException ex) { FacesUtil.error(ex); }
    }

    public List<Prestamo> getItems() { return items; }
    public List<Lector> getLectores() { return lectores; }
    public List<Ejemplar> getDisponibles() { return disponibles; }
    public Long getLectorId() { return lectorId; }
    public void setLectorId(Long lectorId) { this.lectorId = lectorId; }
    public List<Long> getEjemplarIds() { return ejemplarIds; }
    public void setEjemplarIds(List<Long> ejemplarIds) { this.ejemplarIds = ejemplarIds; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public Map<Long, String> getObservacionesDevolucion() { return observacionesDevolucion; }
    public void setObservacionesDevolucion(Map<Long, String> observacionesDevolucion) { this.observacionesDevolucion = observacionesDevolucion; }
    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }
}
