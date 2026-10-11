package pe.edu.utp.biblioteca.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.biblioteca.dao.UsuarioDao;
import pe.edu.utp.biblioteca.model.Usuario;
import pe.edu.utp.biblioteca.util.JpaManager;
import pe.edu.utp.biblioteca.util.PasswordUtil;
import pe.edu.utp.biblioteca.util.ValidationUtil;
import java.util.Optional;

@ApplicationScoped
public class AuthenticationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
    private final UsuarioDao usuarioDao = new UsuarioDao();

    public Optional<Usuario> authenticate(String username, String password) {
        String cleanUsername = ValidationUtil.required(username, "El usuario");
        Optional<Usuario> result = JpaManager.query(em -> usuarioDao.findByUsername(em, cleanUsername))
                .filter(Usuario::isActivo)
                .filter(user -> PasswordUtil.matches(password, user.getClaveHash()));
        if (result.isPresent()) LOGGER.info("Inicio de sesión correcto para {}", cleanUsername);
        else LOGGER.warn("Intento de acceso rechazado para {}", cleanUsername);
        return result;
    }
}
