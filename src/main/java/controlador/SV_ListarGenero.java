package controlador;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import modelo.DaoGenero;
import modelo.Genero;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/SV_ListarGenero")
public class SV_ListarGenero extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DaoGenero dao = new DaoGenero();
        ArrayList<Genero> generos = dao.listar();

        Gson gson = new Gson();
        String json = gson.toJson(generos);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}

