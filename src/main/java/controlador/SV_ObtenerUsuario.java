package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import modelo.DaoUsuarios;
import modelo.Usuario;
import modelo.GsonLocalDateAdapter; // ✅ Importamos el adaptador para LocalDate
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Servlet que devuelve un usuario en formato JSON, dado su ID.
 * Utilizado por editarUsuario2.html para precargar el formulario.
 */
@WebServlet("/SV_ObtenerUsuario")
public class SV_ObtenerUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SV_ObtenerUsuario() {
        super();
    }

    /**
     * Procesa solicitudes GET y devuelve un JSON con los datos del usuario.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configuración de la respuesta en JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");

        try (PrintWriter out = response.getWriter()) {
            int id = Integer.parseInt(idParam); // Convertimos el parámetro a int
            System.out.println("📥 ID recibido: " + id);

            // Buscar el usuario en la base de datos
            Usuario usuario = DaoUsuarios.getInstance().obtenerPorId(id);

            if (usuario != null) {
                System.out.println("✅ Usuario encontrado: " + usuario.getNombre());

                // 🔧 Crear Gson con adaptador para LocalDate
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new GsonLocalDateAdapter())
                        .create();

                String json = gson.toJson(usuario); // Convertimos el objeto a JSON
                out.print(json); // Devolvemos el JSON al cliente

            } else {
                System.out.println("⚠️ Usuario no encontrado con ID: " + id);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Usuario no encontrado\"}");
            }

        } catch (NumberFormatException e) {
            // Error al convertir el ID
            System.out.println("❌ ID inválido: " + idParam);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"ID no válido\"}");

        } catch (SQLException e) {
            // Error en la base de datos
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\":\"Error en la base de datos\"}");
        }
    }
}
