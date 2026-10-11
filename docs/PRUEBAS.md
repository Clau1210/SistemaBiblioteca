# Plan de pruebas

## Pruebas unitarias

| Código | Escenario | Resultado esperado |
|---|---|---|
| PU001 | Registrar un préstamo con fechas válidas | La validación finaliza sin errores. |
| PU002 | Registrar un vencimiento anterior al préstamo | Se rechaza la operación. |
| PU003 | Seleccionar más de cinco ejemplares | Se rechaza la operación. |
| PU004 | Prestar a un lector con tres préstamos activos | Se rechaza la operación. |
| PU005 | Generar y verificar una contraseña BCrypt | La clave correcta coincide y una clave diferente no coincide. |
| PU006 | Registrar una contraseña corta | Se rechaza la operación. |
| PU007 | Normalizar un correo válido | El correo se almacena en minúsculas. |
| PU008 | Ingresar un correo inválido | Se rechaza la operación. |

## Pruebas de integración

| Código | Flujo | Verificación |
|---|---|---|
| PI001 | Crear lector, libro y ejemplar | Los registros se consultan desde sus módulos. |
| PI002 | Registrar préstamo | El préstamo queda vigente y el ejemplar prestado. |
| PI003 | Registrar devolución | El préstamo queda devuelto y el ejemplar disponible. |
| PI004 | Descargar XLSX | El archivo se abre y contiene el historial. |
| PI005 | Descargar PDF | El archivo se abre y contiene la tabla de préstamos. |
| PI006 | Acceder a usuarios como encargado | El servidor responde con acceso prohibido. |

La prueba automatizada `PersistenceMappingTest` utiliza H2 en modo compatible con MySQL para comprobar el mapeo completo de usuario, lector, categoría, autor, libro, ejemplar, préstamo y detalle. Los flujos PI002 a PI006 se ejecutan además durante la demostración en Payara y MySQL.

## Ejecución

```bash
mvn clean test
```
