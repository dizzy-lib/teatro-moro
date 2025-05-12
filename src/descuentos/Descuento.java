package descuentos;

public class Descuento {
    private final String codigoDescuento;
    private final float tasaDescuento;

    /**
     * Clase constructora que crea un descuento
     * @param codigoDescuento indica el nombre identificador del descuento
     * @param tasaDescuento indica la tasa de descuento
     */
    public Descuento(String codigoDescuento, float tasaDescuento) {
        this.codigoDescuento = codigoDescuento;
        this.tasaDescuento = tasaDescuento;
    }

    /**
     * Obtiene el código del descuento
     * @return código de descuento
     */
    public String obtenerCodigoDescuento() {
        return codigoDescuento;
    }

    /**
     * Obtiene la tasa del descuento
     * @return tasa del descuento
     */
    public float obtenerTasaDescuento() {
        return tasaDescuento;
    }
}
