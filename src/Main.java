import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Programa para venta de entradas de un evento
 * Este sistema permite al usuario comprar entradas para diferentes zonas,
 * aplicando descuentos según la edad del comprador.
 */
public class Main {
    // Define las cantidades disponibles para cada zona
    static int disponiblesPlateaAlta = 3;  // Cantidad de entradas disponibles para Platea Alta
    static int disponiblesVip = 3;         // Cantidad de entradas disponibles para zona VIP
    static int disponiblesPlateaBaja = 3;  // Cantidad de entradas disponibles para Platea Baja
    static int disponiblesPalcos = 3;      // Cantidad de entradas disponibles para Palcos

    /**
     * Método que valida si una entrada está dentro de un rango determinado
     * Este método captura la entrada del usuario y verifica:
     * 1. Que sea un valor numérico (maneja InputMismatchException)
     * 2. Que esté dentro del rango especificado
     *
     * @param scanner El scanner para leer la entrada del usuario
     * @param minimo Valor mínimo aceptable (inclusivo)
     * @param maximo Valor máximo aceptable (inclusivo)
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
     * Verifica si una entrada está fuera del rango válido
     * A diferencia de obtenerEntradaValida(), este método no captura excepciones
     * y asume que la entrada ya es un número válido
     *
     * @param entrada El valor a validar
     * @param minimo Valor mínimo aceptable (inclusivo)
     * @param maximo Valor máximo aceptable (inclusivo)
     * @return true si la entrada es inválida, false si es válida
     */
    public static boolean esEntradaInvalida(int entrada, int minimo, int maximo) {
        if (entrada >= minimo && entrada <= maximo) {
            return false;
        } else {
            System.out.println("[Error] Ingresa una entrada válida, intenta nuevamente");
            return true;
        }
    }

    /**
     * Función para obtener el precio base de la entrada según la zona seleccionada
     *
     * @param zona Número de zona (1-4)
     * @return Precio base de la entrada para la zona especificada
     */
    public static int obtenerPrecioEntradaPorZona(int zona) {
        // Definimos los precios para cada zona
        int[] tablaPrecios = new int[4];
        tablaPrecios[0] = 30000; // Precio para zona VIP (índice 0, zona 1)
        tablaPrecios[1] = 15000; // Precio para Platea baja (índice 1, zona 2)
        tablaPrecios[2] = 18000; // Precio para Platea alta (índice 2, zona 3)
        tablaPrecios[3] = 13000; // Precio para Palcos (índice 3, zona 4)

        // Restamos 1 a la zona ingresada para que haga match
        // con el índice de la tabla (los arrays empiezan en 0)
        return tablaPrecios[zona - 1];
    }

    /**
     * Verifica si hay entradas disponibles para la zona seleccionada
     *
     * @param zona Número de zona (1-4)
     * @return true si hay al menos una entrada disponible, false en caso contrario
     */
    public static boolean hayDisponibilidad(int zona) {
        // Usamos switch expression para verificar la disponibilidad según la zona
        return switch (zona) {
            case 1 -> disponiblesPlateaAlta > 0;  // Platea alta
            case 2 -> disponiblesVip > 0;         // VIP
            case 3 -> disponiblesPlateaBaja > 0;  // Platea baja
            case 4 -> disponiblesPalcos > 0;      // Palcos
            default -> false;                     // Zona inválida
        };
    }

    /**
     * Obtiene el nombre descriptivo de la zona según su número
     *
     * @param zona Número de zona (1-4)
     * @return Nombre de la zona o null si la zona es inválida
     */
    private static String obtenerNombreZona(int zona) {
        // Verificamos primero si la zona es válida
        if (esEntradaInvalida(zona, 1, 4)) return null;

        // Usamos switch expression para obtener el nombre según la zona
        return switch (zona) {
            case 1 -> "Platea alta";
            case 2 -> "Vip";
            case 3 -> "Platea baja";
            case 4 -> "Palcos";
            default -> null; // No debería llegar aquí debido a la validación previa
        };
    }

