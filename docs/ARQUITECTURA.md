# Arquitectura del sistema

## Modelo por capas

| Capa | Componentes | Responsabilidad |
|---|---|---|
| Presentación | XHTML, Facelets, JSF, CSS y JavaScript | Interacción, navegación y validaciones de interfaz. |
| Control | Managed Beans | Recibir eventos de las vistas y coordinar los casos de uso. |
| Fachada | BibliotecaFacade | Ofrecer operaciones de alto nivel a la presentación. |
| Servicio | Services y políticas | Aplicar reglas de negocio y controlar transacciones. |
| Acceso a datos | DAO y JPA | Ejecutar consultas y persistir entidades. |
| Dominio | Entidades y enumeraciones | Representar usuarios, lectores, catálogo y préstamos. |
| Persistencia | Hibernate y MySQL | Almacenar y relacionar la información. |

## Patrones aplicados

- MVC separa las vistas JSF, los Managed Beans y el modelo de dominio.
- DAO concentra las operaciones de persistencia.
- DTO transporta los datos del panel y del registro de préstamos.
- Facade simplifica las operaciones utilizadas por la interfaz.
- Policy Object concentra las reglas de fechas, límites y ejemplares del préstamo.
- SOLID se aplica mediante responsabilidades separadas, servicios especializados y dependencias inyectadas.

## Seguridad

- BCrypt con factor de trabajo 12.
- Sesión de 30 minutos.
- Cookie HTTP-only.
- Filtro de autenticación para rutas protegidas.
- Verificación de rol para rutas administrativas.
- Validaciones del lado del servidor.
- Consultas JPQL parametrizadas.
- Bloqueo pesimista de ejemplares durante el préstamo.

## Librerías

| Librería | Uso |
|---|---|
| Jakarta Faces | Interfaz web basada en componentes. |
| Hibernate ORM | Implementación de JPA. |
| Apache Commons Lang | Limpieza y validación de cadenas. |
| Google Guava | Caché temporal de indicadores. |
| Apache POI | Generación de reportes XLSX. |
| OpenPDF | Generación de reportes PDF. |
| Logback | Registro de eventos. |
| JBCrypt | Protección de contraseñas. |
| JUnit y Mockito | Pruebas automatizadas. |
