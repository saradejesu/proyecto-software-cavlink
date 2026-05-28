package __71.back_CAVlink.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * File: Pedido.java
 * Model: Representa un pedido / carrito
 * Funcionalidad: Guarda ID pedido, ID usuario, lista de productos, total y estado
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pedido {

    private static final AtomicLong COUNTER = new AtomicLong(1);

    private Long idPedido;
    private Long idUsuario;
    private List<Producto> productos;
    private double total;
    private String estado; // e.g., PENDIENTE, CONFIRMADO

    public Pedido() {}

    public Pedido(Long idUsuario, List<Producto> productos, double total, String estado) {
        this.idPedido = COUNTER.getAndIncrement();
        this.idUsuario = idUsuario;
        this.productos = productos;
        this.total = total;
        this.estado = estado;
    }

    // Getters y setters
    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
