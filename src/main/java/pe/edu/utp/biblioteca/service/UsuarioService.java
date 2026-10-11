package pe.edu.utp.biblioteca.service;

import jakarta.enterprise.context.ApplicationScoped;
import pe.edu.utp.biblioteca.dao.UsuarioDao;
import pe.edu.utp.biblioteca.model.Rol;
import pe.edu.utp.biblioteca.model.Usuario;
import pe.edu.utp.biblioteca.util.JpaManager;
import pe.edu.utp.biblioteca.util.PasswordUtil;
import pe.edu.utp.biblioteca.util.ValidationUtil;
import java.util.List;

@ApplicationScoped
public class UsuarioService {
    private final UsuarioDao dao = new UsuarioDao();

    public List<Usuario> list(String search) {
        return JpaManager.query(em -> search == null || search.isBlank()
                ? dao.findAll(em, "nombreCompleto") : dao.search(em, search.trim()));
    }

    public Usuario find(Long id) {
        return JpaManager.query(em -> dao.find(em, id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado")));
    }

    public Usuario save(Usuario user, String rawPassword) {
        user.setNombreUsuario(ValidationUtil.required(user.getNombreUsuario(), "El nombre de usuario").toLowerCase());
        user.setNombreCompleto(ValidationUtil.required(user.getNombreCompleto(), "El nombre completo"));
        user.setCorreo(ValidationUtil.email(user.getCorreo()));
        if (user.getRol() == null) user.setRol(Rol.ENCARGADO);
        return JpaManager.transactionResult(em -> {
            if (dao.existsUsername(em, user.getNombreUsuario(), user.getId())) {
                throw new IllegalArgumentException("El nombre de usuario ya se encuentra registrado");
            }
            if (dao.existsEmail(em, user.getCorreo(), user.getId())) {
                throw new IllegalArgumentException("El correo ya se encuentra registrado");
            }
            Usuario current = user.getId() == null ? null : em.find(Usuario.class, user.getId());
            if (rawPassword != null && !rawPassword.isBlank()) user.setClaveHash(PasswordUtil.hash(rawPassword));
            else if (current != null) user.setClaveHash(current.getClaveHash());
            else throw new IllegalArgumentException("La contraseña es obligatoria");
            return dao.save(em, user);
        });
    }

    public void toggle(Long id, Long currentUserId) {
        if (id.equals(currentUserId)) throw new IllegalArgumentException("No puede desactivar su propio usuario");
        JpaManager.transaction(em -> {
            Usuario user = dao.find(em, id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            user.setActivo(!user.isActivo());
            dao.save(em, user);
        });
    }
}
