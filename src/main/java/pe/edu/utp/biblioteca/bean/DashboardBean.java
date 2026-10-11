package pe.edu.utp.biblioteca.bean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pe.edu.utp.biblioteca.dto.DashboardDto;
import pe.edu.utp.biblioteca.facade.BibliotecaFacade;

@Named
@RequestScoped
public class DashboardBean {
    @Inject private BibliotecaFacade facade;
    private DashboardDto metrics;

    @PostConstruct
    public void init() { metrics = facade.dashboard(); }
    public DashboardDto getMetrics() { return metrics; }
}
