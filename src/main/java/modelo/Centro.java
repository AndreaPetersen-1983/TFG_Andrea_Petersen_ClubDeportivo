package modelo;

public class Centro {
    private int idCentro;
    private String nombre;

    public Centro() {}

    public Centro(int idCentro, String nombre) {
        this.idCentro = idCentro;
        this.nombre = nombre;
    }

    public int getId() {
        return idCentro;
    }

    public void setId(int idCentro) {
        this.idCentro = idCentro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
