package controlador;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.bd;

import java.io.IOException;

import java.sql.*;
/**
 * Servlet implementation class sv_conexion
 */
public class sv_conexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Servlet implementation class sv_conexion.
	 * Este servlet maneja la conexión a la base de datos y pruebas básicas de conexión.
	 */
    public sv_conexion() {
        super();
            }

    /**
     * Maneja el método GET de HTTP.
     * Responde con un mensaje simple para verificar el servicio.
     *
     * @param request  la solicitud del cliente
     * @param response la respuesta del servidor
     * @throws ServletException si ocurre un error del servlet
     * @throws IOException      si ocurre un error de entrada/salida
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
     * Maneja el método POST de HTTP.
     * Prueba la conexión a la base de datos utilizando un patrón Singleton.
     *
     * @param request  la solicitud del cliente
     * @param response la respuesta del servidor
     * @throws ServletException si ocurre un error del servlet
     * @throws IOException      si ocurre un error de entrada/salida
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		System.out.println(request.getParameter("nombre"));
		
		bd conn = bd.getInstance();// Utilizar con el patron singelton las conexiones a bd. Andrea
	}

}
