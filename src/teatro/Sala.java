package teatro;

import enums.EstadoAsiento;
import enums.PreciosAsientos;
import utils.ConsoleColors;

import java.util.HashMap;
import java.util.Map;

public class Sala {
    // Arreglos fijos para distintos tipos de asientos
    // la longitud de estos viene dado supuestamente por reglas de negocio
    // y distribución de sala
    private final Asiento[] asientosVIP = new Asiento[9];
    private final Asiento[] asientosPlateaBaja = new Asiento[52];
    private final Asiento[] asientosPlateaAlta = new Asiento[13];
    private final Asiento[] asientosGaleria = new Asiento[15];
    private final Asiento[] asientosPalcos = new Asiento[18];

    // Mapa para búsqueda rápida de asientos por código
    private final Map<String, Asiento> mapaAsientos;

    // Asientos totales precalculados para evitar recálculos frecuentes
    private Asiento[] todosLosAsientos;

    /**
     * Constructor para crear una abstracción de la sala
     * inicializando los procesos de configuración de la sala
     */
    public Sala() {
        this.mapaAsientos = new HashMap<>();

        inicializarAsientos();
        inicializarMapaAsientos();

        // hace los calculos previos para optimizar proceso de conteo
        precalcularTodosLosAsientos();
    }

    /**
     * Inicializa todos los tipos de asientos.
     */
    private void inicializarAsientos() {
        inicializarTipoAsiento(asientosVIP, "V", PreciosAsientos.VIP);
        inicializarTipoAsiento(asientosPlateaBaja, "P", PreciosAsientos.PLATEA_BAJA);
        inicializarTipoAsiento(asientosPlateaAlta, "J", PreciosAsientos.PLATEA_ALTA);
        inicializarTipoAsiento(asientosGaleria, "G", PreciosAsientos.GALERIA);
        inicializarTipoAsiento(asientosPalcos, "F", PreciosAsientos.PALCO);
    }

    /**
     * Inicializa un tipo específico de asientos.
     *
     * @param arregloAsientos Arreglo de asientos a inicializar
     * @param prefijo Prefijo para los códigos de los asientos
     * @param precio Precio asociado a este tipo de asiento
     */
    private void inicializarTipoAsiento(Asiento[] arregloAsientos, String prefijo, PreciosAsientos precio) {
        for (int i = 0; i < arregloAsientos.length; i++) {
            String codigo = String.format("%s%02d", prefijo, i);
            arregloAsientos[i] = new Asiento(codigo, precio);
        }
    }

    /**
     * Inicializa el mapa de asientos para búsqueda rápida por código.
     */
    private void inicializarMapaAsientos() {
        agregarAsientosAlMapa(asientosVIP);
        agregarAsientosAlMapa(asientosPlateaBaja);
        agregarAsientosAlMapa(asientosPlateaAlta);
        agregarAsientosAlMapa(asientosGaleria);
        agregarAsientosAlMapa(asientosPalcos);
    }

    /**
     * Agrega un conjunto de asientos al mapa de búsqueda.
     *
     * @param asientos Arreglo de asientos a agregar al mapa
     */
    private void agregarAsientosAlMapa(Asiento[] asientos) {
        for (Asiento asiento : asientos) {
            mapaAsientos.put(asiento.obtenerCodigo(), asiento);
        }
    }

    /**
     * Precalcula el arreglo que contiene todos los asientos para evitar
     * recrearlo cada vez que se solicite.
     */
    private void precalcularTodosLosAsientos() {
        // Calcular el tamaño total del nuevo arreglo
        int tamanioTotal = asientosVIP.length +
                asientosPlateaBaja.length +
                asientosPlateaAlta.length +
                asientosGaleria.length +
                asientosPalcos.length;

        // Crear el nuevo arreglo con el tamaño total
        todosLosAsientos = new Asiento[tamanioTotal];

        // Índice para seguir la posición en el nuevo arreglo
        int indiceActual = 0;

        // Copiar cada arreglo al nuevo arreglo unificado
        System.arraycopy(asientosVIP, 0, todosLosAsientos, indiceActual, asientosVIP.length);
        indiceActual += asientosVIP.length;

        System.arraycopy(asientosPlateaBaja, 0, todosLosAsientos, indiceActual, asientosPlateaBaja.length);
        indiceActual += asientosPlateaBaja.length;

        System.arraycopy(asientosPlateaAlta, 0, todosLosAsientos, indiceActual, asientosPlateaAlta.length);
        indiceActual += asientosPlateaAlta.length;

        System.arraycopy(asientosGaleria, 0, todosLosAsientos, indiceActual, asientosGaleria.length);
        indiceActual += asientosGaleria.length;

        System.arraycopy(asientosPalcos, 0, todosLosAsientos, indiceActual, asientosPalcos.length);
    }

    /**
     * Determina el color a usar para mostrar un asiento según su estado.
     *
     * @param asiento El asiento a evaluar
     * @param colorDesocupado El color a usar si el asiento está desocupado
     * @return El color que debe usarse para mostrar el asiento
     */
    private String obtenerColorPorEstado(Asiento asiento, String colorDesocupado) {
        return asiento.obtenerEstado() == EstadoAsiento.OCUPADO
                ? ConsoleColors.RESET
                : colorDesocupado;
    }

