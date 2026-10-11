package pe.edu.utp.biblioteca.bean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pe.edu.utp.biblioteca.model.Rol;
import pe.edu.utp.biblioteca.model.Usuario;
import pe.edu.utp.biblioteca.service.UsuarioService;
import pe.edu.utp.biblioteca.util.FacesUtil;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {
    @Inject private UsuarioService service;
    @Inject private LoginBean loginBean;
    private List<Usuario> items;
    private Usuario form;
    private String password;
    private String search;

    @PostConstruct
    public void init() { nuevo(); buscar(); }
    public void buscar() { items = service.list(search); }
    public void nuevo() { form = new Usuario(); form.setRol(Rol.ENCARGADO); form.setActivo(true); password = null; }
    public void editar(Long id) { form = service.find(id); password = null; }
    public void guardar() {
        try { service.save(form, password); FacesUtil.success("Usuario guardado correctamente"); nuevo(); buscar(); }
        catch (RuntimeException ex) { FacesUtil.error(ex); }
    }
    public void cambiarEstado(Long id) {
        try { service.toggle(id, loginBean.getUsuario().getId()); FacesUtil.success("Estado actualizado"); buscar(); }
        catch (RuntimeException ex) { FacesUtil.error(ex); }
    }
    public Rol[] getRoles() { return Rol.values(); }
    public List<Usuario> getItems() { return items; }
    public Usuario getForm() { return form; }
    public void setForm(Usuario form) { this.form = form; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }
}
