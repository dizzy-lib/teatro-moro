/**
 * Enumera los tipos de asientos disponibles en el teatro con sus precios base
 */
public enum TipoAsiento {
    VIP(1000),
    PLATEA(1500),
    BALCON(500);

    private final float precio;

    TipoAsiento(float precio) {
        this.precio = precio;
    }

    public float getPrecio() {
        return this.precio;
    }
}