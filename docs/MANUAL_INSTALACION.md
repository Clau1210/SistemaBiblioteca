# Manual de instalación

## Opción 1: Docker Compose

1. Instalar Docker Desktop.
2. Abrir una terminal en la raíz del proyecto.
3. Ejecutar `docker compose up --build`.
4. Esperar a que MySQL figure como saludable y Payara termine el despliegue.
5. Abrir `http://localhost:8080/SistemaBiblioteca/`.
6. Ingresar con `admin` y `Admin123*`.

Para detener los contenedores, ejecutar `docker compose down`. Los datos permanecen en el volumen `biblioteca_data`.

## Opción 2: Maven, MySQL y Payara

1. Instalar JDK 17, Maven 3.9, MySQL 8 y Payara Server 6.
2. Ejecutar `database/biblioteca_db.sql` en MySQL.
3. Configurar las variables indicadas en `.env.example` dentro del proceso que inicia Payara.
4. Ejecutar `mvn clean test`.
5. Ejecutar `mvn clean package`.
6. Desplegar `target/SistemaBiblioteca.war` en Payara.
7. Abrir `http://localhost:8080/SistemaBiblioteca/`.

## Datos de demostración

Después de crear el esquema puede ejecutarse `database/datos_demo.sql`. Los registros incluidos son ficticios y sirven únicamente para la exposición.

## Verificación rápida

- La pantalla de acceso se muestra sin errores.
- La cuenta inicial permite ingresar.
- El panel carga sus cinco indicadores.
- Se puede registrar un lector, libro y ejemplar.
- El ejemplar cambia a PRESTADO y vuelve a DISPONIBLE después de la devolución.
- Los dos reportes se descargan y abren correctamente.
