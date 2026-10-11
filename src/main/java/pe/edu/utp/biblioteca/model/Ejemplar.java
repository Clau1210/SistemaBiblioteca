package pe.edu.utp.biblioteca.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ejemplar", uniqueConstraints = @UniqueConstraint(name = "uk_ejemplar_codigo", columnNames = "codigo"))
public class Ejemplar implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoEjemplar estado = EstadoEjemplar.DISPONIBLE;

    @Column(length = 120)
    private String ubicacion;

    @Column(length = 300)
    private String observacion;

    @Column(nullable = false)
    private boolean activo = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }
    public EstadoEjemplar getEstado() { return estado; }
    public void setEstado(EstadoEjemplar estado) { this.estado = estado; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
