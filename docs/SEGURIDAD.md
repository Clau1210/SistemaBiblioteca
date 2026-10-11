# Controles de seguridad

| Riesgo | Control implementado |
|---|---|
| Exposición de contraseñas | Hash BCrypt con costo 12; nunca se guarda la clave en texto plano. |
| Acceso sin autenticación | Filtro aplicado a las rutas y servlets protegidos. |
| Escalada de privilegios | La ruta `/admin/` exige rol ADMINISTRADOR. |
| Fijación de sesión | El identificador de sesión cambia después del acceso correcto. |
| Sesiones abandonadas | Expiración por inactividad a los 30 minutos. |
| Inyección en consultas | JPQL parametrizado y persistencia mediante JPA. |
| Asignación doble de ejemplar | Bloqueo pesimista dentro de la transacción del préstamo. |
| Registros inconsistentes | Préstamo y cambio de disponibilidad se confirman o revierten juntos. |
| Clickjacking y MIME sniffing | Encabezados X-Frame-Options y X-Content-Type-Options. |
| Caché de información privada | Encabezado Cache-Control para páginas autenticadas. |
| Entrada inválida | Validaciones JSF y de servicio, longitudes máximas y restricciones únicas. |

En un despliegue público debe utilizarse HTTPS, marcar la cookie como segura, sustituir las credenciales iniciales y almacenar los secretos en el entorno del servidor.
