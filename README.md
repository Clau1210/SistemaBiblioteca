# Sistema web para el registro y control de préstamos de libros de una biblioteca

Aplicación web desarrollada con Java 17, Jakarta Faces, Facelets, JSP, Servlets, CDI, JPA con Hibernate, MySQL y Maven. Implementa los patrones MVC, DAO, DTO y Facade, separación por capas, control de acceso por roles, pruebas automatizadas y generación de reportes en Excel y PDF.

## Funcionalidades

- Inicio y cierre de sesión.
- Perfiles de administrador y encargado.
- Administración de usuarios.
- Registro, edición, búsqueda y activación de lectores.
- Administración de autores, editoriales y categorías.
- Registro de libros con múltiples autores.
- Control individual de ejemplares y disponibilidad.
- Registro de préstamos con validación de fechas y disponibilidad.
- Registro parcial o completo de devoluciones.
- Identificación automática de préstamos vencidos.
- Historial por lector.
- Panel con indicadores.
- Reportes XLSX mediante Apache POI.
- Reportes PDF mediante OpenPDF.
- Registro de eventos mediante SLF4J y Logback.
- Pruebas unitarias con JUnit 5.

## Requisitos

- Java JDK 17.
- Maven 3.9 o superior.
- MySQL 8.
- Payara Server 6.
- Visual Studio Code con Extension Pack for Java y Community Server Connectors.

## Configuración de MySQL

1. Abrir MySQL Workbench.
2. Ejecutar `database/biblioteca_db.sql`.
3. Copiar `.env.example` como referencia para configurar las variables del servidor.
4. Definir las variables antes de iniciar Payara:

```text
BIBLIOTECA_DB_URL=jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima
BIBLIOTECA_DB_USER=root
BIBLIOTECA_DB_PASSWORD=CLAVE_MYSQL
BIBLIOTECA_SCHEMA_ACTION=update
BIBLIOTECA_ADMIN_USER=admin
BIBLIOTECA_ADMIN_PASSWORD=Admin123*
```

Si no se definen variables, se utilizará MySQL en `localhost:3306`, el usuario `root` y una contraseña vacía.

## Compilación

```bash
mvn clean test
mvn clean package
```

El archivo generado será `target/SistemaBiblioteca.war`.

## Ejecución en Payara

1. Iniciar el dominio de Payara.
2. Desplegar `target/SistemaBiblioteca.war`.
3. Abrir `http://localhost:8080/SistemaBiblioteca/`.
4. Ingresar con `admin` y `Admin123*`.
5. Cambiar la contraseña inicial desde el módulo de usuarios.

## Ejecución con Docker

```bash
docker compose up --build
```

Abrir `http://localhost:8080/SistemaBiblioteca/`.

## Estructura

```text
src/main/java/pe/edu/utp/biblioteca
├── bean
├── config
├── dao
├── dto
├── facade
├── model
├── report
├── security
├── service
└── util
```

## Seguridad

Las contraseñas se almacenan con BCrypt, las sesiones expiran a los 30 minutos, las cookies de sesión son HTTP-only, las rutas administrativas requieren el rol `ADMINISTRADOR` y las operaciones de préstamo utilizan bloqueos de escritura para evitar que un ejemplar sea asignado dos veces.

## Documentación

- `docs/REQUISITOS.md`
- `docs/CASOS_DE_USO.md`
- `docs/ARQUITECTURA.md`
- `docs/DICCIONARIO_DATOS.md`
- `docs/DIAGRAMAS.md`
- `docs/PLANIFICACION.md`
- `docs/MATRIZ_TRAZABILIDAD.md`
- `docs/PRUEBAS.md`
- `docs/SEGURIDAD.md`
- `docs/MANUAL_INSTALACION.md`
- `docs/MANUAL_USUARIO.md`
- `docs/GITHUB.md`
- `docs/GUIA_DEMOSTRACION.md`
- `docs/CHECKLIST_ENTREGA.md`
- `docs/javadoc/index.html`
- `docs/diagramas/`

## Credenciales iniciales

| Usuario | Contraseña | Rol |
|---|---|---|
| admin | Admin123* | ADMINISTRADOR |

La cuenta inicial se crea automáticamente durante el primer inicio de la aplicación.
