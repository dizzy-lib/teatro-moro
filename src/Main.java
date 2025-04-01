import java.util.Scanner;

public class Main {

    // Función para obtener el precio según tipo de entrada y tarifa
    public static int obtenerPrecio(int tipoEntrada, int tipoTarifa) {
        int[][] tablaPrecios = new int[4][2];

        // VIP
        tablaPrecios[0][0] = 20000; // Estudiante
        tablaPrecios[0][1] = 30000; // Público general

        // Platea baja
        tablaPrecios[1][0] = 10000; // Estudiante
        tablaPrecios[1][1] = 15000; // Público general

        // Platea alta
        tablaPrecios[2][0] = 9000;  // Estudiante
        tablaPrecios[2][1] = 18000; // Público general

        // Palcos
        tablaPrecios[3][0] = 6500;  // Estudiante
        tablaPrecios[3][1] = 13000; // Público general

        return tablaPrecios[tipoEntrada][tipoTarifa];
    }

    // Función para validar que la entrada esté dentro de un rango
    public static boolean esInputValido(int input, int minimo, int maximo) {
        if (input >= minimo && input <= maximo) {
            return true;
        } else {
            System.out.println("[Error] Ingresa un input válido, intenta nuevamente");
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tipoEntrada = 0, tipoTarifa = 0;
        int precioNormal, precioEstudiante;
        int descuentoEstudiantil, tasaDescuento;

        // Solicitar tipo de entrada
        do {
            System.out.println("=== Tipos de entrada ===");
            System.out.println("0. Vip");
            System.out.println("1. Platea baja");
            System.out.println("2. Platea alta");
            System.out.println("3. Palcos");

            System.out.print("Ingresa el tipo de entrada: ");
            try {
                tipoEntrada = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("[Error] Debes ingresar un número");
                scanner.nextLine(); // Limpiar buffer
                tipoEntrada = -1; // Valor que no pasará la validación
            }
        } while (!esInputValido(tipoEntrada, 0, 3));

        // Solicitar tipo de tarifa
        do {
            System.out.println("=== Tipos de tarifas ===");
            System.out.println("0. Estudiante");
            System.out.println("1. Público general");

            System.out.print("Ingresa el tipo de tarifa: ");
            try {
                tipoTarifa = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("[Error] Debes ingresar un número");
                scanner.nextLine(); // Limpiar buffer
                tipoTarifa = -1; // Valor que no pasará la validación
            }
        } while (!esInputValido(tipoTarifa, 0, 1));

        // Obtener precios
        precioNormal = obtenerPrecio(tipoEntrada, 1);
        precioEstudiante = obtenerPrecio(tipoEntrada, 0);

        // Calcular el descuento por estudiante
        if (tipoTarifa == 0) {
            descuentoEstudiantil = precioNormal - precioEstudiante;
        } else {
            descuentoEstudiantil = 0;
        }

        // Calcular tasa de descuento
        tasaDescuento = (int)((descuentoEstudiantil * 100) / precioNormal);

        // Mostrar resultados
        System.out.println("Precio normal: $" + precioNormal);
        System.out.println("------------------------");
        System.out.println("Descuento (%): " + tasaDescuento + "%");
        System.out.println("Descuento a aplicar: $" + descuentoEstudiantil);
        System.out.println("------------------------");
        System.out.println("Total a pagar: $" + (precioNormal - descuentoEstudiantil));
        System.out.println("==========================");

        System.out.println("Gracias por su compra, disfrute la función");

        scanner.close();
    }
}