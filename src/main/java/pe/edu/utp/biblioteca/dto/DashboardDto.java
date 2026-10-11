package pe.edu.utp.biblioteca.dto;

import java.io.Serializable;

public class DashboardDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private final long lectores;
    private final long libros;
    private final long ejemplaresDisponibles;
    private final long prestamosVigentes;
    private final long prestamosVencidos;

    public DashboardDto(long lectores, long libros,
                        long ejemplaresDisponibles,
                        long prestamosVigentes,
                        long prestamosVencidos) {
        this.lectores = lectores;
        this.libros = libros;
        this.ejemplaresDisponibles = ejemplaresDisponibles;
        this.prestamosVigentes = prestamosVigentes;
        this.prestamosVencidos = prestamosVencidos;
    }

    public long getLectores() {
        return lectores;
    }

    public long getLibros() {
        return libros;
    }

    public long getEjemplaresDisponibles() {
        return ejemplaresDisponibles;
    }

    public long getPrestamosVigentes() {
        return prestamosVigentes;
    }

    public long getPrestamosVencidos() {
        return prestamosVencidos;
    }
}
