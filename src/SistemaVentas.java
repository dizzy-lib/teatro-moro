import java.util.ArrayList;
import java.util.Optional;

/**
 * Sistema para la gestión de ventas del teatro
 * Utiliza ArrayList para las ventas ya que su cantidad puede crecer indefinidamente
 */
public class SistemaVentas {
    // ArrayList para almacenar las ventas
    private ArrayList<Venta> ventas;
    private SistemaAsientos sistemaAsientos;
    private int ultimoId;

    public SistemaVentas(SistemaAsientos sistemaAsientos) {
        this.ventas = new ArrayList<>();
        this.sistemaAsientos = sistemaAsientos;
        this.ultimoId = 0;
    }

    // Método para realizar una venta
    public Optional<Venta> realizarVenta(Asiento asiento, Cliente cliente) {
        if (asiento.getEstado() != EstadoAsiento.OCUPADO) {
            Venta venta = new Venta(++ultimoId, asiento, cliente);
            sistemaAsientos.marcarAsientoComoOcupado(asiento);
            ventas.add(venta);
            return Optional.of(venta);
        }

        return Optional.empty();
    }

    // Método para obtener todas las ventas
    public ArrayList<Venta> obtenerVentas() {
        return new ArrayList<>(ventas);
    }

    // Método para obtener ventas por tipo de asiento
    public ArrayList<Venta> obtenerVentasPorTipo(TipoAsiento tipo) {
        ArrayList<Venta> ventasFiltradas = new ArrayList<>();

        for (Venta venta : ventas) {
            if (venta.getAsiento().getTipo() == tipo) {
                ventasFiltradas.add(venta);
            }
        }

        return ventasFiltradas;
    }

    // Método para calcular ingresos totales
    public float calcularIngresos() {
        float total = 0;

        for (Venta venta : ventas) {
            total += venta.getPrecioFinal();
        }

        return total;
    }

    // Método para calcular ingresos por tipo de asiento
    public float calcularIngresosPorTipo(TipoAsiento tipo) {
        float total = 0;

        for (Venta venta : ventas) {
            if (venta.getAsiento().getTipo() == tipo) {
                total += venta.getPrecioFinal();
            }
        }

        return total;
    }

    // Método para mostrar las boletas de todas las ventas
    public void mostrarBoletasVentas() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        System.out.println("\n\n--------------------------------");
        System.out.println("     Historial de Boletas      ");
        System.out.println("--------------------------------");
        System.out.println("Total de boletas: " + ventas.size());

        for (int i = 0; i < ventas.size(); i++) {
            System.out.println("\nBoleta #" + (i + 1) + ":");
            ventas.get(i).generarBoleta().mostrar();
        }
    }
}