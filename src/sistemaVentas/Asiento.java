package sistemaVentas;

public class Asiento {
    private EstadoAsiento estado;
    private TipoAsiento tipo;

    public Asiento(TipoAsiento tipo) {
        this.estado = EstadoAsiento.DISPONIBLE;
        this.tipo = tipo;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    public TipoAsiento getTipo() {
        return tipo;
    }
}
