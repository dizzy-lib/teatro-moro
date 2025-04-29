package sistemaVentas;

public class Venta {
    public Asiento asiento;
    public TipoAsiento ubicacion;
    public float costoFinal;
    public float descuentoAplicado;
    public float precioBase;

    Boleta boleta;

    public Venta(Asiento asiento, float descuentoAplicado, float precioBase, float costoFinal) {
        this.asiento = asiento;
        this.ubicacion = asiento.getTipo();
        this.descuentoAplicado = descuentoAplicado;
        this.precioBase = precioBase;
        this.costoFinal = costoFinal;
    }

    public Boleta generarBoleta() {
        return new Boleta(this.asiento, this.descuentoAplicado, this.precioBase, this.costoFinal);
    }
}