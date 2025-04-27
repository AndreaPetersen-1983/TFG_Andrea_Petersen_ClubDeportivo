package modelo;

public class Deporte {
    private int idDeporte;
    private String nombre;

    public Deporte() {}

    public Deporte(int idDeporte, String nombre) {
        this.idDeporte = idDeporte;
        this.nombre = nombre;
    }

    public int getId() {
        return idDeporte;
    }

    public void setId(int idDeporte) {
        this.idDeporte = idDeporte;
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
