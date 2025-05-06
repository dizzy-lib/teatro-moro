/**
 * Representa un asiento en el teatro
 */
public class Asiento {
    private int id;
    private TipoAsiento tipo;
    private EstadoAsiento estado;
    private int fila;
    private int columna;

    public Asiento(int id, TipoAsiento tipo, int fila, int columna) {
        this.id = id;
        this.tipo = tipo;
        this.estado = EstadoAsiento.DISPONIBLE;
        this.fila = fila;
        this.columna = columna;
    }

    // Getters y setters
    public int getId() { return id; }
    public TipoAsiento getTipo() { return tipo; }
    public EstadoAsiento getEstado() { return estado; }
    public void setEstado(EstadoAsiento estado) { this.estado = estado; }
    public int getFila() { return fila; }
    public int getColumna() { return columna; }

    // Métodos de utilidad
    public boolean estaDisponible() {
        return this.estado == EstadoAsiento.DISPONIBLE;
    }

    public String getUbicacion() {
        return "Fila " + this.fila + ", Columna " + this.columna;
    }
}