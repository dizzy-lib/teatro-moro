import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Sistema para generar estadísticas del teatro
 */
public class SistemaEstadisticas {
    private SistemaAsientos sistemaAsientos;
    private SistemaVentas sistemaVentas;

    public SistemaEstadisticas(SistemaAsientos sistemaAsientos, SistemaVentas sistemaVentas) {
        this.sistemaAsientos = sistemaAsientos;
        this.sistemaVentas = sistemaVentas;
    }

    // Método para calcular la ocupación total
    public float calcularOcupacion() {
        int totalAsientos = sistemaAsientos.getCapacidadTotal();
        int asientosOcupados = 0;

        // Contar asientos ocupados por tipo
        for (Asiento asiento : sistemaAsientos.getAsientosVIP()) {
            if (asiento.getEstado() == EstadoAsiento.OCUPADO) {
                asientosOcupados++;
            }
        }

        for (Asiento asiento : sistemaAsientos.getAsientosPlatea()) {
            if (asiento.getEstado() == EstadoAsiento.OCUPADO) {
                asientosOcupados++;
            }
        }

        for (Asiento asiento : sistemaAsientos.getAsientosBalcon()) {
            if (asiento.getEstado() == EstadoAsiento.OCUPADO) {
                asientosOcupados++;
            }
        }

        return (float) asientosOcupados / totalAsientos * 100;
    }

    // Método para calcular la ocupación por tipo de asiento
    public float calcularOcupacionPorTipo(TipoAsiento tipo) {
        Asiento[] asientos;

        switch (tipo) {
            case VIP:
                asientos = sistemaAsientos.getAsientosVIP();
                break;
            case PLATEA:
                asientos = sistemaAsientos.getAsientosPlatea();
                break;
            case BALCON:
                asientos = sistemaAsientos.getAsientosBalcon();
                break;
            default:
                throw new IllegalArgumentException("Tipo de asiento no válido");
        }

        int totalAsientosTipo = asientos.length;
        int asientosOcupadosTipo = 0;

        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == EstadoAsiento.OCUPADO) {
                asientosOcupadosTipo++;
            }
        }

        return (float) asientosOcupadosTipo / totalAsientosTipo * 100;
    }

    // Método para calcular la venta promedio
    public float calcularVentaPromedio() {
        ArrayList<Venta> ventas = sistemaVentas.obtenerVentas();

        if (ventas.isEmpty()) {
            return 0;
        }

        float totalIngresos = sistemaVentas.calcularIngresos();
        return totalIngresos / ventas.size();
    }

    // Método para obtener el tipo de asiento más vendido
    public TipoAsiento obtenerAsientosMasVendidos() {
        int ventasVIP = sistemaVentas.obtenerVentasPorTipo(TipoAsiento.VIP).size();
        int ventasPlatea = sistemaVentas.obtenerVentasPorTipo(TipoAsiento.PLATEA).size();
        int ventasBalcon = sistemaVentas.obtenerVentasPorTipo(TipoAsiento.BALCON).size();

        if (ventasVIP >= ventasPlatea && ventasVIP >= ventasBalcon) {
            return TipoAsiento.VIP;
        } else if (ventasPlatea >= ventasVIP && ventasPlatea >= ventasBalcon) {
            return TipoAsiento.PLATEA;
        } else {
            return TipoAsiento.BALCON;
        }
    }

    // Método para mostrar un resumen de las estadísticas
    public void mostrarResumen() {
        System.out.println("\n\n--------------------------------");
        System.out.println("       Estadísticas Teatro     ");
        System.out.println("--------------------------------");
        System.out.println("Ocupación total: " + String.format("%.1f", calcularOcupacion()) + "%");

        for (TipoAsiento tipo : TipoAsiento.values()) {
            System.out.println("Ocupación " + tipo + ": " +
                    String.format("%.1f", calcularOcupacionPorTipo(tipo)) + "%");
        }

        System.out.println("Venta promedio: $" + String.format("%.1f", calcularVentaPromedio()));

        TipoAsiento tipoMasVendido = obtenerAsientosMasVendidos();
        System.out.println("Tipo de asiento más vendido: " + tipoMasVendido);

        System.out.println("Ingresos totales: $" + String.format("%.1f", sistemaVentas.calcularIngresos()));

        for (TipoAsiento tipo : TipoAsiento.values()) {
            System.out.println("Ingresos " + tipo + ": $" +
                    String.format("%.1f", sistemaVentas.calcularIngresosPorTipo(tipo)));
        }

        System.out.println("--------------------------------");
        System.out.println("Asientos disponibles por tipo:");
        for (TipoAsiento tipo : TipoAsiento.values()) {
            System.out.println(tipo + ": " + sistemaAsientos.contarAsientosDisponibles(tipo));
        }
        System.out.println("--------------------------------");
    }

    // Método para obtener un mapa con la disponibilidad por tipo
    public Map<TipoAsiento, Integer> obtenerDisponibilidadPorTipo() {
        Map<TipoAsiento, Integer> disponibilidad = new HashMap<>();

        for (TipoAsiento tipo : TipoAsiento.values()) {
            disponibilidad.put(tipo, sistemaAsientos.contarAsientosDisponibles(tipo));
        }

        return disponibilidad;
    }
}