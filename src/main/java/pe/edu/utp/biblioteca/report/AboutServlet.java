package pe.edu.utp.biblioteca.report;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/acerca")
public class AboutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("javaVersion", System.getProperty("java.version"));
        request.setAttribute("applicationVersion", "1.0.0");
        request.getRequestDispatcher("/WEB-INF/views/acerca.jsp").forward(request, response);
    }
}
