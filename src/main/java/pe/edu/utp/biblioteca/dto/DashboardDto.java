package pe.edu.utp.biblioteca.dto;

import java.io.Serializable;

public record DashboardDto(long lectores, long libros, long ejemplaresDisponibles,
                           long prestamosVigentes, long prestamosVencidos) implements Serializable {}
