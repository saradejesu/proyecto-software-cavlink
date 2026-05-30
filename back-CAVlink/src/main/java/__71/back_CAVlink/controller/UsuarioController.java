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
 * Controlador de usuarios.
 * Maneja registro, login y consulta de usuarios con validaciones de backend.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:3000"})
public class UsuarioController {

    @Autowired
    private JsonStorage storage;

    private final Pattern emailPattern = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private final Pattern namePattern = Pattern.compile("^[A-Za-zÀ-ÿ0-9 ]+$");

    // Crear usuario (registro). Solo rol USUARIO puede registrarse vía frontend
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario u) throws IOException {
        if (u == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos de usuario inválidos");
        }
        if (!isValidUsuario(u, false)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos de usuario inválidos");
        }
        JsonStorage.RootData data = storage.readAll();
        // Verificar correo duplicado
        boolean correoExistente = data.usuarios.stream().anyMatch(x -> x.getCorreo().equalsIgnoreCase(u.getCorreo()));
        if (correoExistente) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cuenta ya asociada a este correo");
        }
        // Verificar nombre de usuario duplicado
        boolean nombreExistente = data.usuarios.stream().anyMatch(x -> x.getNombre().equalsIgnoreCase(u.getNombre().trim()));
        if (nombreExistente) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya existe");
        }
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
        return found
                .map(u -> ResponseEntity.ok((Object) u))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
    }

    // Util: validar usuario
    private boolean isValidUsuario(Usuario u, boolean isUpdate) {
        if (u == null) return false;
        if (!isUpdate && (u.getRol() == null || u.getRol().isEmpty())) return false;
        if (u.getCorreo() == null || !emailPattern.matcher(u.getCorreo()).matches()) return false;
        if (u.getNombre() == null || u.getNombre().trim().isEmpty()) return false;
        String nombre = u.getNombre().trim();
        if (!namePattern.matcher(nombre).matches()) return false;
        if (nombre.matches("^[0-9 ]+$")) return false; // no solo números
        if (u.getContraseña() == null || u.getContraseña().length() < 4) return false;
        return true;
    }
}
