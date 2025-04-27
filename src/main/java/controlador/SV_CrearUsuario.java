package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import modelo.DaoUsuarios;
import modelo.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Servlet para crear nuevos usuarios desde el panel de administración.
 */
@WebServlet("/SV_CrearUsuario")
@MultipartConfig
public class SV_CrearUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Recuperar parámetros del formulario
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String email = request.getParameter("email");
            String telefonoStr = request.getParameter("telefono");
            String genero = request.getParameter("genero");
            String centro = request.getParameter("centro");
            String deporte = request.getParameter("deporte");
            String fechaNacimientoStr = request.getParameter("fechaNacimiento");
            String rolStr = request.getParameter("idRol"); // CORRECTO

            // Procesar campos numéricos
            int telefono = (telefonoStr != null && !telefonoStr.isEmpty()) ? Integer.parseInt(telefonoStr) : 0;
            int idRol = (rolStr != null && !rolStr.isEmpty()) ? Integer.parseInt(rolStr) : 0;

            // Validar fecha de nacimiento
            LocalDate fechaNacimiento = null;
            if (fechaNacimientoStr != null && !fechaNacimientoStr.isEmpty()) {
                fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
            }

            // Manejar subida de imagen
            Part fotoPart = request.getPart("foto");
            String fotoNombre = "";
            if (fotoPart != null && fotoPart.getSize() > 0) {
                fotoNombre = Paths.get(fotoPart.getSubmittedFileName()).getFileName().toString();
                File directorio = new File(getServletContext().getRealPath("/fotosSubidas/"));
                if (!directorio.exists()) directorio.mkdirs();
                fotoPart.write(new File(directorio, fotoNombre).getAbsolutePath());
            }

            // Crear contraseña encriptada por defecto
            String password = new BCryptPasswordEncoder().encode("123456");

            // Crear el objeto Usuario con todos los datos
            Usuario usuario = new Usuario(
                0, nombre, apellidos, email, telefono, password,
                fechaNacimiento, genero, centro, deporte, fotoNombre, idRol
            );

            // Insertar usuario en base de datos
            DaoUsuarios daoUsuarios = DaoUsuarios.getInstance();
            daoUsuarios.insertar(usuario);

            // Redirigir con mensaje
            response.sendRedirect("listarUsuarios2.html?msg=creado");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("nuevoUsuario.html?error=sql");
        }
    }
}
