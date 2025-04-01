import java.util.Scanner;

public class Main {
    // Define las zonas disponibles
    static int disponiblesPlateaAlta = 3;
    static int disponiblesVip = 3;
    static int disponiblesPlateaBaja = 3;
    static int disponiblesPalcos = 3;

    public static boolean isInputValid(int input, int min, int max) {
        // TODO: agregar validación de tipo, para que maneje el error en caso de que ponga un string por ejemplo
        if (input >= min && input <= max) {
            return true;
        } else {
            System.out.println("[Error] Ingresa un input válido, intenta nuevamente");
            return false;
        }
    }

    // Función para obtener el precio de la entrada
    public static int obtenerPrecioEntradaPorZona(int zona) {
        int[] tablaPrecios = new int[4];
        tablaPrecios[0] = 30000; // VIP
        tablaPrecios[1] = 15000; // Platea baja
        tablaPrecios[2] = 18000; // Platea alta
        tablaPrecios[3] = 13000; // Palcos


        // restamos 1 a la zona ingresada para que haga match
        // con el índice de la lista
        return tablaPrecios[zona - 1];
    }

    public static boolean isAvailable(int zone) {
        return switch (zone) {
            case 1 -> disponiblesPlateaAlta >= 0;
            case 2 -> disponiblesVip >= 0;
            case 3 -> disponiblesPlateaBaja >= 0;
            case 4 -> disponiblesPalcos >= 0;
            default -> false;
        };
    }

    private static String obtenerZona(int zone) {
        if (!isInputValid(zone, 1, 4)) return null;

        return switch (zone) {
            case 1 -> "Platea alta";
            case 2 -> "Vip";
            case 3 -> "Platea baja";
            case 4 -> "Palcos";
            default -> null;
        };
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double STUDENT_DISCOUNT = 0.15; // descuento de estudiante
        double SENIOR_DISCOUNT = 0.10; // descuento de adulto mayor

        double precioFinalEntrada;

        System.out.println("MENU");
        int option;

        do {
            System.out.println("1. Comprar entrada");
            System.out.println("2. Salir");

            System.out.print("Ingresa la opción que deseas: ");
            option = sc.nextInt();
        } while (!isInputValid(option, 1, 2));

        // Verifica que el usuario uso la opción de salir y retorna o sale
        // del programa
        if (option == 2) {
            System.out.println("Gracias por usar el programa :) Hasta luego!");
            return;
        }

        // no agrego un else if para no anidar código dentro de otro scope
        if (option == 1) {
            System.out.println("=== ZONAS DISPONIBLES ===");

            System.out.println("1. Platea alta: " + disponiblesPlateaAlta);
            System.out.println("2. Vip: " + disponiblesVip);
            System.out.println("3. Platea baja: " + disponiblesPlateaBaja);
            System.out.println("4. Palcos: " + disponiblesPalcos);

            int inputZone;

            do {
                System.out.print("Ingresa la zona que desea comprar: ");
                inputZone = sc.nextInt();
            } while (!isInputValid(inputZone, 1, 4) && !isAvailable(inputZone));

            // Asigna el precio general de la entrada sin descuento según
            // la zona escogida
            double precioGeneralEntrada = obtenerPrecioEntradaPorZona(inputZone);

            int edad;

            // Validamos que la edad sea válida
            do {
                System.out.print("Ingresa tu edad: ");
                edad = sc.nextInt();
            } while (!isInputValid(edad, 1, 100));

            double descuentoAplicado = 0;

            if (edad <= 18) {
                descuentoAplicado = precioGeneralEntrada * STUDENT_DISCOUNT;
                precioFinalEntrada = precioGeneralEntrada - descuentoAplicado;
            } else if (edad >= 60) {
                descuentoAplicado = precioGeneralEntrada * SENIOR_DISCOUNT;
                precioFinalEntrada = precioGeneralEntrada - descuentoAplicado;
            } else {
                precioFinalEntrada = precioGeneralEntrada;
            }

            System.out.println("=== RESUMEN DE TU COMPRA ===");
            System.out.println("Ubicación (Zona): " + obtenerZona(inputZone));
            System.out.println("Precio base: " + obtenerPrecioEntradaPorZona(inputZone));
            System.out.println("Descuento aplicado: " + descuentoAplicado);
            System.out.println("Precio final a pagar: " + precioFinalEntrada);
        }

        sc.close();
    }
}