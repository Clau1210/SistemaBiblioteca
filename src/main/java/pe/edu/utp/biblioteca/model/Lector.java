package pe.edu.utp.biblioteca.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "lector", uniqueConstraints = {
        @UniqueConstraint(name = "uk_lector_documento", columnNames = "numero_documento"),
        @UniqueConstraint(name = "uk_lector_correo", columnNames = "correo")
})
public class Lector implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_documento", nullable = false, length = 20)
    private String tipoDocumento = "DNI";

    @Column(name = "numero_documento", nullable = false, length = 20)
    private String numeroDocumento;

    @Column(nullable = false, length = 120)
    private String nombres;

    @Column(nullable = false, length = 120)
    private String apellidos;

    @Column(nullable = false, length = 120)
    private String correo;

    @Column(length = 20)
    private String telefono;

    @Column(length = 200)
    private String direccion;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    @PrePersist
    public void prePersist() {
        creadoEn = LocalDateTime.now();
        actualizadoEn = creadoEn;
    }

    @PreUpdate
    public void preUpdate() { actualizadoEn = LocalDateTime.now(); }

    public String getNombreCompleto() { return apellidos + ", " + nombres; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
}
