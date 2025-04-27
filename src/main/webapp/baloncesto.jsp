<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modelo.Usuario" %>
<%@ page session="true" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Baloncesto - Club Deportivo</title>
  <link rel="stylesheet" href="css/index.css">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>
<header>
  <nav>
    <div class="nav-container">
      <a class="logo" href="SV_inicio">
        <img src="img/logo_light_background sin fondo 10.png" alt="Logo">
      </a>
      <button class="hamburger" onclick="toggleMenu()">☰</button>
      <ul class="nav-links" id="menuPrincipal">
        <li><a href="inicio.jsp">Inicio</a></li>
        <li><a href="SV_futbol">Fútbol</a></li>
        <li><a href="SV_Baloncesto">Baloncesto</a></li>
        <li><a href="SV_Waterpolo">Waterpolo</a></li>
        <li><a href="SV_Sincronizada">Sincronizada</a></li>
       

        <% if (usuario == null) { %>
          <li><a href="registro.jsp">Regístrate</a></li>
          <li><a href="login.jsp">Login</a></li>
        <% } else { %>
          <li><a href="SV_misDatosPersonales"><%= usuario.getNombre() %></a></li>
          <li><a href="SV_Logout">Cerrar sesión</a></li>
        <% } %>
      </ul>
    </div>
  </nav>
</header>

<main>
  <section class="hero-banner">
    <img src="img/logo basket.jpeg" alt="Equipo de baloncesto" />
    <h1>Equipo de Baloncesto del Club Deportivo</h1>
  </section>

  <div class="contenido">
    <h2>Historia del Equipo</h2>
    <p>El equipo de baloncesto se ha distinguido por su espíritu competitivo y sus éxitos continuos en ligas nacionales.</p>

    <h2>Categorías</h2>
    <ul>
      <li>Junior: De 10 a 14 años.</li>
      <li>Senior: Mayores de 15 años.</li>
    </ul>

    <h2>Galería de Fotos</h2>
    <div class="galeria">
      <img src="img/equipo basket.jpeg" alt="Equipo Junior">
      <img src="img/basket.png" alt="Equipo Senior">
    </div>

    <h2>Próximos Partidos</h2>
    <table>
      <thead>
        <tr>
          <th>Categoría</th>
          <th>Adversario</th>
          <th>Fecha</th>
          <th>Ubicación</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>Junior</td>
          <td>Club Estrella</td>
          <td>15/10/2024</td>
          <td>Estadio Central</td>
        </tr>
        <tr>
          <td>Senior</td>
          <td>Club Nacional</td>
          <td>18/10/2024</td>
          <td>Estadio Ciudad</td>
        </tr>
      </tbody>
    </table>

    <h2>Noticias Recientes</h2>
    <article>
      <h3>Clasificados para el campeonato nacional</h3>
      <p>El equipo senior ha clasificado para el campeonato nacional después de una temporada impresionante con solo una derrota.</p>
    </article>

    <% if (usuario != null) { %>
      <div class="cta">
        <a href="SV_Inscripcion?deporte=baloncesto" class="button">🏀 Inscribirme</a>
      </div>
    <% } %>
  </div>
</main>

<footer>
  <p>&copy; 2024 Club Deportivo Brains</p>
</footer>

<script>
  function toggleMenu() {
    const menu = document.getElementById("menuPrincipal");
    menu.classList.toggle("show");
  }
</script>
</body>
</html>
