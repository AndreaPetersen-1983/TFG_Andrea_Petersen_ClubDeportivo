package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import modelo.Usuario;

import java.io.IOException;

/**
 * Servlet que muestra los datos del usuario logueado.
 */
@WebServlet("/SV_misDatosPersonales")
public class SV_misDatosPersonales extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SV_misDatosPersonales() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Comprobar si hay sesión y usuario
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            // ❌ Si no hay sesión, redirige a login
            response.sendRedirect("login.jsp?error=sesion");
            return;
        }

        // ✅ Usuario autenticado
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // 👉 Enviar a la vista
        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("misDatos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Por si alguien accede vía POST
    }
}
