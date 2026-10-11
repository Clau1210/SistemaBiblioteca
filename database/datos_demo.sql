USE biblioteca_db;

INSERT IGNORE INTO categoria (nombre, descripcion, activo) VALUES
('Literatura', 'Novelas, cuentos y obras literarias', TRUE),
('Tecnología', 'Informática, desarrollo y sistemas', TRUE),
('Ciencias', 'Obras de ciencias naturales y exactas', TRUE);

INSERT INTO autor (nombre, nacionalidad, activo)
SELECT 'Julio Ramón Ribeyro', 'Peruana', TRUE
WHERE NOT EXISTS (SELECT 1 FROM autor WHERE nombre = 'Julio Ramón Ribeyro');

INSERT INTO autor (nombre, nacionalidad, activo)
SELECT 'Robert C. Martin', 'Estadounidense', TRUE
WHERE NOT EXISTS (SELECT 1 FROM autor WHERE nombre = 'Robert C. Martin');

INSERT INTO editorial (nombre, pais, activo)
SELECT 'Editorial Demo', 'Perú', TRUE
WHERE NOT EXISTS (SELECT 1 FROM editorial WHERE nombre = 'Editorial Demo');

INSERT IGNORE INTO lector (tipo_documento, numero_documento, nombres, apellidos, correo, telefono, direccion, activo)
VALUES ('DNI', '70000001', 'María', 'Quispe Flores', 'maria.quispe@example.com', '900000001', 'Lima', TRUE),
       ('DNI', '70000002', 'José', 'Torres Ruiz', 'jose.torres@example.com', '900000002', 'Lima', TRUE);

INSERT IGNORE INTO libro (titulo, isbn, anio_publicacion, idioma, descripcion, editorial_id, categoria_id, activo)
SELECT 'La palabra del mudo', '978-demo-0001', 1973, 'Español', 'Registro académico de demostración', e.id, c.id, TRUE
FROM editorial e, categoria c
WHERE e.nombre = 'Editorial Demo' AND c.nombre = 'Literatura';

INSERT IGNORE INTO libro (titulo, isbn, anio_publicacion, idioma, descripcion, editorial_id, categoria_id, activo)
SELECT 'Código limpio', '978-demo-0002', 2008, 'Español', 'Registro académico de demostración', e.id, c.id, TRUE
FROM editorial e, categoria c
WHERE e.nombre = 'Editorial Demo' AND c.nombre = 'Tecnología';

INSERT IGNORE INTO libro_autor (libro_id, autor_id)
SELECT l.id, a.id FROM libro l, autor a
WHERE l.isbn = '978-demo-0001' AND a.nombre = 'Julio Ramón Ribeyro';

INSERT IGNORE INTO libro_autor (libro_id, autor_id)
SELECT l.id, a.id FROM libro l, autor a
WHERE l.isbn = '978-demo-0002' AND a.nombre = 'Robert C. Martin';

INSERT IGNORE INTO ejemplar (codigo, libro_id, estado, ubicacion, activo)
SELECT 'EJ-0001', id, 'DISPONIBLE', 'Estante A-01', TRUE FROM libro WHERE isbn = '978-demo-0001';

INSERT IGNORE INTO ejemplar (codigo, libro_id, estado, ubicacion, activo)
SELECT 'EJ-0002', id, 'DISPONIBLE', 'Estante A-01', TRUE FROM libro WHERE isbn = '978-demo-0001';

INSERT IGNORE INTO ejemplar (codigo, libro_id, estado, ubicacion, activo)
SELECT 'EJ-0003', id, 'DISPONIBLE', 'Estante T-02', TRUE FROM libro WHERE isbn = '978-demo-0002';