    /**
     * Dibuja una representación visual del plano del teatro en la consola.
     */
    public void dibujarPlanoTeatroNumerado() {
        // VIP - Cyan (9 asientos)
        for (Asiento asiento: this.asientosVIP) {
            String color = this.obtenerColorPorEstado(asiento, ConsoleColors.CYAN_BRILLANTE);
            System.out.print(ConsoleColors.colorear(
                    asiento.obtenerCodigo() + "      ",
                    color)
            );
        }

        System.out.print("\n\n");

        // PLATEA - Amarillo (primera fila: 13 asientos)
        System.out.print("      ");
        for (int i = 0; i < 13; i++) {
            Asiento asiento = this.asientosPlateaBaja[i];
            String codigo = asiento.obtenerCodigo();
            String color = this.obtenerColorPorEstado(asiento, ConsoleColors.AMARILLO_BRILLANTE);
            System.out.print(ConsoleColors.colorear(codigo + "  ", color));
        }

        System.out.print("\n");

        // PLATEA - Amarillo (segunda fila: 11 asientos)
        System.out.print("           ");
        for (int i = 13; i < 24; i++) {
            Asiento asiento = this.asientosPlateaBaja[i];
            String codigo = asiento.obtenerCodigo();
            String color = this.obtenerColorPorEstado(asiento, ConsoleColors.AMARILLO_BRILLANTE);
            System.out.print(ConsoleColors.colorear(codigo + "  ", color));
        }

        System.out.print("\n");

        // PLATEA - Amarillo (central superior: 7 asientos)
        System.out.print("                     ");
        for (int i = 24; i < 31; i++) {
            Asiento asiento = this.asientosPlateaBaja[i];
            String codigo = asiento.obtenerCodigo();
            String color = this.obtenerColorPorEstado(asiento, ConsoleColors.AMARILLO_BRILLANTE);
            System.out.print(ConsoleColors.colorear(codigo + "  ", color));
        }

        System.out.print("\n");

        // Contador para los asientos de PALCO (Rojo)
        int contadorF = 0;

        // Filas de PALCO y PLATEA central (3 filas)
        for (int j = 0; j < 3; j++) {
            // PALCO izquierdo (3 asientos por fila)
            for (int i = 0; i < 3; i++) {
                Asiento asiento = asientosPalcos[contadorF++];
                String codigo = asiento.obtenerCodigo();
                String color = this.obtenerColorPorEstado(asiento, ConsoleColors.ROJO_BRILLANTE);
                System.out.print(ConsoleColors.colorear(codigo + "  ", color));
            }

            System.out.print("      ");

            // PLATEA central (7 asientos por fila)
            for (int i = 31 + (j * 7); i < 31 + ((j + 1) * 7); i++) {
                Asiento asiento = asientosPlateaBaja[i];
                String codigo = asiento.obtenerCodigo();
                String color = this.obtenerColorPorEstado(asiento, ConsoleColors.AMARILLO_BRILLANTE);
                System.out.print(ConsoleColors.colorear(codigo + "  ", color));
            }

            System.out.print("      ");

            // PALCO derecho (3 asientos por fila)
            for (int i = 0; i < 3; i++) {
                Asiento asiento = asientosPalcos[contadorF++];
                String codigo = asiento.obtenerCodigo();
                String color = this.obtenerColorPorEstado(asiento, ConsoleColors.ROJO_BRILLANTE);
                System.out.print(ConsoleColors.colorear(codigo + "  ", color));
            }

            System.out.print("\n");
        }

        System.out.print("\n");

        // PLATEA ALTA - Azul (13 asientos)
        System.out.print("      ");
        for (int i = 0; i < 13; i++) {
            Asiento asiento = asientosPlateaAlta[i];
            String codigo = asiento.obtenerCodigo();
            String color = this.obtenerColorPorEstado(asiento, ConsoleColors.AZUL_BRILLANTE);
            System.out.print(ConsoleColors.colorear(codigo + "  ", color));
        }

        System.out.print("\n");

        // GALERÍA - Verde (15 asientos)
        System.out.print(" ");
        for (int i = 0; i < 15; i++) {
            Asiento asiento = asientosGaleria[i];
            String codigo = asiento.obtenerCodigo();
            String color = this.obtenerColorPorEstado(asiento, ConsoleColors.VERDE_BRILLANTE);
            System.out.print(ConsoleColors.colorear(codigo + "  ", color));
        }

        // Agregar una leyenda para mejorar la experiencia de usuario
        System.out.println("\n\nLeyenda:");
        System.out.println(ConsoleColors.colorear("VXX - VIP", ConsoleColors.CYAN_BRILLANTE) + " | " +
                ConsoleColors.colorear("PXX - Platea Baja", ConsoleColors.AMARILLO_BRILLANTE) + " | " +
                ConsoleColors.colorear("JXX - Platea Alta", ConsoleColors.AZUL_BRILLANTE));
        System.out.println(ConsoleColors.colorear("GXX - Galería", ConsoleColors.VERDE_BRILLANTE) + " | " +
                ConsoleColors.colorear("FXX - Palcos", ConsoleColors.ROJO_BRILLANTE));
    }

    /**
     * Obtiene un asiento específico por su código.
     * Este método ha sido optimizado para usar un mapa en lugar de búsqueda lineal.
     *
     * @param codigo El código del asiento a buscar
     * @return El asiento encontrado o null si no existe
     */
    public Asiento obtenerAsiento(String codigo) {
        // Validar entrada
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("El código del asiento no puede ser null o vacío");
        }

        // Búsqueda directa en el mapa (O(1) en lugar de O(n))
        Asiento asiento = mapaAsientos.get(codigo);

        if (asiento == null) {
            System.out.println("No se encontró ningún asiento con código: " + codigo);
        }

        return asiento;
    }

    /**
     * Retorna un arreglo con todos los asientos de la sala,
     * independientemente de su tipo o estado.
     *
     * @return Un arreglo con todos los asientos de la sala
     */
    public Asiento[] obtenerTodosLosAsientos() {
        // Como ya tenemos precalculado el arreglo, simplemente lo devolvemos
        return todosLosAsientos;
    }
}