public class Asiento {
    private EstadoAsiento _estado;

    public Asiento() {
        this._estado = EstadoAsiento.DISPONIBLE;
    }

    public EstadoAsiento estado() {
        return _estado;
    }

    public void setEstado(EstadoAsiento estado) {
        this._estado = estado;
    }
}
