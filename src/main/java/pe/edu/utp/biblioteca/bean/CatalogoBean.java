package pe.edu.utp.biblioteca.bean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pe.edu.utp.biblioteca.model.*;
import pe.edu.utp.biblioteca.service.CatalogoService;
import pe.edu.utp.biblioteca.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class CatalogoBean implements Serializable {
    @Inject private CatalogoService service;
    private List<Autor> autores;
    private List<Editorial> editoriales;
    private List<Categoria> categorias;
    private List<Libro> libros;
    private List<Ejemplar> ejemplares;
    private Autor autor;
    private Editorial editorial;
    private Categoria categoria;
    private Libro libro;
    private Ejemplar ejemplar;
    private Long categoriaId;
    private Long editorialId;
    private Long libroId;
    private List<Long> autorIds;
    private String search;

    @PostConstruct
    public void init() { nuevos(); recargar(); }

    public void recargar() {
        autores = service.autores();
        editoriales = service.editoriales();
        categorias = service.categorias();
        libros = service.libros(search);
        ejemplares = service.ejemplares(search);
    }

    public void buscar() { recargar(); }

    public void nuevos() {
        autor = new Autor(); autor.setActivo(true);
        editorial = new Editorial(); editorial.setActivo(true);
        categoria = new Categoria(); categoria.setActivo(true);
        libro = new Libro(); libro.setActivo(true); libro.setIdioma("Español");
        ejemplar = new Ejemplar(); ejemplar.setActivo(true); ejemplar.setEstado(EstadoEjemplar.DISPONIBLE);
        categoriaId = null; editorialId = null; libroId = null; autorIds = new ArrayList<>();
    }

    public void editarAutor(Autor item) { autor = item; }
    public void editarEditorial(Editorial item) { editorial = item; }
    public void editarCategoria(Categoria item) { categoria = item; }
    public void editarLibro(Libro item) {
        libro = item;
        categoriaId = item.getCategoria() == null ? null : item.getCategoria().getId();
        editorialId = item.getEditorial() == null ? null : item.getEditorial().getId();
        autorIds = item.getAutores().stream().map(Autor::getId).toList();
    }
    public void editarEjemplar(Ejemplar item) { ejemplar = item; libroId = item.getLibro().getId(); }

    public void guardarAutor() { execute(() -> service.guardarAutor(autor), "Autor guardado correctamente"); }
    public void guardarEditorial() { execute(() -> service.guardarEditorial(editorial), "Editorial guardada correctamente"); }
    public void guardarCategoria() { execute(() -> service.guardarCategoria(categoria), "Categoría guardada correctamente"); }
    public void guardarLibro() { execute(() -> service.guardarLibro(libro, categoriaId, editorialId, autorIds), "Libro guardado correctamente"); }
    public void guardarEjemplar() { execute(() -> service.guardarEjemplar(ejemplar, libroId), "Ejemplar guardado correctamente"); }

    public void estadoAutor(Long id) { toggle(Autor.class, id); }
    public void estadoEditorial(Long id) { toggle(Editorial.class, id); }
    public void estadoCategoria(Long id) { toggle(Categoria.class, id); }
    public void estadoLibro(Long id) { toggle(Libro.class, id); }
    public void estadoEjemplar(Long id) { toggle(Ejemplar.class, id); }

    private void execute(Runnable action, String message) {
        try { action.run(); FacesUtil.success(message); nuevos(); recargar(); }
        catch (RuntimeException ex) { FacesUtil.error(ex); }
    }

    private <T> void toggle(Class<T> type, Long id) {
        try { service.toggle(type, id); FacesUtil.success("Estado actualizado"); recargar(); }
        catch (RuntimeException ex) { FacesUtil.error(ex); }
    }

    public EstadoEjemplar[] getEstadosEjemplar() { return EstadoEjemplar.values(); }
    public List<Autor> getAutores() { return autores; }
    public List<Editorial> getEditoriales() { return editoriales; }
    public List<Categoria> getCategorias() { return categorias; }
    public List<Libro> getLibros() { return libros; }
    public List<Ejemplar> getEjemplares() { return ejemplares; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public Editorial getEditorial() { return editorial; }
    public void setEditorial(Editorial editorial) { this.editorial = editorial; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }
    public Ejemplar getEjemplar() { return ejemplar; }
    public void setEjemplar(Ejemplar ejemplar) { this.ejemplar = ejemplar; }
    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
    public Long getEditorialId() { return editorialId; }
    public void setEditorialId(Long editorialId) { this.editorialId = editorialId; }
    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }
    public List<Long> getAutorIds() { return autorIds; }
    public void setAutorIds(List<Long> autorIds) { this.autorIds = autorIds; }
    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }
}
