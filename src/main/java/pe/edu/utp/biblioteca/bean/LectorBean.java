package pe.edu.utp.biblioteca.bean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pe.edu.utp.biblioteca.model.Lector;
import pe.edu.utp.biblioteca.service.LectorService;
import pe.edu.utp.biblioteca.util.FacesUtil;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class LectorBean implements Serializable {
    @Inject private LectorService service;
    private List<Lector> items;
    private Lector form;
    private String search;

    @PostConstruct
    public void init() { nuevo(); buscar(); }
    public void buscar() { items = service.list(search); }
    public void nuevo() { form = new Lector(); form.setActivo(true); }
    public void editar(Long id) { form = service.find(id); }
    public void guardar() {
        try { service.save(form); FacesUtil.success("Lector guardado correctamente"); nuevo(); buscar(); }
        catch (RuntimeException ex) { FacesUtil.error(ex); }
    }
    public void cambiarEstado(Long id) {
        try { service.toggle(id); FacesUtil.success("Estado actualizado"); buscar(); }
        catch (RuntimeException ex) { FacesUtil.error(ex); }
    }
    public List<Lector> getItems() { return items; }
    public Lector getForm() { return form; }
    public void setForm(Lector form) { this.form = form; }
    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }
}
