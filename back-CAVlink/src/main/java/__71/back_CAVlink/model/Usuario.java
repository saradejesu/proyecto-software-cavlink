package __71.back_CAVlink.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Modelo de usuario.
 * Contiene datos básicos del usuario: id, correo, nombre, contraseña y rol.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {

    // Contador local para generar IDs si es necesario
    private static final AtomicLong COUNTER = new AtomicLong(1);

    private Long id;
    private String correo;
    private String nombre;
    private String contraseña;
    private String rol; // USUARIO | ADMIN | TECNICO

    public Usuario() {
        // constructor vacío para Jackson
    }

    public Usuario(String correo, String nombre, String contraseña, String rol) {
        this.id = COUNTER.getAndIncrement();
        this.correo = correo;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
