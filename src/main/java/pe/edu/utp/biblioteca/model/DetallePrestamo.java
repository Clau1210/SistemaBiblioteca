package pe.edu.utp.biblioteca.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "detalle_prestamo")
public class DetallePrestamo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prestamo_id", nullable = false)
    private Prestamo prestamo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ejemplar_id", nullable = false)
    private Ejemplar ejemplar;

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    @Column(length = 300)
    private String observacion;

    public boolean isDevuelto() { return fechaDevolucion != null; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Prestamo getPrestamo() { return prestamo; }
    public void setPrestamo(Prestamo prestamo) { this.prestamo = prestamo; }
    public Ejemplar getEjemplar() { return ejemplar; }
    public void setEjemplar(Ejemplar ejemplar) { this.ejemplar = ejemplar; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
