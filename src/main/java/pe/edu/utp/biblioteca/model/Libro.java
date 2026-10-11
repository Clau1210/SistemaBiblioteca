package pe.edu.utp.biblioteca.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "libro", uniqueConstraints = @UniqueConstraint(name = "uk_libro_isbn", columnNames = "isbn"))
public class Libro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 20)
    private String isbn;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @Column(length = 30)
    private String idioma;

    @Column(length = 500)
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "editorial_id")
    private Editorial editorial;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private Set<Autor> autores = new LinkedHashSet<>();

    @Column(nullable = false)
    private boolean activo = true;

    public String getAutoresTexto() {
        return autores.stream().map(Autor::getNombre).collect(Collectors.joining(", "));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Editorial getEditorial() { return editorial; }
    public void setEditorial(Editorial editorial) { this.editorial = editorial; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Set<Autor> getAutores() { return autores; }
    public void setAutores(Set<Autor> autores) { this.autores = autores; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
