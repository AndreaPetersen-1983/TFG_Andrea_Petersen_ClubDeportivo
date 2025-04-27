package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import modelo.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet que prepara los datos necesarios para el formulario de registro.
 * 
 * Este servlet:
 * - Carga desde la base de datos los géneros, centros y deportes.
 * - Guarda los resultados en el request como atributos.
 * - Redirige a registro.jsp para mostrar el formulario con esos datos.
 * 
 * Acceso: http://localhost:8080/prueva_maven/formularioRegistro
 * 
 * @author TuNombre
 * @date 21/04/2025
 */
@WebServlet("/registro") // <<-- Aquí defines la URL que invoca este Servlet
public class SV_FormRegistro extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SV_FormRegistro() {
        super();
    }

    /**
     * Maneja las peticiones GET, normalmente al entrar desde el navegador.
     * Carga datos y los envía al JSP.
     *
     * @param request  Petición HTTP entrante
     * @param response Respuesta HTTP saliente
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // ✅ Crear instancias de los DAO
            DaoGenero daoGenero = new DaoGenero();
            DaoCentroDeporte daoCentroDeporte = new DaoCentroDeporte();

            // 📥 Obtener listas desde la base de datos
            List<Genero> listaGeneros = daoGenero.listarGeneros();
            List<Centro> listaCentros = daoCentroDeporte.listarCentros();
            List<Deporte> listaDeportes = daoCentroDeporte.listarDeportes();

            // 💾 Guardar las listas como atributos del request
            request.setAttribute("listaGeneros", listaGeneros);
            request.setAttribute("listaCentros", listaCentros);
            request.setAttribute("listaDeportes", listaDeportes);

            // 🚀 Redirigir al formulario JSP
            request.getRequestDispatcher("registro.jsp").forward(request, response);

        } catch (SQLException e) {
            System.err.println("❌ Error al cargar datos para el formulario de registro: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}