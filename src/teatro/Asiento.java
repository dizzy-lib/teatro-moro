package teatro;

import enums.EstadoAsiento;
import enums.PreciosAsientos;

public class Asiento {
    private EstadoAsiento estado;
    private final PreciosAsientos tipoAsiento;
    private final String codigoAsiento;

    /**
     * Constructor que inicializa la configuración de un asiento de la sala
     * @param codigoAsiento código identificador del asiento
     * @param tipoAsiento tipo de asiento disponible dentro del Teatro
     */
    public Asiento(String codigoAsiento, PreciosAsientos tipoAsiento) {
        this.codigoAsiento = codigoAsiento;

        // inicializa el asiento como disponible
        this.estado = EstadoAsiento.DESOCUPADO;
        this.tipoAsiento = tipoAsiento;
    }

    /**
     * Obtiene el código del asiento
     * @return código del asiento
     */
    public String obtenerCodigo() {
        return this.codigoAsiento;
    }

    /**
     * Método que obtiene el estado actual del asiento
     * @return estado del asiento (ocupado, desocupado)
     */
    public EstadoAsiento obtenerEstado() {
        return this.estado;
    }

    /**
     * Método que permite cambiar el estado del asiento
     * @param estado nuevo estado del asiento
     */
    public void setearEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    /**
     * Método que obtiene el tipo del asiento
     * @return tipo del asiento que contiene información base y genérica del asiento
     */
    public PreciosAsientos obtenerTipo() {
        return this.tipoAsiento;
    }
}
