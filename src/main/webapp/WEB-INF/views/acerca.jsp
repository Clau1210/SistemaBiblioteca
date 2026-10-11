<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Información del sistema | Biblioteca</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/app.css">
</head>
<body>
    <main class="login-panel" style="min-height:100vh">
        <section class="login-card" style="width:min(100%,720px)">
            <div class="brand" style="color:#17212b;margin-bottom:20px">
                <div class="brand-mark">B</div>
                <div><strong>Sistema Biblioteca</strong><span style="color:#667382">Información técnica</span></div>
            </div>
            <div class="table-wrap">
                <table>
                    <tbody>
                        <tr><th>Versión</th><td>${applicationVersion}</td></tr>
                        <tr><th>Java</th><td>${javaVersion}</td></tr>
                        <tr><th>Interfaz</th><td>Jakarta Faces, Facelets, JSP, HTML5 y CSS3</td></tr>
                        <tr><th>Persistencia</th><td>JPA, Hibernate y MySQL</td></tr>
                        <tr><th>Arquitectura</th><td>MVC, DAO, DTO, Facade y servicios</td></tr>
                    </tbody>
                </table>
            </div>
            <div class="actions">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/dashboard.xhtml">Volver al panel</a>
            </div>
        </section>
    </main>
</body>
</html>
