import java.time.LocalDateTime;

/**
 * Representa una venta de entrada en el teatro
 */
public class Venta {
    private int id;
    private Asiento asiento;
    private Cliente cliente;
    private LocalDateTime fechaVenta;
    private float precioBase;
    private TipoDescuento descuento;
    private float precioFinal;

    public Venta(int id, Asiento asiento, Cliente cliente) {
        this.id = id;
        this.asiento = asiento;
        this.cliente = cliente;
        this.fechaVenta = LocalDateTime.now();
        this.precioBase = asiento.getTipo().getPrecio();
        this.descuento = cliente.obtenerTipoDescuento();
        this.precioFinal = this.precioBase * (1 - this.descuento.getValor());
    }

    // Getters
    public int getId() { return id; }
    public Asiento getAsiento() { return asiento; }
    public Cliente getCliente() { return cliente; }
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public float getPrecioBase() { return precioBase; }
    public TipoDescuento getDescuento() { return descuento; }
    public float getPrecioFinal() { return precioFinal; }

    // Método para generar una boleta
    public Boleta generarBoleta() {
        return new Boleta(this);
    }
}