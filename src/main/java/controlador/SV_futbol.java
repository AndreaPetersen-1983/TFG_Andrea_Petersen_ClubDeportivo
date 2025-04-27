package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet que maneja las solicitudes relacionadas con la página de fútbol.
 */
public class SV_futbol extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
     * Constructor del servlet.
     * @see HttpServlet#HttpServlet()
     */
    public SV_futbol() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Maneja la solicitud GET para redirigir a la página de fútbol.
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException si ocurre un error en el servlet.
     * @throws IOException si ocurre un error de entrada/salida.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.sendRedirect(request.getContextPath()+"/futbol.jsp");// Cada vez que le de al boton de inicio le redirijo al Contactanos..
	}

	/**
     * Maneja la solicitud POST redirigiendo a doGet.
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException si ocurre un error en el servlet.
     * @throws IOException si ocurre un error de entrada/salida.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
