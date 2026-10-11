# Diccionario de datos

## usuario

| Campo | Tipo | Restricción | Descripción |
|---|---|---|---|
| id | BIGINT | PK, autoincremental | Identificador del usuario. |
| nombre_usuario | VARCHAR(50) | Obligatorio, único | Credencial de acceso. |
| nombre_completo | VARCHAR(120) | Obligatorio | Nombre del integrante del personal. |
| correo | VARCHAR(120) | Obligatorio, único | Correo institucional o de contacto. |
| clave_hash | VARCHAR(100) | Obligatorio | Contraseña protegida con BCrypt. |
| rol | ENUM | ADMINISTRADOR o ENCARGADO | Nivel de autorización. |
| activo | BOOLEAN | Obligatorio | Habilitación de la cuenta. |
| creado_en | DATETIME | Obligatorio | Fecha de creación. |
| actualizado_en | DATETIME | Obligatorio | Última actualización. |

## lector

| Campo | Tipo | Restricción | Descripción |
|---|---|---|---|
| id | BIGINT | PK, autoincremental | Identificador del lector. |
| tipo_documento | VARCHAR(20) | Obligatorio | DNI, carné de extranjería o pasaporte. |
| numero_documento | VARCHAR(20) | Obligatorio, único | Documento del lector. |
| nombres | VARCHAR(120) | Obligatorio | Nombres del lector. |
| apellidos | VARCHAR(120) | Obligatorio | Apellidos del lector. |
| correo | VARCHAR(120) | Obligatorio, único | Correo del lector. |
| telefono | VARCHAR(20) | Opcional | Teléfono de contacto. |
| direccion | VARCHAR(200) | Opcional | Dirección de contacto. |
| activo | BOOLEAN | Obligatorio | Habilitación para operar. |
| creado_en | DATETIME | Obligatorio | Fecha de creación. |
| actualizado_en | DATETIME | Obligatorio | Última actualización. |

## autor

| Campo | Tipo | Restricción | Descripción |
|---|---|---|---|
| id | BIGINT | PK, autoincremental | Identificador del autor. |
| nombre | VARCHAR(160) | Obligatorio | Nombre del autor. |
| nacionalidad | VARCHAR(80) | Opcional | Nacionalidad declarada. |
| activo | BOOLEAN | Obligatorio | Estado del registro. |

## editorial

| Campo | Tipo | Restricción | Descripción |
|---|---|---|---|
| id | BIGINT | PK, autoincremental | Identificador de la editorial. |
| nombre | VARCHAR(140) | Obligatorio | Razón o nombre editorial. |
| pais | VARCHAR(80) | Opcional | País de procedencia. |
| activo | BOOLEAN | Obligatorio | Estado del registro. |

## categoria

| Campo | Tipo | Restricción | Descripción |
|---|---|---|---|
| id | BIGINT | PK, autoincremental | Identificador de la categoría. |
| nombre | VARCHAR(100) | Obligatorio, único | Nombre de clasificación. |
| descripcion | VARCHAR(250) | Opcional | Detalle de la categoría. |
| activo | BOOLEAN | Obligatorio | Estado del registro. |

## libro

| Campo | Tipo | Restricción | Descripción |
|---|---|---|---|
| id | BIGINT | PK, autoincremental | Identificador del título. |
| titulo | VARCHAR(200) | Obligatorio | Título bibliográfico. |
| isbn | VARCHAR(20) | Obligatorio, único | Código ISBN. |
| anio_publicacion | INT | Opcional | Año de publicación. |
| idioma | VARCHAR(30) | Opcional | Idioma principal. |
| descripcion | VARCHAR(500) | Opcional | Reseña breve. |
| editorial_id | BIGINT | FK opcional | Editorial relacionada. |
| categoria_id | BIGINT | FK obligatoria | Categoría relacionada. |
| activo | BOOLEAN | Obligatorio | Estado del título. |

## libro_autor

| Campo | Tipo | Restricción | Descripción |
|---|---|---|---|
| libro_id | BIGINT | PK compuesta, FK | Libro relacionado. |
| autor_id | BIGINT | PK compuesta, FK | Autor relacionado. |

## ejemplar

| Campo | Tipo | Restricción | Descripción |
|---|---|---|---|
| id | BIGINT | PK, autoincremental | Identificador de la copia física. |
| codigo | VARCHAR(30) | Obligatorio, único | Código interno del ejemplar. |
| libro_id | BIGINT | FK obligatoria | Título al que pertenece. |
| estado | ENUM | DISPONIBLE, PRESTADO o FUERA_DE_SERVICIO | Disponibilidad actual. |
| ubicacion | VARCHAR(120) | Opcional | Estante o ubicación física. |
| observacion | VARCHAR(300) | Opcional | Nota de conservación. |
| activo | BOOLEAN | Obligatorio | Estado administrativo. |

## prestamo

| Campo | Tipo | Restricción | Descripción |
|---|---|---|---|
| id | BIGINT | PK, autoincremental | Identificador del préstamo. |
| lector_id | BIGINT | FK obligatoria | Lector que recibe los ejemplares. |
| usuario_id | BIGINT | FK obligatoria | Personal que registra la operación. |
| fecha_prestamo | DATE | Obligatorio | Fecha de entrega. |
| fecha_vencimiento | DATE | Obligatorio | Fecha límite de devolución. |
| fecha_devolucion | DATE | Opcional | Fecha de devolución completa. |
| estado | ENUM | VIGENTE, VENCIDO o DEVUELTO | Situación general. |
| observacion | VARCHAR(300) | Opcional | Nota de la operación. |
| creado_en | DATETIME | Obligatorio | Momento de creación. |

## detalle_prestamo

| Campo | Tipo | Restricción | Descripción |
|---|---|---|---|
| id | BIGINT | PK, autoincremental | Identificador del detalle. |
| prestamo_id | BIGINT | FK obligatoria | Préstamo principal. |
| ejemplar_id | BIGINT | FK obligatoria | Ejemplar prestado. |
| fecha_devolucion | DATE | Opcional | Fecha de retorno individual. |
| observacion | VARCHAR(300) | Opcional | Nota de la devolución. |
