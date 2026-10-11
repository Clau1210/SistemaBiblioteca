# Resultado de compilación y pruebas

## Ejecución verificada

| Dato | Resultado |
|---|---|
| Fecha de verificación | 11 de octubre de 2026 |
| Java | 17 |
| Comando | `mvn clean package` |
| Fuentes principales compiladas | 45 |
| Pruebas ejecutadas | 10 |
| Fallos | 0 |
| Errores | 0 |
| Pruebas omitidas | 0 |
| Empaquetado | `SistemaBiblioteca.war` |
| Resultado Maven | BUILD SUCCESS |

## Cobertura funcional comprobada

- Reglas de fechas y límites de préstamo.
- Protección y verificación de contraseñas BCrypt.
- Validación y normalización de campos.
- Mapeo JPA completo con H2 en modo compatible con MySQL.
- Relaciones entre usuario, lector, catálogo, ejemplar, préstamo y detalle.
- Generación íntegra del archivo WAR.

La prueba de aceptación en navegador debe ejecutarse después de desplegar el WAR con MySQL y Payara, siguiendo `GUIA_DEMOSTRACION.md`.
