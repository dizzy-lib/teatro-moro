package sistemaVentas;

public class Entrada {
    private Asiento asiento;
    private String codigoDescuento;

    public Entrada(
            Asiento asiento
    ) {
        this.asiento = asiento;
    }

    public void setCodigoDescuento(String codigoDescuento) {
        this.codigoDescuento = codigoDescuento;
    }

    public String getCodigoDescuento() {
        return codigoDescuento;
    }

    public Asiento getAsiento() {
        return asiento;
    }
}
