package pe.edu.utp.biblioteca.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prestamo")
public class Prestamo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "lector_id", nullable = false)
    private Lector lector;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPrestamo estado = EstadoPrestamo.VIGENTE;

    @Column(length = 300)
    private String observacion;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetallePrestamo> detalles = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (fechaPrestamo == null) fechaPrestamo = LocalDate.now();
        creadoEn = LocalDateTime.now();
    }

    public void agregarDetalle(DetallePrestamo detalle) {
        detalle.setPrestamo(this);
        detalles.add(detalle);
    }

    public String getCodigo() { return id == null ? "PENDIENTE" : String.format("PRE-%06d", id); }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Lector getLector() { return lector; }
    public void setLector(Lector lector) { this.lector = lector; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) { this.estado = estado; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public List<DetallePrestamo> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePrestamo> detalles) { this.detalles = detalles; }
}
