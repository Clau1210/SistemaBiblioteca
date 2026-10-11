package pe.edu.utp.biblioteca.service;

import jakarta.enterprise.context.ApplicationScoped;
import pe.edu.utp.biblioteca.dao.PrestamoDao;
import pe.edu.utp.biblioteca.model.Prestamo;
import pe.edu.utp.biblioteca.util.JpaManager;
import java.util.List;

@ApplicationScoped
public class ReporteService {
    private final PrestamoDao dao = new PrestamoDao();
    public List<Prestamo> prestamos() { return JpaManager.query(dao::findRecent); }
}
