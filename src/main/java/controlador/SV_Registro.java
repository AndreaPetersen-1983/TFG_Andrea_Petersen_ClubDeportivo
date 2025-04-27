package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import modelo.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Servlet único que:
 * - En GET: carga datos para el formulario de registro (género, centro, deporte)
 * - En POST: procesa el registro de tutor y jugador
 */
@WebServlet("/registro")
@MultipartConfig
public class SV_Registro extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SV_Registro() {
        super();
    }

    /**
     * Método GET → Carga de datos para el formulario
     * Se accede al visitar directamente /registro (registro.jsp)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Cargar datos desde los DAO
            DaoGenero daoGenero = new DaoGenero();
            DaoCentroDeporte daoCentroDeporte = new DaoCentroDeporte();

            List<Genero> generos = daoGenero.listarGeneros();
            List<Centro> centros = daoCentroDeporte.listarCentros();
            List<Deporte> deportes = daoCentroDeporte.listarDeportes();

            // Guardar los datos para el JSP
            request.setAttribute("listaGeneros", generos);
            request.setAttribute("listaCentros", centros);
            request.setAttribute("listaDeportes", deportes);

            // Redirigir a la vista
            request.getRequestDispatcher("registro.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    /**
     * Método POST → Procesamiento del formulario de registro
     * Recibe datos del tutor y jugador, crea los usuarios y los vincula
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // =============================
            // 📥 BLOQUE 1: DATOS DEL TUTOR
            // =============================
            String nombreTutor = request.getParameter("nombreTutor");
            String apellidosTutor = request.getParameter("apellidosTutor");
            String emailTutor = request.getParameter("emailTutor");
            String passwordTutor = request.getParameter("passwordTutor");
            int telefonoTutor = Integer.parseInt(request.getParameter("telefonoTutor"));
            int idGeneroTutor = Integer.parseInt(request.getParameter("idGeneroTutor"));
            LocalDate fechaNacimientoTutor = LocalDate.parse(request.getParameter("fechaNacimientoTutor"));
            int rolTutor = Integer.parseInt(request.getParameter("rolTutor")); // debe ser 4

            // =============================
            // 🧒 BLOQUE 2: DATOS DEL JUGADOR
            // =============================
            String nombreJugador = request.getParameter("nombreJugador");
            String apellidosJugador = request.getParameter("apellidosJugador");
            int idGeneroJugador = Integer.parseInt(request.getParameter("idGeneroJugador"));
            LocalDate fechaNacimientoJugador = LocalDate.parse(request.getParameter("fechaNacimientoJugador"));
            int idCentroJugador = Integer.parseInt(request.getParameter("idCentroJugador"));
            int idDeporteJugador = Integer.parseInt(request.getParameter("idDeporteJugador"));
            int rolJugador = Integer.parseInt(request.getParameter("rolJugador")); // debe ser 3

            // =============================
            // 🖼️ FOTO DEL JUGADOR
            // =============================
            Part filePart = request.getPart("fotoJugador");
            String fotoNombre = filePart.getSubmittedFileName();

            String pathFotos = getServletContext().getRealPath("/img/usuarios");
            File uploads = new File(pathFotos);
            if (!uploads.exists()) uploads.mkdirs();

            File file = new File(uploads, fotoNombre);
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // =============================
            // 💾 GUARDAR EN LA BASE DE DATOS
            // =============================
            DaoUsuarios dao = DaoUsuarios.getInstance();

            // Crear y guardar el Tutor
            Usuario tutor = new Usuario();
            tutor.setNombre(nombreTutor);
            tutor.setApellidos(apellidosTutor);
            tutor.setEmail(emailTutor);
            tutor.setPassword(passwordTutor);
            tutor.setTelefono(telefonoTutor);
            tutor.setIdGenero(idGeneroTutor);
            tutor.setFechaNacimiento(fechaNacimientoTutor);
            tutor.setIdRol(rolTutor);

            int idTutor = dao.insertarYDevolverID(tutor); // 🆔

            // Crear y guardar el Jugador asociado
            Usuario jugador = new Usuario();
            jugador.setNombre(nombreJugador);
            jugador.setApellidos(apellidosJugador);
            jugador.setIdGenero(idGeneroJugador);
            jugador.setFechaNacimiento(fechaNacimientoJugador);
            jugador.setIdCentro(idCentroJugador);
            jugador.setIdDeporte(idDeporteJugador);
            jugador.setFoto(fotoNombre);
            jugador.setIdRol(rolJugador);
            jugador.setIdTutor(idTutor);

            dao.insertar(jugador);

            // ✅ Redirigir tras éxito
            response.sendRedirect("login.jsp?registro=ok");

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("registro.jsp?error=sql");
        }
    }
}

