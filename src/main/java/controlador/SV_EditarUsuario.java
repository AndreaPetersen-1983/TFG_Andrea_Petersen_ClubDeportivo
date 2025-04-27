package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import modelo.DaoUsuarios;
import modelo.Usuario;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Servlet que permite editar los datos personales:
 * - Propios si es usuario
 * - De otros si es administrador
 * 
 * @author TuNombre
 * @version 1.0
 */
@WebServlet("/SV_EditarUsuario")
public class SV_EditarUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SV_EditarUsuario() {
        super();
    }

    /**
     * Procesa el formulario de edición de usuario.
     * Controla que el usuario tenga permisos para editar su perfil o el de otros.
     *
     * @param request  Petición HTTP
     * @param response Respuesta HTTP
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===============================
        // 🔐 Verificar sesión activa
        // ===============================
        HttpSession session = request.getSession(false);
        Usuario usuarioSesion = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuarioSesion == null) {
            response.sendRedirect("login.jsp?error=sesion");
            return;
        }

        try {
            int idFormulario = Integer.parseInt(request.getParameter("id"));

            // ===============================
            // 🛡️ Validación de permisos
            // ===============================
            boolean puedeEditarOtro = usuarioSesion.getIdRol() == 1; // admin
            boolean esMiPropioPerfil = (idFormulario == usuarioSesion.getId());

            if (!puedeEditarOtro && !esMiPropioPerfil) {
                response.sendRedirect("login.jsp?error=permiso");
                return;
            }

            // ===============================
            // 📥 Obtener parámetros del formulario
            // ===============================
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String email = request.getParameter("email");
            String telefonoStr = request.getParameter("telefono");
            String centro = request.getParameter("centro");
            String deporte = request.getParameter("deporte");
            String foto = request.getParameter("foto");
            String fechaStr = request.getParameter("fechaNacimiento");
            int idGenero = Integer.parseInt(request.getParameter("idGenero")); // ✅ actualizado

            int telefono = Integer.parseInt(telefonoStr);
            LocalDate fechaNacimiento = (fechaStr != null && !fechaStr.isEmpty())
                    ? LocalDate.parse(fechaStr)
                    : null;

         // ===============================
         // 🧾 Construir objeto Usuario
         // ===============================
         Usuario actualizado = new Usuario();
         actualizado.setId(idFormulario);
         actualizado.setNombre(nombre);
         actualizado.setApellidos(apellidos);
         actualizado.setEmail(email);
         actualizado.setTelefono(telefono);
         actualizado.setIdGenero(idGenero); // FK a la tabla genero

         // ⚠️ Cambiar a valores enteros en lugar de String
         int idCentro = Integer.parseInt(request.getParameter("idCentro"));
         int idDeporte = Integer.parseInt(request.getParameter("idDeporte"));

         actualizado.setIdCentro(idCentro);     // FK a la tabla centro
         actualizado.setIdDeporte(idDeporte);   // FK a la tabla deporte

         actualizado.setFoto(foto);
         actualizado.setFechaNacimiento(fechaNacimiento);

         DaoUsuarios dao = DaoUsuarios.getInstance();


            // ===============================
            // 🧠 Mantener datos originales (rol y password)
            // ===============================
            Usuario original = dao.obtenerPorId(idFormulario);
            actualizado.setIdRol(original.getIdRol());
            actualizado.setPassword(original.getPassword());

            dao.update(actualizado);

            // ===============================
            // 🔄 Redirigir tras actualizar
            // ===============================
            if (esMiPropioPerfil) {
                session.setAttribute("usuario", actualizado);
                response.sendRedirect("misDatos.jsp?msg=editado");
            } else {
                response.sendRedirect("listarUsuarios2.html?msg=editado");
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("editarUsuario2.jsp?error=sql");
        }
    }
}
