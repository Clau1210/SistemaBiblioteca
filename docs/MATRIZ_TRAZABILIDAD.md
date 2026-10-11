# Matriz de trazabilidad

| Requisito | Interfaz | Lógica principal | Datos | Verificación |
|---|---|---|---|---|
| RF001 | `login.xhtml` | `LoginBean`, `AuthenticationService` | `Usuario` | Credencial válida e inválida. |
| RF002 | `admin/usuarios.xhtml` | `UsuarioBean`, `UsuarioService` | `Usuario`, `Rol` | Acceso por rol y cambio de estado. |
| RF003 | `lectores.xhtml` | `LectorBean`, `LectorService` | `Lector` | Alta, búsqueda, edición y desactivación. |
| RF004-RF006 | `catalogo.xhtml` | `CatalogoBean`, `CatalogoService` | Catálogo y `Ejemplar` | Registros, ISBN y código único. |
| RF007 | `catalogo.xhtml`, `prestamos.xhtml` | `EjemplarDao` | `EstadoEjemplar` | Consulta de disponibles. |
| RF008-RF010 | `prestamos.xhtml` | `PrestamoService` | `Prestamo`, `DetallePrestamo` | Préstamo transaccional. |
| RF011-RF012 | `prestamos.xhtml` | `PrestamoService.devolver` | Detalle y ejemplar | Devolución parcial y completa. |
| RF013-RF014 | `prestamos.xhtml` | `PrestamoDao` | Estados y fechas | Búsqueda e identificación de vencidos. |
| RF015 | `reportes.xhtml` | Servlets y `ReporteService` | Historial | Descarga XLSX y PDF. |
| RF016 | `dashboard.xhtml` | `DashboardService` | Consultas agregadas | Indicadores del panel. |
