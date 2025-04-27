package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Servlet que gestiona el cierre de sesión del usuario.
 */
@WebServlet("/SV_Logout")
public class SV_Logout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SV_Logout() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 🧹 Invalidar la sesión
        HttpSession session = request.getSession(false); // No crea sesión si no existe
        if (session != null) {
            session.invalidate();
        }

        // 🔁 Redirigir a login con mensaje
        response.sendRedirect("login.jsp?logout=1");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // por si alguien usa POST para logout
    }
}
