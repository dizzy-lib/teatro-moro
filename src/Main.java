/**
 * Clase principal para iniciar la aplicación del Teatro Moro
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Teatro Moro...");

        // Inicializar el menú optimizado
        MenuOptimizado menu = new MenuOptimizado();

        // Iniciar el sistema
        menu.iniciar();

        System.out.println("El programa ha finalizado. ¡Hasta pronto!");
    }
}