package pe.edu.utp.biblioteca.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record PrestamoRequest(Long lectorId, Long usuarioId, LocalDate fechaPrestamo,
                              LocalDate fechaVencimiento, List<Long> ejemplarIds,
                              String observacion) implements Serializable {}
