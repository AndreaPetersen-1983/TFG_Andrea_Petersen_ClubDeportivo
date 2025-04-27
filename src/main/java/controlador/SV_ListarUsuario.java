package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.DaoUsuarios;
import modelo.Usuario;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet que maneja la obtención de la lista de usuarios en formato JSON.
 */
public class SV_ListarUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Maneja la solicitud GET para obtener la lista de usuarios.
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String respuestaJSON = DaoUsuarios.getInstance().dameJson();
            PrintWriter respuesta = response.getWriter();
            respuesta.print(respuestaJSON);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\":\"Ocurrió un error al obtener los usuarios\"}");
        }
    }
}

 	