import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Boleta {
    private final String id;
    private final int salaId;
    private final int fila;
    private final int columna;
    private final LocalDateTime fechaCompra;
    private final double precio;
    private final int cantidad; // Representa el tamaño del grupo, no entradas en el mismo asiento

    public Boleta(int salaId, int fila, int columna, double precio, int cantidad) {
        this.id = UUID.randomUUID().toString().substring(0, 8); // Identificador único corto
        this.salaId = salaId;
        this.fila = fila;
        this.columna = columna;
        this.fechaCompra = LocalDateTime.now();
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public int getSalaId() {
        return salaId;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getTotal() {
        return precio * cantidad;
    }

    public String getFechaCompraFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaCompra.format(formatter);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Boleta de Entrada #").append(id).append("\n\n");
        sb.append("| Concepto         | Detalle                |\n");
        sb.append("|------------------|------------------------|\n");
        sb.append(String.format("| %-16s | %-22d |\n", "Sala", salaId));

        // Aclaración sobre los asientos
        if (cantidad > 1) {
            sb.append(String.format("| %-16s | Fila %-2d, Columna %-5d |\n", "Asiento referencia", fila, columna));
            sb.append(String.format("| %-16s | %-22d |\n", "Total asientos", cantidad));
            sb.append(String.format("| %-16s | %-22s |\n", "Tipo de compra", "Grupal"));
        } else {
            sb.append(String.format("| %-16s | Fila %-2d, Columna %-5d |\n", "Ubicación", fila, columna));
            sb.append(String.format("| %-16s | %-22d |\n", "Cantidad", cantidad));
        }

        sb.append(String.format("| %-16s | $%-21.2f |\n", "Precio unitario", precio));
        sb.append(String.format("| %-16s | $%-21.2f |\n", "Total", getTotal()));
        sb.append(String.format("| %-16s | %-22s |\n", "Fecha", getFechaCompraFormateada()));

        return sb.toString();
    }
}