CREATE DATABASE IF NOT EXISTS biblioteca_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biblioteca_db;

CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL,
    nombre_completo VARCHAR(120) NOT NULL,
    correo VARCHAR(120) NOT NULL,
    clave_hash VARCHAR(100) NOT NULL,
    rol ENUM('ADMINISTRADOR','ENCARGADO') NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_usuario_nombre UNIQUE (nombre_usuario),
    CONSTRAINT uk_usuario_correo UNIQUE (correo)
);

CREATE TABLE IF NOT EXISTS lector (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_documento VARCHAR(20) NOT NULL,
    numero_documento VARCHAR(20) NOT NULL,
    nombres VARCHAR(120) NOT NULL,
    apellidos VARCHAR(120) NOT NULL,
    correo VARCHAR(120) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_lector_documento UNIQUE (numero_documento),
    CONSTRAINT uk_lector_correo UNIQUE (correo)
);

CREATE TABLE IF NOT EXISTS autor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(160) NOT NULL,
    nacionalidad VARCHAR(80),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS editorial (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(140) NOT NULL,
    pais VARCHAR(80),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(250),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_categoria_nombre UNIQUE (nombre)
);

CREATE TABLE IF NOT EXISTS libro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    anio_publicacion INT,
    idioma VARCHAR(30),
    descripcion VARCHAR(500),
    editorial_id BIGINT,
    categoria_id BIGINT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_libro_isbn UNIQUE (isbn),
    CONSTRAINT fk_libro_editorial FOREIGN KEY (editorial_id) REFERENCES editorial(id),
    CONSTRAINT fk_libro_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

CREATE TABLE IF NOT EXISTS libro_autor (
    libro_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    PRIMARY KEY (libro_id, autor_id),
    CONSTRAINT fk_libro_autor_libro FOREIGN KEY (libro_id) REFERENCES libro(id),
    CONSTRAINT fk_libro_autor_autor FOREIGN KEY (autor_id) REFERENCES autor(id)
);

CREATE TABLE IF NOT EXISTS ejemplar (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL,
    libro_id BIGINT NOT NULL,
    estado ENUM('DISPONIBLE','PRESTADO','FUERA_DE_SERVICIO') NOT NULL DEFAULT 'DISPONIBLE',
    ubicacion VARCHAR(120),
    observacion VARCHAR(300),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_ejemplar_codigo UNIQUE (codigo),
    CONSTRAINT fk_ejemplar_libro FOREIGN KEY (libro_id) REFERENCES libro(id)
);

CREATE TABLE IF NOT EXISTS prestamo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lector_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    fecha_devolucion DATE,
    estado ENUM('VIGENTE','VENCIDO','DEVUELTO') NOT NULL DEFAULT 'VIGENTE',
    observacion VARCHAR(300),
    creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prestamo_lector FOREIGN KEY (lector_id) REFERENCES lector(id),
    CONSTRAINT fk_prestamo_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT chk_prestamo_fechas CHECK (fecha_vencimiento >= fecha_prestamo)
);

CREATE TABLE IF NOT EXISTS detalle_prestamo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prestamo_id BIGINT NOT NULL,
    ejemplar_id BIGINT NOT NULL,
    fecha_devolucion DATE,
    observacion VARCHAR(300),
    CONSTRAINT fk_detalle_prestamo FOREIGN KEY (prestamo_id) REFERENCES prestamo(id),
    CONSTRAINT fk_detalle_ejemplar FOREIGN KEY (ejemplar_id) REFERENCES ejemplar(id)
);

CREATE INDEX idx_lector_nombre ON lector(apellidos, nombres);
CREATE INDEX idx_libro_titulo ON libro(titulo);
CREATE INDEX idx_ejemplar_estado ON ejemplar(estado, activo);
CREATE INDEX idx_prestamo_estado_vencimiento ON prestamo(estado, fecha_vencimiento);
CREATE INDEX idx_prestamo_lector ON prestamo(lector_id);
