package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DaoGenero {
    private Connection conn;

    public DaoGenero() {
        conn = bd.getInstance().getConnection();
    }

    public ArrayList<Genero> listar() {
        ArrayList<Genero> lista = new ArrayList<>();

        String sql = "SELECT * FROM genero ORDER BY nombre";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Genero g = new Genero();
                g.setId(rs.getInt("idGenero"));
                g.setNombre(rs.getString("nombre"));
                lista.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

	public List<Genero> listarGeneros() {
		// TODO Auto-generated method stub
		return null;
	}
}

