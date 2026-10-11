# Inicio rápido

## Ejecutar con Docker

```bash
docker compose up --build
```

Abra `http://localhost:8080/SistemaBiblioteca/` e ingrese con:

| Usuario | Contraseña |
|---|---|
| admin | Admin123* |

## Ejecutar de forma tradicional

1. Instale JDK 17, Maven 3.9, MySQL 8 y Payara 6.
2. Ejecute `database/biblioteca_db.sql`.
3. Configure las variables de `.env.example`.
4. Ejecute `mvn clean package`.
5. Despliegue `target/SistemaBiblioteca.war` en Payara.

El archivo compilado incluido en la entrega se llama `SistemaBiblioteca.war`. Los detalles están en `README.md` y `docs/MANUAL_INSTALACION.md`.
