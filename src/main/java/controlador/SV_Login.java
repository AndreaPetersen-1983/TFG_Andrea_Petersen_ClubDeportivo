package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import modelo.DaoUsuarios;
import modelo.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet que gestiona el inicio de sesión y redirige según el rol.
 */
@WebServlet("/SV_Login")
public class SV_Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SV_Login() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validación defensiva: campos vacíos
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=vacia");
            return;
        }

        try {
            DaoUsuarios daoUsuarios = DaoUsuarios.getInstance();
            Usuario usuario = daoUsuarios.obtenerPorEmail(email);

            if (usuario != null && new BCryptPasswordEncoder().matches(password, usuario.getPassword())) {
                // ✅ Login correcto
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);

                // 🔁 Redirigir según rol
                switch (usuario.getIdRol()) {
                    case 1:
                        response.sendRedirect("adminPanel.html");
                        break;
                    case 2:
                        response.sendRedirect("entrenadorPanel.html");
                        break;
                    case 3:
                        response.sendRedirect("jugadorArea.html");
                        break;
                    case 4:
                        response.sendRedirect("tutorArea.html");
                        break;
                    default:
                        response.sendRedirect("inicio.jsp");
                        break;
                }
            } else {
                // ❌ Login incorrecto
                response.sendRedirect("login.jsp?error=usuario&email=" + email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=sql");
        }
    }

    // Redirige a login.jsp si se accede por GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
