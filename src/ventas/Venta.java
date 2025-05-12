package ventas;
import enums.PreciosAsientos;

public class Venta {
    private final Cotizacion cotizacion;
    private final String fechaVenta;

    /**
     * Constructor que genera una instancia de una venta dada una cotización
     * @param cotizacion cotización que se desea convertir en venta
     */
    public Venta(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
        // obtiene la fecha actual
        this.fechaVenta = java.time.OffsetDateTime.now().toString();
    }

    /**
     * Imprime una boleta con formato ordenado y claro.
     */
    public void imprimirBoleta() {
        // Obtener datos necesarios
        String nombreCliente = this.cotizacion.obtenerCliente().obtenerNombre();
        PreciosAsientos tipoAsiento = this.cotizacion.obtenerAsiento().obtenerTipo();
        String codigoAsiento = this.cotizacion.obtenerAsiento().obtenerCodigo();
        double precioOriginal = this.cotizacion.obtenerPrecioOriginal();
        double descuento = this.cotizacion.obtenerDescuento();
        double precioFinal = this.cotizacion.obtenerPrecioVenta();

        // Constantes para el formato
        final String LINEA = "-------------------------------------";
        final String LINEA_DOBLE = "=====================================";

        // Imprimir boleta
        System.out.println(LINEA_DOBLE);
        System.out.println("           TEATRO MORO           ");
        System.out.println("        BOLETA DE ENTRADA        ");
        System.out.println(LINEA_DOBLE);

        // Datos del cliente y asiento
        System.out.printf("%-15s %s\n", "Cliente:", nombreCliente);
        System.out.printf("%-15s %s\n", "Tipo Asiento:", tipoAsiento);
        System.out.printf("%-15s %s\n", "Asiento:", codigoAsiento);
        System.out.printf("%-15s %s\n", "Fecha:", fechaVenta);
        System.out.printf("%-15s %s\n", "Función:", "20:00 hrs");

        System.out.println(LINEA);

        // Detalle del pago
        System.out.printf("%-25s $%.0f\n", "Precio Normal:", precioOriginal);
        if (descuento > 0) {
            System.out.printf("%-25s $%.0f\n", "Descuento Aplicado:", descuento);
        }

        System.out.println(LINEA);
        System.out.printf("%-25s $%.0f\n", "TOTAL A PAGAR:", precioFinal);
        System.out.println(LINEA_DOBLE);

        // Mensaje final
        System.out.println("   Gracias por su visita al Teatro Moro");
        System.out.println("        www.teatromoro.cl");
        System.out.println(LINEA_DOBLE);
    }

    /**
     * Obtiene la cotización de la venta
     * @return cotización de la venta
     */
    public Cotizacion obtenerCotizacion() {
        return cotizacion;
    }
}
