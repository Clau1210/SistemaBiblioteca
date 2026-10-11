# Casos de uso

## CU01 Iniciar sesión

| Elemento | Descripción |
|---|---|
| Actor | Administrador o encargado. |
| Precondición | La cuenta existe y se encuentra activa. |
| Flujo principal | El usuario ingresa sus credenciales, el sistema verifica el hash y abre el panel. |
| Alternativa | Si las credenciales no coinciden, se informa el rechazo sin revelar cuál dato falló. |
| Postcondición | Se crea una sesión autenticada con vigencia máxima de 30 minutos sin actividad. |

## CU02 Gestionar usuarios

| Elemento | Descripción |
|---|---|
| Actor | Administrador. |
| Precondición | Sesión con rol ADMINISTRADOR. |
| Flujo principal | Registrar o editar nombre, correo, usuario, rol y contraseña; activar o desactivar cuentas. |
| Regla | El administrador no puede desactivar su propia cuenta durante la sesión. |
| Postcondición | La cuenta queda disponible según el estado asignado. |

## CU03 Gestionar lectores

| Elemento | Descripción |
|---|---|
| Actor | Administrador o encargado. |
| Flujo principal | Registrar, buscar, editar y cambiar el estado de un lector. |
| Reglas | Documento y correo no se repiten; el lector debe estar activo para recibir préstamos. |
| Postcondición | Los datos quedan centralizados para futuras operaciones. |

## CU04 Gestionar catálogo

| Elemento | Descripción |
|---|---|
| Actor | Administrador o encargado. |
| Flujo principal | Administrar autores, editoriales, categorías, libros y ejemplares. |
| Reglas | Cada libro tiene categoría y al menos un autor; el ISBN y el código de ejemplar son únicos. |
| Postcondición | El catálogo distingue el título bibliográfico de cada copia física. |

## CU05 Registrar préstamo

| Elemento | Descripción |
|---|---|
| Actor | Administrador o encargado. |
| Precondición | Lector activo y al menos un ejemplar disponible. |
| Flujo principal | Seleccionar lector, fechas y ejemplares; validar; confirmar la operación. |
| Reglas | Máximo cinco ejemplares por operación y tres préstamos activos por lector. |
| Postcondición | El préstamo queda vigente y los ejemplares quedan prestados en una transacción. |

## CU06 Registrar devolución

| Elemento | Descripción |
|---|---|
| Actor | Administrador o encargado. |
| Precondición | Existe un detalle pendiente de devolución. |
| Flujo principal | Seleccionar el ejemplar, ingresar una observación opcional y confirmar. |
| Postcondición | El ejemplar vuelve a estar disponible; al devolver todos, el préstamo queda devuelto. |

## CU07 Consultar historial

| Elemento | Descripción |
|---|---|
| Actor | Administrador o encargado. |
| Flujo principal | Buscar por nombre o documento y revisar fechas, ejemplares y estados. |
| Postcondición | Se visualiza el historial sin modificarlo. |

## CU08 Generar reportes

| Elemento | Descripción |
|---|---|
| Actor | Administrador o encargado. |
| Flujo principal | Elegir Excel o PDF y descargar el historial. |
| Postcondición | Se obtiene un archivo con datos de préstamos y ejemplares. |
