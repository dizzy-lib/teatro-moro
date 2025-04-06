import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    // Define las zonas disponibles
    static int disponiblesPlateaAlta = 3;
    static int disponiblesVip = 3;
    static int disponiblesPlateaBaja = 3;
    static int disponiblesPalcos = 3;

    /**
     * Método que valida si un input está dentro de un rango determinado
     * @param scanner El scanner para leer la entrada
     * @param min Valor mínimo aceptable
     * @param max Valor máximo aceptable
     * @return El valor validado si es correcto, o -1 si ocurrió un error
     */
    public static int getValidInput(Scanner scanner, int min, int max) {
        int input;

        try {
            input = scanner.nextInt();
            if (input >= min && input <= max) {
                return input;
            } else {
                System.out.println("[Error] Ingresa un valor entre " + min + " y " + max + ", intenta nuevamente");
                return -1;
            }
        } catch (InputMismatchException e) {
            System.out.println("[Error] Debes ingresar un número válido, intenta nuevamente");
            return -1;
        }
    }

    /**
     * Método original mantenido por compatibilidad
     */
    public static boolean isNotInputValid(int input, int min, int max) {
        if (input >= min && input <= max) {
            return false;
        } else {
            System.out.println("[Error] Ingresa un input válido, intenta nuevamente");
            return true;
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
            case 1 -> disponiblesPlateaAlta > 0;
            case 2 -> disponiblesVip > 0;
            case 3 -> disponiblesPlateaBaja > 0;
            case 4 -> disponiblesPalcos > 0;
            default -> false;
        };
    }

    private static String obtenerZona(int zone) {
        if (isNotInputValid(zone, 1, 4)) return null;

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

        while (true) {
            System.out.println("\nMENU");
            int option;

            do {
                System.out.println("1. Comprar entrada");
                System.out.println("2. Salir");

                System.out.print("Ingresa la opción que deseas: ");
                option = getValidInput(sc, 1, 2);

                // Si el valor es inválido, limpiamos el buffer para evitar un bucle infinito
                if (option == -1) {
                    sc.nextLine();
                }
            } while (option == -1);

            // Verifica que el usuario uso la opción de salir y retorna o sale
            // del programa
            if (option == 2) {
                System.out.println("Gracias por usar el programa :) Hasta luego!");
                sc.close();
                return;
            }

            // no agrego un else if para no anidar código dentro de otro scope
            if (option == 1) {
                System.out.println("\n=== ZONAS DISPONIBLES ===");

                System.out.println("1. Platea alta: " + disponiblesPlateaAlta);
                System.out.println("2. Vip: " + disponiblesVip);
                System.out.println("3. Platea baja: " + disponiblesPlateaBaja);
                System.out.println("4. Palcos: " + disponiblesPalcos);

                int inputZone;

                do {
                    System.out.print("Ingresa la zona que desea comprar: ");
                    inputZone = getValidInput(sc, 1, 4);

                    // Si el valor es inválido, limpiamos el buffer para evitar un bucle infinito
                    if (inputZone == -1) {
                        sc.nextLine();
                        continue;
                    }

                    // Verificar disponibilidad
                    if (!isAvailable(inputZone)) {
                        System.out.println("[Error] No hay entradas disponibles para esta zona, elige otra");
                        inputZone = -1;
                    }
                } while (inputZone == -1);

                // Reducir el contador de entradas disponibles para la zona elegida
                switch (inputZone) {
                    case 1 -> disponiblesPlateaAlta--;
                    case 2 -> disponiblesVip--;
                    case 3 -> disponiblesPlateaBaja--;
                    case 4 -> disponiblesPalcos--;
                }

                // Asigna el precio general de la entrada sin descuento según
                // la zona escogida
                double precioGeneralEntrada = obtenerPrecioEntradaPorZona(inputZone);

                int edad;

                // Validamos que la edad sea válida
                do {
                    System.out.print("Ingresa tu edad: ");
                    edad = getValidInput(sc, 1, 100);

                    // Si el valor es inválido, limpiamos el buffer para evitar un bucle infinito
                    if (edad == -1) {
                        sc.nextLine();
                    }
                } while (edad == -1);

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

                System.out.println("\n=== RESUMEN DE TU COMPRA ===");
                System.out.println("Ubicación (Zona): " + obtenerZona(inputZone));
                System.out.println("Precio base: " + obtenerPrecioEntradaPorZona(inputZone));
                System.out.println("Descuento aplicado: " + descuentoAplicado);
                System.out.println("Precio final a pagar: " + precioFinalEntrada);
            }
        }
    }
}