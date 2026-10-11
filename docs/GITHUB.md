# Estrategia de versionamiento

## Ramas

- `main`: versión estable y demostrable.
- `develop`: integración de funcionalidades aprobadas.
- `feature/nombre-funcionalidad`: desarrollo de módulos.
- `fix/nombre-incidencia`: corrección de defectos.

## Flujo

1. Crear una rama desde `develop`.
2. Implementar y probar una funcionalidad completa.
3. Registrar commits pequeños y descriptivos.
4. Publicar la rama en GitHub.
5. Crear un pull request hacia `develop`.
6. Revisar los cambios y las pruebas.
7. Utilizar squash and merge.
8. Integrar `develop` en `main` para cada entrega estable.

## Convención de commits

```text
feat: implementa registro de préstamos
fix: corrige validación de ejemplar disponible
test: agrega pruebas de reglas de préstamo
docs: actualiza manual de instalación
refactor: separa consultas del servicio de lectores
```

## Evidencias

- Repositorio y estructura de archivos.
- Lista de ramas.
- Historial de commits.
- Pull requests aprobados.
- Issues registrados y cerrados.
- Resultado de las pruebas automatizadas.
