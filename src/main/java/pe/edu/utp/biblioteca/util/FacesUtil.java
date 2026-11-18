package pe.edu.utp.biblioteca.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public final class FacesUtil {
    private FacesUtil() {}

    public static void success(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación realizada", message));
    }

    public static void error(Throwable error) {
        String message = error instanceof IllegalArgumentException ? error.getMessage() : null;
        if (!(error instanceof IllegalArgumentException)) org.slf4j.LoggerFactory.getLogger(FacesUtil.class).error("Error de operación", error);
        if (message == null || message.isBlank()) message = "No fue posible completar la operación";
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", message));
    }
}
