package pe.edu.utp.biblioteca.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import pe.edu.utp.biblioteca.model.Rol;
import pe.edu.utp.biblioteca.model.Usuario;
import pe.edu.utp.biblioteca.service.AuthenticationService;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;
    private Usuario usuario;

    @Inject
    private AuthenticationService authenticationService;

    public void login() throws IOException {
        Usuario authenticated = authenticationService.authenticate(username, password).orElse(null);
        if (authenticated == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acceso denegado", "Usuario o contraseña incorrectos"));
            return;
        }
        usuario = authenticated;
        password = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ((HttpServletRequest) context.getExternalContext().getRequest()).changeSessionId();
        context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/dashboard.xhtml");
    }

    public void logout() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ((HttpServletRequest) context.getExternalContext().getRequest()).getSession().invalidate();
        context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/login.xhtml");
    }

    public boolean isAuthenticated() { return usuario != null; }
    public boolean isAdmin() { return usuario != null && usuario.getRol() == Rol.ADMINISTRADOR; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Usuario getUsuario() { return usuario; }
}
