package pe.edu.utp.biblioteca.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "editorial")
public class Editorial implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 140)
    private String nombre;

    @Column(length = 80)
    private String pais;

    @Column(nullable = false)
    private boolean activo = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
