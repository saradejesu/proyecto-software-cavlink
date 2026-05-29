package __71.back_CAVlink.controller;

import __71.back_CAVlink.model.Usuario;
import __71.back_CAVlink.service.JsonStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * File: UsuarioController.java
 * Controlador: Gestiona creación, consulta y login de usuarios
 * Funcionalidad: Endpoints REST con validación de backend antes de persistir
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class UsuarioController {

    @Autowired
    private JsonStorage storage;

    private final Pattern emailPattern = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    // Crear usuario (registro). Solo rol USUARIO puede registrarse vía frontend
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario u) throws IOException {
        // Backend validation
        if (!isValidUsuario(u, false)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos de usuario inválidos");
        }
        JsonStorage.RootData data = storage.readAll();
        // Asignar ID simple si viene nulo
        if (u.getId() == null) u.setId(System.currentTimeMillis());
        data.usuarios.add(u);
        storage.writeAll(data);
        return ResponseEntity.ok(u);
    }

    // Login: busca por correo y contraseña
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario credentials) throws IOException {
        if (credentials.getCorreo() == null || credentials.getContraseña() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan credenciales");

        JsonStorage.RootData data = storage.readAll();
        Optional<Usuario> found = data.usuarios.stream()
                .filter(x -> credentials.getCorreo().equals(x.getCorreo()) && credentials.getContraseña().equals(x.getContraseña()))
                .findFirst();
        if (found.isPresent()) return ResponseEntity.ok(found.get());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    // Obtener perfil por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws IOException {
        JsonStorage.RootData data = storage.readAll();
        Optional<Usuario> found = data.usuarios.stream().filter(u -> id.equals(u.getId())).findFirst();
        return found.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
    }

    // Util: validar usuario
    private boolean isValidUsuario(Usuario u, boolean isUpdate) {
        if (u == null) return false;
        if (!isUpdate && (u.getRol() == null || u.getRol().isEmpty())) return false;
        if (u.getCorreo() == null || !emailPattern.matcher(u.getCorreo()).matches()) return false;
        if (u.getNombre() == null || u.getNombre().trim().isEmpty()) return false;
        if (u.getContraseña() == null || u.getContraseña().length() < 4) return false;
        return true;
    }
}
