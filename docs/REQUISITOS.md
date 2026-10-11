# Especificación de requisitos del sistema

## Requisitos funcionales

| Código | Requisito | Prioridad |
|---|---|---|
| RF001 | El sistema permitirá iniciar y cerrar sesión mediante credenciales válidas. | Alta |
| RF002 | El administrador gestionará usuarios y asignará los roles administrador o encargado. | Alta |
| RF003 | El personal registrará, buscará, actualizará y desactivará lectores. | Alta |
| RF004 | El personal administrará autores, editoriales y categorías. | Alta |
| RF005 | El personal registrará libros, ISBN, autores, editorial y categoría. | Alta |
| RF006 | El personal administrará ejemplares y su ubicación. | Alta |
| RF007 | El sistema mostrará la disponibilidad actual de cada ejemplar. | Alta |
| RF008 | El personal registrará préstamos para lectores activos. | Alta |
| RF009 | El sistema validará que los ejemplares seleccionados estén disponibles. | Alta |
| RF010 | El sistema actualizará el ejemplar a prestado al confirmar el préstamo. | Alta |
| RF011 | El personal registrará devoluciones parciales o completas. | Alta |
| RF012 | El sistema actualizará el ejemplar a disponible al registrar su devolución. | Alta |
| RF013 | El sistema identificará préstamos vigentes, vencidos y devueltos. | Alta |
| RF014 | El personal consultará el historial mediante nombre o documento del lector. | Media |
| RF015 | El sistema exportará el historial de préstamos en Excel y PDF. | Media |
| RF016 | El panel mostrará indicadores de lectores, libros, ejemplares y préstamos. | Media |

## Requisitos no funcionales

| Código | Requisito | Criterio verificable |
|---|---|---|
| RNF001 | Seguridad de contraseñas | Las contraseñas se almacenan mediante BCrypt. |
| RNF002 | Control de acceso | Las rutas administrativas solo son accesibles para administradores. |
| RNF003 | Integridad | El préstamo y la actualización de ejemplares se confirman en una sola transacción. |
| RNF004 | Concurrencia | El sistema bloquea el ejemplar durante el registro del préstamo. |
| RNF005 | Usabilidad | Las pantallas son responsivas y utilizan etiquetas comprensibles. |
| RNF006 | Compatibilidad | La aplicación funciona en navegadores actuales con soporte HTML5. |
| RNF007 | Rendimiento | Las búsquedas comunes utilizan índices de base de datos. |
| RNF008 | Trazabilidad | Las operaciones técnicas se registran mediante Logback. |
| RNF009 | Mantenibilidad | El código se organiza por capas y responsabilidades. |
| RNF010 | Portabilidad | La aplicación se distribuye como WAR y mediante Docker Compose. |

## Actores

| Actor | Responsabilidades |
|---|---|
| Administrador | Gestionar usuarios y realizar todas las operaciones del sistema. |
| Encargado | Gestionar lectores, catálogo, préstamos, devoluciones y reportes. |

## Reglas de negocio

- Un lector debe encontrarse activo para recibir un préstamo.
- Un lector puede mantener como máximo tres préstamos activos.
- Cada préstamo puede incluir hasta cinco ejemplares.
- La fecha de vencimiento no puede ser anterior a la fecha de préstamo.
- Solo pueden prestarse ejemplares activos con estado disponible.
- Un ejemplar prestado no puede desactivarse.
- El préstamo se considera devuelto cuando todos sus ejemplares han sido entregados.
