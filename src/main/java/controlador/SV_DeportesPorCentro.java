package controlador;

import com.google.gson.Gson;
import modelo.DaoCentroDeporte;
import modelo.Deporte;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/SV_DeportesPorCentro")
public class SV_DeportesPorCentro extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idCentro = Integer.parseInt(request.getParameter("idCentro"));

        try {
            DaoCentroDeporte dao = new DaoCentroDeporte();
            List<Deporte> deportes = dao.deportesPorCentro(idCentro);

            Gson gson = new Gson();
            String json = gson.toJson(deportes);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error cargando deportes");
        }
    }
}
