package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
* Clase para manejar la conexión a la base de datos.
* Implementa el patrón Singleton para asegurar que solo exista una instancia de la conexión a la base de datos.
*/
public class bd {
	
	
	public static bd instance;
	private static final String JDBC_URL ="jdbc:mysql://localhost:3306/tfg";
	private static final String USUARIO ="root";//usuario del xampp
	private static final String CONTRASENIA ="";//pass del xampp
	private Connection conn;
	
	/**
     * Constructor privado para evitar la creación de múltiples instancias.
     * Establece la conexión a la base de datos al crear la primera instancia.
     */
	private bd () {
		// Connecta al crear la primera instància
		conectar(); 
		
		
	}

	// patron Singelton, lo utilizo para cuando ya existe una isntacnia, la comprueba y la reutiliza
	/**
     * Obtiene la instancia única de la clase bd.
     * Si no existe una instancia, la crea y la devuelve.
     *
     * @return La instancia única de la clase bd.
     */
	public static bd getInstance() {
		if(instance == null) {
			instance = new bd();
		}
		return instance;
	}
	
	/**
     * Establece la conexión a la base de datos.
     * Utiliza el controlador JDBC para MySQL.
     */

	private void conectar() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(JDBC_URL,USUARIO,CONTRASENIA); 
			System.out.println("Conexion a la base de datos establecida correctamente. Andrea");
			
		}catch(SQLException | ClassNotFoundException e){
			//System.out.println(e.getMessage());
			System.out.println("Error al conectar la bd: +"+e.getMessage());
			
		}
	}
	/**
     * Obtiene la conexión actual a la base de datos.
     * Si no hay una conexión establecida, la crea.
     *
     * @return La conexión a la base de datos.
     */
	public Connection getConnection() {
		if(conn == null) {
			conectar();
		}
		
		return conn;
	}
	
	/**
     * Cierra la conexión a la base de datos si está abierta.
     */
	public void close () {
		
		try {
			if(conn != null) conn.close();
			
		}catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
     * Ejecuta una sentencia SQL de actualización (INSERT, UPDATE, DELETE).
     *
     * @param sql La sentencia SQL a ejecutar.
     */
	
	 public void update(String sql) {
	        try (Statement stmt = conn.createStatement()) {
	            stmt.executeUpdate(sql);
	            
	            
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            try {
	                conn.rollback(); // Devuleve en caso de error como esta antes. Andrea
	            } catch (SQLException ex) {
	                System.out.println("Error en fer rollback.");
	                ex.printStackTrace();
	            }
	        }
	    }
	    // Se usa para realizar consultas con la bd y la vista
	 	/**
	     * Ejecuta una consulta SQL y devuelve los resultados como una lista de mapas.
	     *
	     * @param sql La consulta SQL a ejecutar.
	     * @return Una lista de mapas, donde cada mapa representa una fila y cada entrada en el mapa representa una columna.
	     */
	 public List<Map<String, Object>> query(String sql) {
	        List<Map<String, Object>> resultList = new ArrayList<>();

	        try (Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {
	            
	            ResultSetMetaData metaData = rs.getMetaData();
	            int columnCount = metaData.getColumnCount();
	            
	            while (rs.next()) {
	                Map<String, Object> row = new HashMap<>();
	                for (int i = 1; i <= columnCount; i++) {
	                    row.put(metaData.getColumnName(i), rs.getObject(i));
	                }
	                resultList.add(row);
	            }
	            System.out.println("Consulta realizada: " + sql);
	        } catch (SQLException e) {
	            System.out.println("Error en la consulta: " + e.getMessage());
	        }
	        return resultList;
	    }


	

		
	

}
