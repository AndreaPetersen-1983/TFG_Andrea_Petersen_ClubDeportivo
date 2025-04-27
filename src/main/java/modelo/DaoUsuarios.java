package modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * DaoUsuarios gestiona todas las operaciones de base de datos para la entidad Usuario.
 * Utiliza el patrón Singleton para asegurar una única instancia.
 * Realiza operaciones CRUD, búsquedas, inserciones con retorno de ID y conversión a JSON.
 * 
 * Autor: TuNombre
 * Fecha: 21/04/2025
 */
public class DaoUsuarios {
    private Connection conn;
    private static DaoUsuarios instance;

    /**
     * Constructor privado. Inicializa la conexión a la base de datos.
     */
    private DaoUsuarios() {
        conn = bd.getInstance().getConnection();
    }

    /**
     * Obtiene la instancia única del DAO (patrón Singleton).
     *
     * @return Instancia única de DaoUsuarios
     * @throws SQLException Si ocurre un error de conexión
     */
    public static DaoUsuarios getInstance() throws SQLException {
        if (instance == null) {
            instance = new DaoUsuarios();
        }
        return instance;
    }

    // ============================================================================
    // 🟩 INSERCIÓN DE USUARIOS
    // ============================================================================

    /**
     * Inserta un nuevo usuario completo en la base de datos.
     *
     * @param u Objeto Usuario con los datos completos
     * @throws SQLException Si ocurre un error de base de datos
     */
    public void insertar(Usuario u) throws SQLException {
        String sql = """
            INSERT INTO usuario 
            (nombre, apellidos, email, telefono, password, fechaNacimiento, idGenero, idCentro, idDeporte, foto, idRol, idTutor) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getEmail());
            ps.setInt(4, u.getTelefono());
            ps.setString(5, u.getPassword());
            ps.setDate(6, u.getFechaNacimiento() != null ? Date.valueOf(u.getFechaNacimiento()) : null);
            ps.setInt(7, u.getIdGenero());
            ps.setInt(8, u.getIdCentro());
            ps.setInt(9, u.getIdDeporte());
            ps.setString(10, u.getFoto());
            ps.setInt(11, u.getIdRol());
            ps.setInt(12, u.getIdTutor());
            ps.executeUpdate();
        }
    }

    /**
     * Inserta un usuario y devuelve el ID generado automáticamente.
     *
     * @param u Usuario a insertar
     * @return ID generado por la base de datos
     * @throws SQLException Si ocurre un error
     */
    public int insertarYDevolverID(Usuario u) throws SQLException {
        String sql = """
            INSERT INTO usuario 
            (nombre, apellidos, email, telefono, password, fechaNacimiento, idGenero, idCentro, idDeporte, foto, idRol) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getEmail());
            ps.setInt(4, u.getTelefono());
            ps.setString(5, u.getPassword());
            ps.setDate(6, u.getFechaNacimiento() != null ? Date.valueOf(u.getFechaNacimiento()) : null);
            ps.setInt(7, u.getIdGenero());
            ps.setInt(8, u.getIdCentro());
            ps.setInt(9, u.getIdDeporte());
            ps.setString(10, u.getFoto());
            ps.setInt(11, u.getIdRol());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            else throw new SQLException("No se pudo obtener el ID generado.");
        }
    }

    // ============================================================================
    // 🟦 CONSULTAS
    // ============================================================================

    /**
     * Devuelve un usuario por su email y contraseña (para login).
     */
    public Usuario obtenerPorEmailYPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapUsuario(rs);
        }
        return null;
    }

    /**
     * Devuelve un usuario solo por su email (comprobación de duplicado).
     */
    public Usuario obtenerPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapUsuario(rs);
        }
        return null;
    }

    /**
     * Obtiene un usuario por ID.
     */
    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapUsuario(rs);
        }
        return null;
    }

    /**
     * Lista todos los usuarios con el nombre de su rol asociado.
     */
    public ArrayList<Usuario> listar() throws SQLException {
        String sql = """
            SELECT u.*, r.nombreRol 
            FROM usuario u 
            JOIN rol r ON u.idRol = r.idRol
            """;
        ArrayList<Usuario> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario(
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
                    rs.getInt("idTutor"),
                    rs.getString("nombreRol")
                );
                lista.add(u);
            }
        }

        return lista;
    }

    /**
     * Devuelve la lista de usuarios en formato JSON.
     */
    public String dameJson() throws SQLException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new GsonLocalDateAdapter())
                .create();
        return gson.toJson(this.listar());
    }

    // ============================================================================
    // 🟥 ACTUALIZACIÓN Y BORRADO
    // ============================================================================

    /**
     * Elimina un usuario por ID.
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE idUsuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Actualiza los datos de un usuario en la base de datos.
     */
    public void update(Usuario u) throws SQLException {
        String sql = """
            UPDATE usuario 
            SET nombre=?, apellidos=?, email=?, telefono=?, password=?, fechaNacimiento=?, idGenero=?, idCentro=?, idDeporte=?, foto=?, idRol=? 
            WHERE idUsuario=?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getEmail());
            ps.setInt(4, u.getTelefono());
            ps.setString(5, u.getPassword());
            ps.setDate(6, u.getFechaNacimiento() != null ? Date.valueOf(u.getFechaNacimiento()) : null);
            ps.setInt(7, u.getIdGenero());
            ps.setInt(8, u.getIdCentro());
            ps.setInt(9, u.getIdDeporte());
            ps.setString(10, u.getFoto());
            ps.setInt(11, u.getIdRol());
            ps.setInt(12, u.getId());
            ps.executeUpdate();
        }
    }

    // ============================================================================
    // 🛠️ UTILIDADES
    // ============================================================================

    /**
     * Mapea un ResultSet a un objeto Usuario.
     *
     * @param rs ResultSet con los datos de la consulta
     * @return Objeto Usuario completo
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

    /**
     * Devuelve el ID de un deporte a partir de su nombre (case-insensitive).
     *
     * @param nombre Nombre del deporte
     * @return ID correspondiente
     * @throws SQLException Si el deporte no existe
     */
    public int obtenerIdDeportePorNombre(String nombre) throws SQLException {
        String sql = "SELECT idDeporte FROM deporte WHERE LOWER(nombre) = LOWER(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("idDeporte");
            else throw new SQLException("No se encontró el deporte: " + nombre);
        }
    }
}

