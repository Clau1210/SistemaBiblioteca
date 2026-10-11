package pe.edu.utp.biblioteca.report;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pe.edu.utp.biblioteca.model.DetallePrestamo;
import pe.edu.utp.biblioteca.model.Prestamo;
import pe.edu.utp.biblioteca.service.ReporteService;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/reportes/prestamos.xlsx")
public class ExcelReportServlet extends HttpServlet {
    @Inject private ReporteService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Prestamo> loans = service.prestamos();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=prestamos_biblioteca.xlsx");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Préstamos");
            CellStyle header = workbook.createCellStyle();
            header.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = workbook.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setBold(true);
            header.setFont(font);
            String[] columns = {"Código", "Lector", "Documento", "Ejemplar", "Libro", "Préstamo", "Vencimiento", "Devolución", "Estado"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(header);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            int rowIndex = 1;
            for (Prestamo loan : loans) {
                for (DetallePrestamo detail : loan.getDetalles()) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(loan.getCodigo());
                    row.createCell(1).setCellValue(loan.getLector().getNombreCompleto());
                    row.createCell(2).setCellValue(loan.getLector().getNumeroDocumento());
                    row.createCell(3).setCellValue(detail.getEjemplar().getCodigo());
                    row.createCell(4).setCellValue(detail.getEjemplar().getLibro().getTitulo());
                    row.createCell(5).setCellValue(loan.getFechaPrestamo().format(formatter));
                    row.createCell(6).setCellValue(loan.getFechaVencimiento().format(formatter));
                    row.createCell(7).setCellValue(detail.getFechaDevolucion() == null ? "" : detail.getFechaDevolucion().format(formatter));
                    row.createCell(8).setCellValue(loan.getEstado().name());
                }
            }
            for (int i = 0; i < columns.length; i++) sheet.autoSizeColumn(i);
            workbook.write(response.getOutputStream());
        }
    }
}
