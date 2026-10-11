# Diagramas de la solución

## Proceso de préstamo

```mermaid
flowchart TD
    A[Recibir solicitud] --> B[Buscar lector]
    B --> C{Lector activo}
    C -- No --> D[Rechazar solicitud]
    C -- Sí --> E[Seleccionar ejemplares]
    E --> F{Disponibles y dentro del límite}
    F -- No --> G[Informar observación]
    F -- Sí --> H[Registrar préstamo]
    H --> I[Marcar ejemplares prestados]
```

## Casos de uso

```mermaid
flowchart LR
    A[Administrador] --> U1[Gestionar usuarios]
    A --> U2[Gestionar lectores y catálogo]
    A --> U3[Registrar préstamos y devoluciones]
    E[Encargado] --> U2
    E --> U3
    E --> U4[Consultar historial y reportes]
    A --> U4
```

## Arquitectura

```mermaid
flowchart TD
    V[Vistas JSF y Facelets] --> C[Managed Beans]
    C --> F[Facade]
    F --> S[Servicios y políticas]
    S --> D[DAO y JPA]
    D --> B[(MySQL)]
```

## Estructura de desglose del trabajo

```mermaid
flowchart TD
    P[Sistema Biblioteca] --> A[1. Análisis]
    P --> D[2. Diseño]
    P --> I[3. Implementación]
    P --> Q[4. Calidad y entrega]
    A --> A1[Requisitos SRS]
    A --> A2[Procesos y casos de uso]
    D --> D1[UX y arquitectura]
    D --> D2[Clases y base de datos]
    I --> I1[Seguridad y catálogos]
    I --> I2[Préstamos y reportes]
    Q --> Q1[Pruebas]
    Q --> Q2[Documentación y Git]
```

## Cronograma referencial

```mermaid
gantt
    title Plan de construcción del sistema
    dateFormat YYYY-MM-DD
    axisFormat %d/%m
    section Análisis
    Levantamiento y SRS :a1, 2026-08-10, 10d
    Procesos y casos de uso :a2, after a1, 7d
    section Diseño
    UX y arquitectura :d1, after a2, 8d
    Modelo de datos y clases :d2, after a2, 8d
    section Implementación
    Acceso y mantenimientos :i1, after d1, 14d
    Préstamos y devoluciones :i2, after i1, 12d
    Reportes y seguridad :i3, after i2, 7d
    section Calidad
    Pruebas y correcciones :q1, after i3, 7d
    Documentación y entrega :q2, after q1, 5d
```

## Diagrama de clases del dominio

```mermaid
classDiagram
    class Usuario {
      Long id
      String nombreUsuario
      Rol rol
      boolean activo
    }
    class Lector {
      Long id
      String numeroDocumento
      String nombres
      boolean activo
    }
    class Libro {
      Long id
      String titulo
      String isbn
    }
    class Ejemplar {
      Long id
      String codigo
      EstadoEjemplar estado
    }
    class Prestamo {
      Long id
      LocalDate fechaVencimiento
      EstadoPrestamo estado
    }
    class DetallePrestamo {
      Long id
      LocalDate fechaDevolucion
    }
    Libro "1" --> "*" Ejemplar
    Lector "1" --> "*" Prestamo
    Usuario "1" --> "*" Prestamo
    Prestamo "1" --> "1..*" DetallePrestamo
    Ejemplar "1" --> "*" DetallePrestamo
```

## Modelo entidad-relación

```mermaid
erDiagram
    USUARIO ||--o{ PRESTAMO : registra
    LECTOR ||--o{ PRESTAMO : solicita
    PRESTAMO ||--|{ DETALLE_PRESTAMO : contiene
    EJEMPLAR ||--o{ DETALLE_PRESTAMO : participa
    LIBRO ||--o{ EJEMPLAR : posee
    CATEGORIA ||--o{ LIBRO : clasifica
    EDITORIAL ||--o{ LIBRO : publica
    LIBRO ||--o{ LIBRO_AUTOR : relaciona
    AUTOR ||--o{ LIBRO_AUTOR : escribe
```
