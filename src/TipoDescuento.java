/**
 * Enumera los tipos de descuentos disponibles en el teatro
 */
public enum TipoDescuento {
    ESTUDIANTE(0.10f),
    TERCERA_EDAD(0.15f),
    NINGUNO(0f);

    private final float valor;

    TipoDescuento(float valor) {
        this.valor = valor;
    }

    public float getValor() {
        return this.valor;
    }
}