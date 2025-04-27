<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error en la Aplicación</title>
    <link rel="stylesheet" href="css/index.css">
    <style>
        main {
            padding: 40px;
            text-align: center;
            color: #B22222;
        }

        pre {
            background-color: #f8d7da;
            padding: 20px;
            color: #721c24;
            border-radius: 10px;
            overflow-x: auto;
            text-align: left;
        }
    </style>
</head>
<body>
    <header>
        <h1>Error del sistema ⚠️</h1>
    </header>

    <main>
        <h2>Ha ocurrido un error inesperado.</h2>
        <p><strong>Mensaje:</strong> <%= exception.getMessage() %></p>

        <h3>Detalles del error:</h3>
        <pre>
<%
    if (exception != null) {
        exception.printStackTrace(new java.io.PrintWriter(out));
    } else {
        out.println("No se recibió información de la excepción.");
    }
%>
        </pre>

        <p><a href="inicio.jsp" class="button">Volver al inicio</a></p>
    </main>
</body>
</html>
