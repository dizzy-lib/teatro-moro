import java.util.Optional;

/**
 * Sistema para la gestión de asientos del teatro
 * Utiliza arrays de longitud fija para los asientos ya que la capacidad del teatro es fija
 */
public class SistemaAsientos {
    // Arrays fijos para los asientos de cada tipo
    private Asiento[] asientosVIP;
    private Asiento[] asientosPlatea;
    private Asiento[] asientosBalcon;

    // Dimensiones de la sala
    private final int FILAS_POR_TIPO = 5;
    private final int COLUMNAS_POR_TIPO = 2;
    private final int CAPACIDAD_POR_TIPO;

    // Contador de asientos
    private int ultimoId;

    public SistemaAsientos() {
        // Calcular capacidad por tipo
        CAPACIDAD_POR_TIPO = FILAS_POR_TIPO * COLUMNAS_POR_TIPO;

        // Inicializar arrays con longitud fija
        asientosVIP = new Asiento[CAPACIDAD_POR_TIPO];
        asientosPlatea = new Asiento[CAPACIDAD_POR_TIPO];
        asientosBalcon = new Asiento[CAPACIDAD_POR_TIPO];

        // Inicializar contador
        ultimoId = 0;

        // Inicializar asientos
        inicializarAsientos();
    }

    private void inicializarAsientos() {
        // Crear asientos VIP
        for (int fila = 1; fila <= FILAS_POR_TIPO; fila++) {
            for (int columna = 1; columna <= COLUMNAS_POR_TIPO; columna++) {
                int index = ((fila - 1) * COLUMNAS_POR_TIPO) + (columna - 1);
                asientosVIP[index] = new Asiento(++ultimoId, TipoAsiento.VIP, fila, columna);
            }
        }

        // Crear asientos PLATEA
        for (int fila = 1; fila <= FILAS_POR_TIPO; fila++) {
            for (int columna = 1; columna <= COLUMNAS_POR_TIPO; columna++) {
                int index = ((fila - 1) * COLUMNAS_POR_TIPO) + (columna - 1);
                asientosPlatea[index] = new Asiento(++ultimoId, TipoAsiento.PLATEA, fila, columna);
            }
        }

        // Crear asientos BALCON
        for (int fila = 1; fila <= FILAS_POR_TIPO; fila++) {
            for (int columna = 1; columna <= COLUMNAS_POR_TIPO; columna++) {
                int index = ((fila - 1) * COLUMNAS_POR_TIPO) + (columna - 1);
                asientosBalcon[index] = new Asiento(++ultimoId, TipoAsiento.BALCON, fila, columna);
            }
        }
    }

    // Método para obtener un asiento disponible de un tipo específico
    public Optional<Asiento> obtenerAsientoDisponible(TipoAsiento tipo) {
        Asiento[] asientos = obtenerAsientosPorTipo(tipo);

        for (Asiento asiento : asientos) {
            if (asiento.estaDisponible()) {
                return Optional.of(asiento);
            }
        }

        return Optional.empty();
    }

    // Método para obtener la cantidad de asientos disponibles por tipo
    public int contarAsientosDisponibles(TipoAsiento tipo) {
        Asiento[] asientos = obtenerAsientosPorTipo(tipo);
        int disponibles = 0;

        for (Asiento asiento : asientos) {
            if (asiento.estaDisponible()) {
                disponibles++;
            }
        }

        return disponibles;
    }

    // Método para marcar un asiento como ocupado
    public void marcarAsientoComoOcupado(Asiento asiento) {
        asiento.setEstado(EstadoAsiento.OCUPADO);
    }

    // Método auxiliar para obtener el array de asientos según su tipo
    private Asiento[] obtenerAsientosPorTipo(TipoAsiento tipo) {
        switch (tipo) {
            case VIP:
                return asientosVIP;
            case PLATEA:
                return asientosPlatea;
            case BALCON:
                return asientosBalcon;
            default:
                throw new IllegalArgumentException("Tipo de asiento no válido");
        }
    }

    // Método para obtener asiento por ID
    public Optional<Asiento> obtenerAsientoPorId(int id) {
        // Buscar en VIP
        for (Asiento asiento : asientosVIP) {
            if (asiento.getId() == id) {
                return Optional.of(asiento);
            }
        }

        // Buscar en PLATEA
        for (Asiento asiento : asientosPlatea) {
            if (asiento.getId() == id) {
                return Optional.of(asiento);
            }
        }

        // Buscar en BALCON
        for (Asiento asiento : asientosBalcon) {
            if (asiento.getId() == id) {
                return Optional.of(asiento);
            }
        }

        return Optional.empty();
    }

    // Getters para los arrays de asientos
    public Asiento[] getAsientosVIP() {
        return asientosVIP;
    }

    public Asiento[] getAsientosPlatea() {
        return asientosPlatea;
    }

    public Asiento[] getAsientosBalcon() {
        return asientosBalcon;
    }

    // Método para obtener la capacidad total del teatro
    public int getCapacidadTotal() {
        return CAPACIDAD_POR_TIPO * 3; // 3 tipos de asientos
    }
}