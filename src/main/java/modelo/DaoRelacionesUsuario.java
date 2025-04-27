package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO que gestiona las relaciones entre usuarios:
 * - Asignación de jugadores a tutores
 * - Asignación de jugadores a entrenadores
 *
 * Tablas implicadas:
 * - tutor_jugador (relación tutor ↔ jugador)
 * - entrenador_jugador (relación entrenador ↔ jugador)
 *
 * @author TuNombre
 * @date 21/04/2025
 */
public class DaoRelacionesUsuario {
    private Connection conn;

    /**
     * Constructor que establece la conexión con la base de datos.
     */
    public DaoRelacionesUsuario() {
        conn = bd.getInstance().getConnection();
    }

    // ========================================================================================
    // 📦 FUNCIONES RELACIONADAS CON TUTORES
    // ========================================================================================

    /**
     * Devuelve la lista de jugadores asignados a un tutor.
     *
     * @param idTutor ID del tutor
     * @return Lista de usuarios con rol de jugador asignados al tutor
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public List<Usuario> obtenerJugadoresPorTutor(int idTutor) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = """
            SELECT u.* FROM tutor_jugador tj
            JOIN usuario u ON tj.idJugador = u.idUsuario
            WHERE tj.idTutor = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTutor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = mapUsuario(rs);
                lista.add(u);
            }
        }

        return lista;
    }

    /**
     * Asocia un jugador a un tutor.
     *
     * @param idJugador ID del jugador
     * @param idTutor ID del tutor
     * @throws SQLException Si ocurre un error en la inserción
     */
    public void asignarJugadorATutor(int idJugador, int idTutor) throws SQLException {
        String sql = "INSERT INTO tutor_jugador (idTutor, idJugador) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTutor);
            ps.setInt(2, idJugador);
            ps.executeUpdate();
        }
    }

    // ========================================================================================
    // 📦 FUNCIONES RELACIONADAS CON ENTRENADORES
    // ========================================================================================

    /**
     * Devuelve la lista de jugadores asignados a un entrenador.
     *
     * @param idEntrenador ID del entrenador
     * @return Lista de usuarios con rol jugador asignados al entrenador
     * @throws SQLException Si ocurre un error de lectura
     */
    public List<Usuario> obtenerJugadoresPorEntrenador(int idEntrenador) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = """
            SELECT u.* FROM entrenador_jugador ej
            JOIN usuario u ON ej.idJugador = u.idUsuario
            WHERE ej.idEntrenador = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = mapUsuario(rs);
                lista.add(u);
            }
        }

        return lista;
    }

    /**
     * Asocia un jugador a un entrenador.
     *
     * @param idJugador ID del jugador
     * @param idEntrenador ID del entrenador
     * @throws SQLException Si ocurre un error en la inserción
     */
    public void asignarJugadorAEntrenador(int idJugador, int idEntrenador) throws SQLException {
        String sql = "INSERT INTO entrenador_jugador (idEntrenador, idJugador) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            ps.setInt(2, idJugador);
            ps.executeUpdate();
        }
    }

    // ========================================================================================
    // 🔧 MÉTODO UTILITARIO PARA MAPEAR RESULTSET → Usuario
    // ========================================================================================

    /**
     * Mapea un ResultSet a un objeto Usuario.
     *
     * @param rs ResultSet de la consulta
     * @return Usuario completo
     * @throws SQLException Si ocurre un error al acceder a los campos
     */
    private Usuario mapUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getInt("idUsuario"),
            rs.getString("nombre"),
            rs.getString("apellidos"),
            rs.getString("email"),
            rs.getInt("telefono"),
            rs.getString("password"),
            rs.getDate("fechaNacimiento") != null ? rs.getDate("fechaNacimiento").toLocalDate() : null,
            rs.getInt("idGenero"),
            rs.getInt("idCentro"),
            rs.getInt("idDeporte"),
            rs.getString("foto"),
            rs.getInt("idRol"),
            rs.getInt("idTutor")
        );
    }
}

