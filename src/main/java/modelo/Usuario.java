package modelo;

import java.time.LocalDate;

/**
 * Clase que representa a un usuario en el sistema.
 * Puede ser tutor, jugador, entrenador o administrador.
 */
public class Usuario {

    // 🆔 Identificadores
    private int id;
    private int idRol;
    private int idGenero;
    private int idCentro;
    private int idDeporte;
    private int idTutor; // ➕ Solo si es jugador

    // 📄 Datos personales
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private int telefono;
    private LocalDate fechaNacimiento;

    // 📷 Datos adicionales
    private String foto;
    private String nombreRol; // opcional, para mostrar el nombre del rol

    // ===============================
    // ✅ Constructores
    // ===============================

    public Usuario() {
        // Constructor vacío para uso flexible
    }

    public Usuario(int id, String nombre, String apellidos, String email, int telefono, String password,
                   LocalDate fechaNacimiento, int idGenero, int idCentro, int idDeporte, String foto,
                   int idRol, int idTutor) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
        this.idGenero = idGenero;
        this.idCentro = idCentro;
        this.idDeporte = idDeporte;
        this.foto = foto;
        this.idRol = idRol;
        this.idTutor = idTutor;
    }

    public Usuario(int id, String nombre, String apellidos, String email, int telefono, String password,
                   LocalDate fechaNacimiento, int idGenero, int idCentro, int idDeporte, String foto,
                   int idRol, String nombreRol) {
        this(id, nombre, apellidos, email, telefono, password, fechaNacimiento, idGenero, idCentro, idDeporte, foto, idRol, 0);
        this.nombreRol = nombreRol;
    }

    // ===============================
    // ✅ Getters y Setters
    // ===============================

    public Usuario(int int1, String string, String string2, String string3, int int2, String string4, Object object,
			int int3, int int4, int int5, String string5, int int6, int int7, String string6) {
		// TODO Auto-generated constructor stub
	}

	public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }

    public int getIdGenero() { return idGenero; }
    public void setIdGenero(int idGenero) { this.idGenero = idGenero; }

    public int getIdCentro() { return idCentro; }
    public void setIdCentro(int idCentro) { this.idCentro = idCentro; }

    public int getIdDeporte() { return idDeporte; }
    public void setIdDeporte(int idDeporte) { this.idDeporte = idDeporte; }

    public int getIdTutor() { return idTutor; }
    public void setIdTutor(int idTutor) { this.idTutor = idTutor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getTelefono() { return telefono; }
    public void setTelefono(int telefono) { this.telefono = telefono; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }
}
