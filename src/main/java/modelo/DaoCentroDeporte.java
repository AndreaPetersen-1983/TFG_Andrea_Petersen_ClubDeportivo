package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO que gestiona las consultas de centros y sus deportes asociados.
 * Usa conexión compartida desde la clase bd.
 * 
 * @author Andrea
 * @since 2025-04-21
 */
public class DaoCentroDeporte {
    private Connection conn;

    /**
     * Constructor que establece la conexión a la base de datos
     */
    public DaoCentroDeporte() {
        conn = bd.getInstance().getConnection();
        System.out.println("🔌 DaoCentroDeporte: conexión establecida: " + (conn != null));
    }

    /**
     * Devuelve todos los centros educativos disponibles en el sistema
     *
     * @return lista de objetos Centro
     * @throws SQLException en caso de fallo de acceso
     */
    public List<Centro> listarCentros() throws SQLException {
        List<Centro> centros = new ArrayList<>();
        String sql = "SELECT * FROM centro ORDER BY nombre";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                centros.add(new Centro(rs.getInt("idCentro"), rs.getString("nombre")));
            }
        }
        return centros;
    }

    /**
     * Devuelve todos los deportes disponibles para un centro específico
     *
     * @param idCentro ID del centro a consultar
     * @return lista de objetos Deporte
     * @throws SQLException si hay un error de acceso a datos
     */
    public List<Deporte> deportesPorCentro(int idCentro) throws SQLException {
        List<Deporte> deportes = new ArrayList<>();
        String sql = """
            SELECT d.idDeporte, d.nombre
            FROM centro_deporte cd
            JOIN deporte d ON cd.idDeporte = d.idDeporte
            WHERE cd.idCentro = ?
            ORDER BY d.nombre
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCentro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                deportes.add(new Deporte(rs.getInt("idDeporte"), rs.getString("nombre")));
            }
        }

        return deportes;
    }

    /**
     * Devuelve todos los deportes del sistema (sin filtrar por centro)
     *
     * @return lista de objetos Deporte
     * @throws SQLException si hay un error en la base de datos
     */
    public List<Deporte> listarDeportes() throws SQLException {
        List<Deporte> deportes = new ArrayList<>();
        String sql = "SELECT * FROM deporte ORDER BY nombre";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                deportes.add(new Deporte(rs.getInt("idDeporte"), rs.getString("nombre")));
            }
        }
        return deportes;
    }
}
