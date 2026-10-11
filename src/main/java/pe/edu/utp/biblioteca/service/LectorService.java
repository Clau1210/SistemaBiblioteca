package pe.edu.utp.biblioteca.service;

import jakarta.enterprise.context.ApplicationScoped;
import pe.edu.utp.biblioteca.dao.LectorDao;
import pe.edu.utp.biblioteca.model.Lector;
import pe.edu.utp.biblioteca.util.JpaManager;
import pe.edu.utp.biblioteca.util.ValidationUtil;
import java.util.List;

@ApplicationScoped
public class LectorService {
    private final LectorDao dao = new LectorDao();

    public List<Lector> list(String search) {
        return JpaManager.query(em -> search == null || search.isBlank()
                ? dao.findAll(em, "apellidos") : dao.search(em, search.trim()));
    }

    public List<Lector> active() {
        return JpaManager.query(em -> em.createQuery("select l from Lector l where l.activo = true order by l.apellidos, l.nombres", Lector.class).getResultList());
    }

    public Lector find(Long id) {
        return JpaManager.query(em -> dao.find(em, id).orElseThrow(() -> new IllegalArgumentException("Lector no encontrado")));
    }

    public Lector save(Lector reader) {
        reader.setTipoDocumento(ValidationUtil.required(reader.getTipoDocumento(), "El tipo de documento"));
        reader.setNumeroDocumento(ValidationUtil.required(reader.getNumeroDocumento(), "El número de documento"));
        reader.setNombres(ValidationUtil.required(reader.getNombres(), "Los nombres"));
        reader.setApellidos(ValidationUtil.required(reader.getApellidos(), "Los apellidos"));
        reader.setCorreo(ValidationUtil.email(reader.getCorreo()));
        return JpaManager.transactionResult(em -> {
            if (dao.existsDocument(em, reader.getNumeroDocumento(), reader.getId())) {
                throw new IllegalArgumentException("El documento ya se encuentra registrado");
            }
            if (dao.existsEmail(em, reader.getCorreo(), reader.getId())) {
                throw new IllegalArgumentException("El correo ya se encuentra registrado");
            }
            return dao.save(em, reader);
        });
    }

    public void toggle(Long id) {
        JpaManager.transaction(em -> {
            Lector reader = dao.find(em, id).orElseThrow(() -> new IllegalArgumentException("Lector no encontrado"));
            reader.setActivo(!reader.isActivo());
            dao.save(em, reader);
        });
    }
}
