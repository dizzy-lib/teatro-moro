package enums;

public enum PreciosAsientos {
    VIP(45000, "Asientos premium con la mejor vista y comodidad"),
    PALCO(35000, "Ubicación exclusiva con excelente vista lateral"),
    PLATEA_BAJA(25000, "Buena ubicación central con visibilidad óptima"),
    PLATEA_ALTA(18000, "Ubicación central en nivel superior"),
    GALERIA(12000, "Ubicación económica en la parte más alta del teatro");

    private final int precio;
    private final String descripcion;

    PreciosAsientos(int precio, String descripcion) {
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public int obtenerPrecio() {
        return precio;
    }

    // Método para formatear el precio con separador de miles
    public String obtenerPrecioFormateado() {
        return String.format("%,d", precio).replace(",", ".");
    }

    @Override
    public String toString() {
        return name() + ": $" + obtenerPrecioFormateado() + " CLP - " + descripcion;
    }
}
