package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class SV_Baloncesto
 */
public class SV_Baloncesto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Servlet implementation class SV_Baloncesto.
	 * Este servlet maneja las solicitudes relacionadas con la página de baloncesto.
	 */
    public SV_Baloncesto() {
        super();
       
    }

    /**
     * Maneja el método GET de HTTP.
     * Redirige la solicitud a la página de baloncesto.
     *
     * @param request  la solicitud del cliente
     * @param response la respuesta del servidor
     * @throws ServletException si ocurre un error del servlet
     * @throws IOException      si ocurre un error de entrada/salida
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.sendRedirect(request.getContextPath()+"/baloncesto.jsp");
	}

	/**
     * Maneja el método POST de HTTP.
     * Llama al método doGet para redirigir la solicitud.
     *
     * @param request  la solicitud del cliente
     * @param response la respuesta del servidor
     * @throws ServletException si ocurre un error del servlet
     * @throws IOException      si ocurre un error de entrada/salida
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
