package __71.back_CAVlink.controller;

import __71.back_CAVlink.model.Producto;
import __71.back_CAVlink.service.JsonStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * File: ProductoController.java
 * Controlador: Gestiona creación y consulta de productos
 * Funcionalidad: Endpoints para CRUD mínimo en productos con validación backend
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private JsonStorage storage;

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Producto p) throws IOException {
        if (!isValidProducto(p)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Producto inválido");
        JsonStorage.RootData data = storage.readAll();
        if (p.getId() == null) p.setId(System.currentTimeMillis());
        data.productos.add(p);
        storage.writeAll(data);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/")
    public ResponseEntity<List<Producto>> listar() throws IOException {
        JsonStorage.RootData data = storage.readAll();
        return ResponseEntity.ok(data.productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws IOException {
        JsonStorage.RootData data = storage.readAll();
        Optional<Producto> found = data.productos.stream().filter(x -> id.equals(x.getId())).findFirst();
        return found.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado"));
    }

    private boolean isValidProducto(Producto p) {
        if (p == null) return false;
        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) return false;
        if (p.getCategoria() == null || p.getCategoria().trim().isEmpty()) return false;
        if (p.getPrecio() < 0) return false;
        if (p.getCantidad() < 0) return false;
        return true;
    }
}
