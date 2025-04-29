package sistemaVentas;

public class Boleta {
    private TipoAsiento tipoAsiento;
    private float descuentoAplicado;
    private float precioBase;
    private float costoFinal;

    public Boleta(Asiento asiento, float descuentoAplicado, float precioBase, float costoFinal) {
        this.tipoAsiento = asiento.getTipo();
        this.descuentoAplicado = descuentoAplicado;
        this.precioBase = precioBase;
        this.costoFinal = costoFinal;
    }

    public void mostrarBoleta() {
        System.out.println("-------------------------------");
        System.out.println("        Teatro Moro           ");
        System.out.println("-------------------------------");
        System.out.println("Ubicación: " + this.tipoAsiento);
        System.out.println("Costo Base: $" + String.format("%.1f", this.precioBase));

        // Mostrar el porcentaje correcto (convertir el decimal a porcentaje)
        int porcentajeDescuento = Math.round(this.descuentoAplicado * 100);
        System.out.println("Descuento Aplicado: " + porcentajeDescuento + "%");

        System.out.println("Costo Final: $" + String.format("%.1f", this.costoFinal));
        System.out.println("-------------------------------");
        System.out.println("Gracias por su visita al Teatro Moro");
        System.out.println("-------------------------------");
    }
}