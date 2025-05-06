/**
 * Representa una boleta para una venta realizada
 */
public class Boleta {
    private Venta venta;

    public Boleta(Venta venta) {
        this.venta = venta;
    }

    public void mostrar() {
        System.out.println("-------------------------------");
        System.out.println("        Teatro Moro           ");
        System.out.println("-------------------------------");
        System.out.println("ID de Venta: " + venta.getId());
        System.out.println("Cliente: " + venta.getCliente().getNombre());
        System.out.println("Ubicación: " + venta.getAsiento().getTipo());
        System.out.println("Asiento: " + venta.getAsiento().getUbicacion());
        System.out.println("Fecha: " + venta.getFechaVenta());
        System.out.println("Costo Base: $" + String.format("%.1f", venta.getPrecioBase()));

        // Mostrar el porcentaje correcto
        int porcentajeDescuento = Math.round(venta.getDescuento().getValor() * 100);
        System.out.println("Descuento Aplicado: " + porcentajeDescuento + "%");

        System.out.println("Costo Final: $" + String.format("%.1f", venta.getPrecioFinal()));
        System.out.println("-------------------------------");
        System.out.println("Gracias por su visita al Teatro Moro");
        System.out.println("-------------------------------");
    }
}