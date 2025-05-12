package ventas;

import client.Client;
import descuentos.SistemaDescuento;
import enums.EstadoAsiento;
import teatro.Asiento;

import java.util.ArrayList;
import java.util.List;

public class SistemaVentas {
    // Inicializamos una lista dinámica de ventas para almacenar en el sistema
    private final List<Venta> ventas = new ArrayList<Venta>();
    private final SistemaDescuento sistemaDescuento;

    /**
     * Constructor para crear una instancia independiente
     * del sistema de venta para el teatro
     * @param sistemaDescuento sistema de descuento que se utilizará en el sistema de venta
     */
    public SistemaVentas(SistemaDescuento sistemaDescuento) {
        this.sistemaDescuento = sistemaDescuento;
    }

    /**
     * Genera la cotización para un cliente y dado un asiento seleccionado por el mismo
     * @param cliente cliente registrado
     * @param asiento asiento seleccionado por el cliente
     * @return cotización lista
     */
    public Cotizacion generarCotizacion(Client cliente, Asiento asiento) {
        return new Cotizacion(cliente, asiento, sistemaDescuento);
    }

    /**
     * Genera una venta con base en una cotización existente
     * @param cotizacion cotización generada aceptada por el cliente
     */
    public void generarVenta(Cotizacion cotizacion) {
        // genera una instancia de una venta dentro del sistema
        Venta ventaGenerada = new Venta(cotizacion);

        // Marca el asiento reservado de la cotización a ocupado
        cotizacion.obtenerAsiento().setearEstado(EstadoAsiento.OCUPADO);

        // Imprime la boleta de la venta generada
        ventaGenerada.imprimirBoleta();

        // Agrega instancia de la venta a la lista de ventas
        ventas.add(ventaGenerada);
    }

    /**
     * Método que obtiene el historico de ventas realizadas por el sistema
     * @return lista de ventas
     */
    public List<Venta> obtenerHistorialVentas() {
        return ventas;
    }

    /**
     * Método auxiliar que funciona como wrapper o envoltorio del sistema de descuento
     * entrega la tasa de descuento para un código de descuento presente configurado dentro del sistema
     * @param codigoDescuento código de descuento a consultar
     * @return tasa de descuento encontrado dentro del sistema
     */
    public float calcularDescuentosPorTipo(String codigoDescuento) {
        float descuento = 0;

        try {
            descuento = this.sistemaDescuento.obtenerTasaDescuento(codigoDescuento);
        } catch (Exception e) {
            System.out.println("Error al obtener información de el código de descuento: " + codigoDescuento);
        }

        return descuento;
    }
}
