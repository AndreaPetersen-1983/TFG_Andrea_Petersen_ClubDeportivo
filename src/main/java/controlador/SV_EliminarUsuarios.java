package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.DaoUsuarios;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet que maneja la eliminación de usuarios.
 */
public class SV_EliminarUsuarios extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Maneja la solicitud DELETE para eliminar un usuario específico.
     * @param request La solicitud HTTP que contiene la identificación del usuario.
     * @param response La respuesta HTTP que indica el resultado de la operación.
     * @throws ServletException si ocurre un error en el servlet.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            DaoUsuarios.getInstance().delete(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

