package __71.back_CAVlink.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Modelo de producto.
 * Representa un artículo disponible en el catálogo con precio y stock.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Producto {

    private static final AtomicLong COUNTER = new AtomicLong(1);

    private Long id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private double precio;
    private int cantidad;

    public Producto() {}

    public Producto(String nombre, String categoria, String descripcion, double precio, int cantidad) {
        this.id = COUNTER.getAndIncrement();
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
