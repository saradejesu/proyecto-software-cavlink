package __71.back_CAVlink.controller;

import __71.back_CAVlink.model.Pedido;
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
 * File: PedidoController.java
 * Controlador: Gestiona registro y consulta de pedidos/carrito
 * Funcionalidad: Registrar pedido y consultar pedidos por usuario
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private JsonStorage storage;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Pedido pedido) throws IOException {
        if (!isValidPedido(pedido)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pedido inválido");
        JsonStorage.RootData data = storage.readAll();
        if (pedido.getIdPedido() == null) pedido.setIdPedido(System.currentTimeMillis());
        data.pedidos.add(pedido);
        // disminuir stock de productos
        for (Producto prod : pedido.getProductos()) {
            data.productos.stream().filter(p -> p.getId().equals(prod.getId())).findFirst().ifPresent(p -> p.setCantidad(p.getCantidad() - prod.getCantidad()));
        }
        storage.writeAll(data);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> porUsuario(@PathVariable Long idUsuario) throws IOException {
        JsonStorage.RootData data = storage.readAll();
        List<Pedido> pedidos = data.pedidos.stream().filter(p -> idUsuario.equals(p.getIdUsuario())).toList();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<?> getById(@PathVariable Long idPedido) throws IOException {
        JsonStorage.RootData data = storage.readAll();
        Optional<Pedido> found = data.pedidos.stream().filter(p -> idPedido.equals(p.getIdPedido())).findFirst();
        return found.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado"));
    }

    private boolean isValidPedido(Pedido pedido) {
        if (pedido == null) return false;
        if (pedido.getIdUsuario() == null) return false;
        if (pedido.getProductos() == null || pedido.getProductos().isEmpty()) return false;
        if (pedido.getTotal() < 0) return false;
        return true;
    }
}
