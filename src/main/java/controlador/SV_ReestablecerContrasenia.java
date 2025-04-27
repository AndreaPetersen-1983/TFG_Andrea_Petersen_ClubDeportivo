package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.DaoUsuarios;
import modelo.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet que maneja la reestablecerción de contraseñas de los usuarios.
 */

public class SV_ReestablecerContrasenia extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SV_ReestablecerContrasenia() {
        super();
    }

    /**
     * Maneja la solicitud GET para redirigir al formulario de reestablecer contraseña.
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirigir al formulario de reestablecer contraseña
        request.getRequestDispatcher("formReestablecercontrasenia.html").forward(request, response);
    }
    /**
     * Maneja la solicitud POST para reestablecer la contraseña del usuario.
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperar parámetros del formulario
        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validar que las contraseñas coinciden
        if (newPassword == null || !newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
            request.getRequestDispatcher("formReestablecercontrasenia.html").forward(request, response);
            return;
        }

        DaoUsuarios daoUsuarios = new DaoUsuarios();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(newPassword);

        try {
            // Actualizar la contraseña en la base de datos
            daoUsuarios.actualizarContrasena(email, hashedPassword);

            // Obtener el usuario actualizado y iniciar sesión
            Usuario usuario = daoUsuarios.obtenerPorEmail(email);
            if (usuario != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                response.sendRedirect("AreaPersonalUsuario.html"); // Redirigir a área personal
            } else {
                request.setAttribute("error", "Usuario no encontrado.");
                request.getRequestDispatcher("formReestablecercontrasenia.html").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar la contraseña.");
            request.getRequestDispatcher("formReestablecercontrasenia.html").forward(request, response);
        }
    }
}