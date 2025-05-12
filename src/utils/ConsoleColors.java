package utils;

/**
 * Clase utilitaria para dar color al texto en la consola.
 * Versión simplificada que contiene solo los elementos necesarios
 * para el sistema de venta
 */
public class ConsoleColors {
    // Resetear color
    public static final String RESET = "\u001B[0m";

    // Colores brillantes (los que se usan en la aplicación)
    public static final String ROJO_BRILLANTE = "\u001B[91m";
    public static final String VERDE_BRILLANTE = "\u001B[92m";
    public static final String AMARILLO_BRILLANTE = "\u001B[93m";
    public static final String AZUL_BRILLANTE = "\u001B[94m";
    public static final String CYAN_BRILLANTE = "\u001B[96m";

    /**
     * Aplica un color al texto y lo resetea al final.
     *
     * @param texto Texto a colorear
     * @param color Color a aplicar
     * @return Texto con color
     */
    public static String colorear(String texto, String color) {
        return color + texto + RESET;
    }
}