    /**
     * Método principal que ejecuta el programa de venta de entradas
     *
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Inicializamos el scanner para leer entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Definimos constantes para los descuentos
        final double DESCUENTO_ESTUDIANTE = 0.15;    // 15% de descuento para estudiantes (menores de 18)
        final double DESCUENTO_ADULTO_MAYOR = 0.10;  // 10% de descuento para adultos mayores (mayores de 60)

        // Variable para almacenar el precio final después de aplicar descuentos
        double precioFinalEntrada;

        // Variable para controlar el bucle principal
        boolean continuarPrograma = true;

        // Utilizamos un ciclo iterativo for para mostrar el menú principal
        // y permitir realizar múltiples compras
        for (int iteracion = 1; continuarPrograma; iteracion++) {
            // Mostramos el menú principal
            System.out.println("\nMENU (Iteración: " + iteracion + ")");
            int opcion;

            // Bucle para obtener una opción válida del menú principal
            do {
                System.out.println("1. Comprar entrada");
                System.out.println("2. Salir");

                System.out.print("Ingresa la opción que deseas: ");
                opcion = obtenerEntradaValida(scanner, 1, 2);

                // Si el valor es inválido, limpiamos el buffer para evitar un bucle infinito
                // causado por caracteres no procesados en la entrada
                if (opcion == -1) {
                    scanner.nextLine();
                }
            } while (opcion == -1);

            // Opción 2: Salir del programa
            if (opcion == 2) {
                System.out.println("Gracias por usar el programa :) Hasta luego!");
                scanner.close(); // Cerramos el scanner para liberar recursos
                return;          // Terminamos la ejecución del programa
            }

            // Opción 1: Comprar entrada
            // No usamos else-if para evitar anidar código y mantener mejor legibilidad
            if (opcion == 1) {
                // Mostrar información de zonas disponibles
                System.out.println("\n=== ZONAS DISPONIBLES ===");

                // Mostramos cada zona con su disponibilidad actual
                System.out.println("1. Platea alta: " + disponiblesPlateaAlta);
                System.out.println("2. Vip: " + disponiblesVip);
                System.out.println("3. Platea baja: " + disponiblesPlateaBaja);
                System.out.println("4. Palcos: " + disponiblesPalcos);

                int zonaSeleccionada;

                // Bucle para obtener una zona válida y con disponibilidad
                do {
                    System.out.print("Ingresa la zona que desea comprar: ");
                    zonaSeleccionada = obtenerEntradaValida(scanner, 1, 4);

                    // Si el valor es inválido, limpiamos el buffer para evitar un bucle infinito
                    if (zonaSeleccionada == -1) {
                        scanner.nextLine();
                        continue; // Volvemos al inicio del bucle
                    }

                    // Verificar si hay entradas disponibles para la zona seleccionada
                    if (!hayDisponibilidad(zonaSeleccionada)) {
                        System.out.println("[Error] No hay entradas disponibles para esta zona, elige otra");
                        zonaSeleccionada = -1; // Forzamos otra iteración del bucle
                    }
                } while (zonaSeleccionada == -1);

                // Reducir el contador de entradas disponibles para la zona elegida
                // porque ya vamos a realizar la venta
                switch (zonaSeleccionada) {
                    case 1 -> disponiblesPlateaAlta--;    // Reducimos disponibilidad de Platea alta
                    case 2 -> disponiblesVip--;           // Reducimos disponibilidad de VIP
                    case 3 -> disponiblesPlateaBaja--;    // Reducimos disponibilidad de Platea baja
                    case 4 -> disponiblesPalcos--;        // Reducimos disponibilidad de Palcos
                }

                // Obtenemos el precio base de la entrada según la zona seleccionada
                // sin aplicar descuentos todavía
                double precioBaseEntrada = obtenerPrecioEntradaPorZona(zonaSeleccionada);

                int edad;

                // Bucle para obtener una edad válida (entre 1 y 100 años)
                do {
                    System.out.print("Ingresa tu edad: ");
                    edad = obtenerEntradaValida(scanner, 1, 100);

                    // Si el valor es inválido, limpiamos el buffer para evitar un bucle infinito
                    if (edad == -1) {
                        scanner.nextLine();
                    }
                } while (edad == -1);

                // Variable para almacenar el descuento en pesos (no en porcentaje)
                double descuentoAplicado = 0;

                // Aplicamos descuentos según la edad del comprador
                if (edad <= 18) {
                    // Descuento para estudiantes (menores o iguales a 18 años)
                    descuentoAplicado = precioBaseEntrada * DESCUENTO_ESTUDIANTE;
                    precioFinalEntrada = precioBaseEntrada - descuentoAplicado;
                } else if (edad >= 60) {
                    // Descuento para adultos mayores (mayores o iguales a 60 años)
                    descuentoAplicado = precioBaseEntrada * DESCUENTO_ADULTO_MAYOR;
                    precioFinalEntrada = precioBaseEntrada - descuentoAplicado;
                } else {
                    // Sin descuento para edades entre 19 y 59 años
                    precioFinalEntrada = precioBaseEntrada;
                }

                // Mostramos el resumen de la compra al usuario
                System.out.println("\n=== RESUMEN DE TU COMPRA ===");
                System.out.println("Ubicación (Zona): " + obtenerNombreZona(zonaSeleccionada));
                System.out.println("Precio base: " + obtenerPrecioEntradaPorZona(zonaSeleccionada));
                System.out.println("Descuento aplicado: " + descuentoAplicado);
                System.out.println("Precio final a pagar: " + precioFinalEntrada);

                // Preguntamos al usuario si desea realizar otra compra
                int deseaOtraCompra;
                do {
                    System.out.println("\n¿Deseas realizar otra compra?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    System.out.print("Ingresa tu opción: ");

                    deseaOtraCompra = obtenerEntradaValida(scanner, 1, 2);

                    // Si el valor es inválido, limpiamos el buffer para evitar un bucle infinito
                    if (deseaOtraCompra == -1) {
                        scanner.nextLine();
                    }
                } while (deseaOtraCompra == -1);

                // Si el usuario no desea realizar otra compra, terminamos el programa
                if (deseaOtraCompra == 2) {
                    System.out.println("Gracias por tu compra. ¡Hasta luego!");
                    scanner.close();
                    return;
                }
            }
        }
    }
}