package pe.edu.utp.biblioteca.report;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.biblioteca.model.DetallePrestamo;
import pe.edu.utp.biblioteca.model.Prestamo;
import pe.edu.utp.biblioteca.service.ReporteService;
import java.awt.Color;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@WebServlet("/reportes/prestamos.pdf")
public class PdfReportServlet extends HttpServlet {
    @Inject private ReporteService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=prestamos_biblioteca.pdf");
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            Font title = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(20, 63, 107));
            document.add(new Paragraph("Reporte de préstamos de biblioteca", title));
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(new float[]{1.1f, 2.2f, 1.2f, 1.1f, 2.5f, 1.2f, 1.2f, 1.1f});
            table.setWidthPercentage(100);
            String[] columns = {"Código", "Lector", "Documento", "Ejemplar", "Libro", "Préstamo", "Vencimiento", "Estado"};
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE);
            for (String column : columns) {
                PdfPCell cell = new PdfPCell(new Phrase(column, headerFont));
                cell.setBackgroundColor(new Color(20, 63, 107));
                cell.setPadding(6);
                table.addCell(cell);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Color.BLACK);
            for (Prestamo loan : service.prestamos()) {
                for (DetallePrestamo detail : loan.getDetalles()) {
                    addCell(table, loan.getCodigo(), bodyFont);
                    addCell(table, loan.getLector().getNombreCompleto(), bodyFont);
                    addCell(table, loan.getLector().getNumeroDocumento(), bodyFont);
                    addCell(table, detail.getEjemplar().getCodigo(), bodyFont);
                    addCell(table, detail.getEjemplar().getLibro().getTitulo(), bodyFont);
                    addCell(table, loan.getFechaPrestamo().format(formatter), bodyFont);
                    addCell(table, loan.getFechaVencimiento().format(formatter), bodyFont);
                    addCell(table, loan.getEstado().name(), bodyFont);
                }
            }
            document.add(table);
        } catch (DocumentException ex) {
            throw new ServletException(ex);
        } finally {
            document.close();
        }
    }

    private void addCell(PdfPTable table, String value, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(value == null ? "" : value, font));
        cell.setPadding(5);
        table.addCell(cell);
    }
}
