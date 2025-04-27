<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Usuario, java.util.List, modelo.Genero, modelo.Centro, modelo.Deporte, modelo.DaoGenero, modelo.DaoCentro, modelo.DaoDeporte, modelo.DaoUsuarios" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    Usuario usuario = DaoUsuarios.getInstance().obtenerPorId(id);

    List<Genero> generos = DaoGenero.getInstance().listar();
    List<Centro> centros = DaoCentro.getInstance().listar();
    List<Deporte> deportes = DaoDeporte.getInstance().listar();
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Editar Usuario</title>
  <link rel="stylesheet" href="css/index.css">
</head>
<body>
<header>
  <nav>
    <ul class="responsive-ul">
      <li><a href="SV_ListarUsuario">Listado</a></li>
      <li><a href="SV_Logout">Cerrar sesión</a></li>
    </ul>
  </nav>
</header>

<main>
  <h1>Editar Usuario</h1>

  <form action="SV_EditarUsuario" method="post">
    <input type="hidden" name="id" value="<%= usuario.getId() %>" />

    <label>Nombre:</label>
    <input type="text" name="nombre" value="<%= usuario.getNombre() %>" required />

    <label>Apellidos:</label>
    <input type="text" name="apellidos" value="<%= usuario.getApellidos() %>" required />

    <label>Email:</label>
    <input type="email" name="email" value="<%= usuario.getEmail() %>" required />

    <label>Teléfono:</label>
    <input type="tel" name="telefono" pattern="^[6-7][0-9]{8}$" maxlength="9"
           value="<%= usuario.getTelefono() %>" required />

    <label>Fecha de nacimiento:</label>
    <input type="date" name="fechaNacimiento" value="<%= usuario.getFechaNacimiento() %>" required />

    <label>Género:</label>
    <select name="idGenero" required>
      <option value="">Selecciona género</option>
      <% for (Genero g : generos) { %>
        <option value="<%= g.getId() %>" <%= g.getId() == usuario.getIdGenero() ? "selected" : "" %>><%= g.getNombre() %></option>
      <% } %>
    </select>

    <label>Centro:</label>
    <select name="idCentro" required>
      <option value="">Selecciona centro</option>
      <% for (Centro c : centros) { %>
        <option value="<%= c.getId() %>" <%= c.getId() == usuario.getIdCentro() ? "selected" : "" %>><%= c.getNombre() %></option>
      <% } %>
    </select>

    <label>Deporte:</label>
    <select name="idDeporte" required>
      <option value="">Selecciona deporte</option>
      <% for (Deporte d : deportes) { %>
        <option value="<%= d.getId() %>" <%= d.getId() == usuario.getIdDeporte() ? "selected" : "" %>><%= d.getNombre() %></option>
      <% } %>
    </select>

    <label>Foto (URL o nombre):</label>
    <input type="text" name="foto" value="<%= usuario.getFoto() %>" />

    <div class="center">
      <button type="submit" class="button">Guardar cambios</button>
    </div>
  </form>
</main>

<footer>
  <p>&copy; 2024 Club Deportivo Brains</p>
</footer>
</body>
</html>
