import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    // Define las zonas disponibles
    static int disponiblesPlateaAlta = 3;
    static int disponiblesVip = 3;
    static int disponiblesPlateaBaja = 3;
    static int disponiblesPalcos = 3;

    /**
     * Método que valida si una entrada está dentro de un rango determinado
     * @param scanner El scanner para leer la entrada
     * @param minimo Valor mínimo aceptable
     * @param maximo Valor máximo aceptable
     * @return El valor validado si es correcto, o -1 si ocurrió un error
     */
    public static int obtenerEntradaValida(Scanner scanner, int minimo, int maximo) {
        int entrada;

        try {
            entrada = scanner.nextInt();
            if (entrada >= minimo && entrada <= maximo) {
                return entrada;
            } else {
                System.out.println("[Error] Ingresa un valor entre " + minimo + " y " + maximo + ", intenta nuevamente");
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
    public static boolean esEntradaInvalida(int entrada, int minimo, int maximo) {
        if (entrada >= minimo && entrada <= maximo) {
            return false;
        } else {
            System.out.println("[Error] Ingresa una entrada válida, intenta nuevamente");
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

    public static boolean hayDisponibilidad(int zona) {
        return switch (zona) {
            case 1 -> disponiblesPlateaAlta > 0;
            case 2 -> disponiblesVip > 0;
            case 3 -> disponiblesPlateaBaja > 0;
            case 4 -> disponiblesPalcos > 0;
            default -> false;
        };
    }

    private static String obtenerNombreZona(int zona) {
        if (esEntradaInvalida(zona, 1, 4)) return null;

        return switch (zona) {
            case 1 -> "Platea alta";
            case 2 -> "Vip";
            case 3 -> "Platea baja";
            case 4 -> "Palcos";
            default -> null;
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double DESCUENTO_ESTUDIANTE = 0.15; // descuento de estudiante
        double DESCUENTO_ADULTO_MAYOR = 0.10; // descuento de adulto mayor

        double precioFinalEntrada;

        while (true) {
            System.out.println("\nMENU");
            int opcion;

            do {
                System.out.println("1. Comprar entrada");
                System.out.println("2. Salir");

                System.out.print("Ingresa la opción que deseas: ");
                opcion = obtenerEntradaValida(scanner, 1, 2);

                // Si el valor es inválido, limpiamos el buffer para evitar un bucle infinito
                if (opcion == -1) {
                    scanner.nextLine();
                }
            } while (opcion == -1);

            // Verifica que el usuario usó la opción de salir y retorna o sale
            // del programa
            if (opcion == 2) {
                System.out.println("Gracias por usar el programa :) Hasta luego!");
                scanner.close();
                return;
            }

            // no agrego un else if para no anidar código dentro de otro scope
            if (opcion == 1) {
                System.out.println("\n=== ZONAS DISPONIBLES ===");

                System.out.println("1. Platea alta: " + disponiblesPlateaAlta);
                System.out.println("2. Vip: " + disponiblesVip);
                System.out.println("3. Platea baja: " + disponiblesPlateaBaja);
                System.out.println("4. Palcos: " + disponiblesPalcos);

                int zonaSeleccionada;

                do {
                    System.out.print("Ingresa la zona que desea comprar: ");
                    zonaSeleccionada = obtenerEntradaValida(scanner, 1, 4);

                    // Si el valor es inválido, limpiamos el buffer para evitar un bucle infinito
                    if (zonaSeleccionada == -1) {
                        scanner.nextLine();
                        continue;
                    }

                    // Verificar disponibilidad
                    if (!hayDisponibilidad(zonaSeleccionada)) {
                        System.out.println("[Error] No hay entradas disponibles para esta zona, elige otra");
                        zonaSeleccionada = -1;
                    }
                } while (zonaSeleccionada == -1);

                // Reducir el contador de entradas disponibles para la zona elegida
                switch (zonaSeleccionada) {
                    case 1 -> disponiblesPlateaAlta--;
                    case 2 -> disponiblesVip--;
                    case 3 -> disponiblesPlateaBaja--;
                    case 4 -> disponiblesPalcos--;
                }

                // Asigna el precio general de la entrada sin descuento según
                // la zona escogida
                double precioBaseEntrada = obtenerPrecioEntradaPorZona(zonaSeleccionada);

                int edad;

                // Validamos que la edad sea válida
                do {
                    System.out.print("Ingresa tu edad: ");
                    edad = obtenerEntradaValida(scanner, 1, 100);

                    // Si el valor es inválido, limpiamos el buffer para evitar un bucle infinito
                    if (edad == -1) {
                        scanner.nextLine();
                    }
                } while (edad == -1);

                double descuentoAplicado = 0;

                if (edad <= 18) {
                    descuentoAplicado = precioBaseEntrada * DESCUENTO_ESTUDIANTE;
                    precioFinalEntrada = precioBaseEntrada - descuentoAplicado;
                } else if (edad >= 60) {
                    descuentoAplicado = precioBaseEntrada * DESCUENTO_ADULTO_MAYOR;
                    precioFinalEntrada = precioBaseEntrada - descuentoAplicado;
                } else {
                    precioFinalEntrada = precioBaseEntrada;
                }

                System.out.println("\n=== RESUMEN DE TU COMPRA ===");
                System.out.println("Ubicación (Zona): " + obtenerNombreZona(zonaSeleccionada));
                System.out.println("Precio base: " + obtenerPrecioEntradaPorZona(zonaSeleccionada));
                System.out.println("Descuento aplicado: " + descuentoAplicado);
                System.out.println("Precio final a pagar: " + precioFinalEntrada);
            }
        }
    }
}