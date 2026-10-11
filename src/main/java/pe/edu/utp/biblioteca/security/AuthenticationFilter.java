package pe.edu.utp.biblioteca.security;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.biblioteca.bean.LoginBean;
import pe.edu.utp.biblioteca.model.Rol;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
    @Inject
    private LoginBean login;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("X-Content-Type-Options", "nosniff");
        httpResponse.setHeader("X-Frame-Options", "DENY");
        httpResponse.setHeader("Referrer-Policy", "same-origin");
        httpResponse.setHeader("Content-Security-Policy", "default-src 'self'; style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline'; img-src 'self' data:");
        String context = httpRequest.getContextPath();
        String uri = httpRequest.getRequestURI().substring(context.length());
        boolean publicResource = uri.equals("/") || uri.equals("/login.xhtml") || uri.equals("/error.xhtml")
                || uri.startsWith("/jakarta.faces.resource/") || uri.startsWith("/resources/");
        if (publicResource) {
            chain.doFilter(request, response);
            return;
        }
        if (login == null || !login.isAuthenticated()) {
            httpResponse.sendRedirect(context + "/login.xhtml");
            return;
        }
        httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        if (uri.startsWith("/admin/") && login.getUsuario().getRol() != Rol.ADMINISTRADOR) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }
}
