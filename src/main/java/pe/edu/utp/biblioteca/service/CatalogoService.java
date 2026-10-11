package pe.edu.utp.biblioteca.service;

import jakarta.enterprise.context.ApplicationScoped;
import pe.edu.utp.biblioteca.dao.EjemplarDao;
import pe.edu.utp.biblioteca.dao.GenericDao;
import pe.edu.utp.biblioteca.dao.LibroDao;
import pe.edu.utp.biblioteca.model.*;
import pe.edu.utp.biblioteca.util.JpaManager;
import pe.edu.utp.biblioteca.util.ValidationUtil;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class CatalogoService {
    private final GenericDao<Autor> autorDao = new GenericDao<>(Autor.class);
    private final GenericDao<Editorial> editorialDao = new GenericDao<>(Editorial.class);
    private final GenericDao<Categoria> categoriaDao = new GenericDao<>(Categoria.class);
    private final LibroDao libroDao = new LibroDao();
    private final EjemplarDao ejemplarDao = new EjemplarDao();

    public List<Autor> autores() { return JpaManager.query(em -> autorDao.findAll(em, "nombre")); }
    public List<Editorial> editoriales() { return JpaManager.query(em -> editorialDao.findAll(em, "nombre")); }
    public List<Categoria> categorias() { return JpaManager.query(em -> categoriaDao.findAll(em, "nombre")); }
    public List<Libro> libros(String search) {
        return JpaManager.query(em -> search == null || search.isBlank()
                ? libroDao.findAll(em, "titulo") : libroDao.search(em, search.trim()));
    }
    public List<Ejemplar> ejemplares(String search) {
        return JpaManager.query(em -> search == null || search.isBlank()
                ? ejemplarDao.findAll(em, "codigo") : ejemplarDao.search(em, search.trim()));
    }
    public List<Ejemplar> ejemplaresDisponibles() { return JpaManager.query(ejemplarDao::available); }

    public Autor guardarAutor(Autor author) {
        author.setNombre(ValidationUtil.required(author.getNombre(), "El nombre del autor"));
        return JpaManager.transactionResult(em -> autorDao.save(em, author));
    }

    public Editorial guardarEditorial(Editorial publisher) {
        publisher.setNombre(ValidationUtil.required(publisher.getNombre(), "El nombre de la editorial"));
        return JpaManager.transactionResult(em -> editorialDao.save(em, publisher));
    }

    public Categoria guardarCategoria(Categoria category) {
        category.setNombre(ValidationUtil.required(category.getNombre(), "El nombre de la categoría"));
        return JpaManager.transactionResult(em -> categoriaDao.save(em, category));
    }

    public Libro guardarLibro(Libro book, Long categoryId, Long publisherId, List<Long> authorIds) {
        book.setTitulo(ValidationUtil.required(book.getTitulo(), "El título"));
        book.setIsbn(ValidationUtil.required(book.getIsbn(), "El ISBN"));
        if (categoryId == null) throw new IllegalArgumentException("La categoría es obligatoria");
        if (authorIds == null || authorIds.isEmpty()) throw new IllegalArgumentException("Seleccione al menos un autor");
        return JpaManager.transactionResult(em -> {
            if (libroDao.existsIsbn(em, book.getIsbn(), book.getId())) throw new IllegalArgumentException("El ISBN ya se encuentra registrado");
            book.setCategoria(em.getReference(Categoria.class, categoryId));
            book.setEditorial(publisherId == null ? null : em.getReference(Editorial.class, publisherId));
            Set<Autor> authors = new LinkedHashSet<>();
            authorIds.forEach(id -> authors.add(em.getReference(Autor.class, id)));
            book.setAutores(authors);
            return libroDao.save(em, book);
        });
    }

    public Ejemplar guardarEjemplar(Ejemplar copy, Long bookId) {
        copy.setCodigo(ValidationUtil.required(copy.getCodigo(), "El código del ejemplar").toUpperCase());
        if (bookId == null) throw new IllegalArgumentException("El libro es obligatorio");
        return JpaManager.transactionResult(em -> {
            if (ejemplarDao.existsCode(em, copy.getCodigo(), copy.getId())) throw new IllegalArgumentException("El código del ejemplar ya existe");
            copy.setLibro(em.getReference(Libro.class, bookId));
            if (copy.getEstado() == null) copy.setEstado(EstadoEjemplar.DISPONIBLE);
            return ejemplarDao.save(em, copy);
        });
    }

    public <T> void toggle(Class<T> type, Long id) {
        JpaManager.transaction(em -> {
            Object entity = em.find(type, id);
            if (entity == null) throw new IllegalArgumentException("Registro no encontrado");
            if (entity instanceof Autor item) item.setActivo(!item.isActivo());
            else if (entity instanceof Editorial item) item.setActivo(!item.isActivo());
            else if (entity instanceof Categoria item) item.setActivo(!item.isActivo());
            else if (entity instanceof Libro item) item.setActivo(!item.isActivo());
            else if (entity instanceof Ejemplar item) {
                if (item.getEstado() == EstadoEjemplar.PRESTADO) throw new IllegalArgumentException("No puede desactivar un ejemplar prestado");
                item.setActivo(!item.isActivo());
            }
        });
    }
}
