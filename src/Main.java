import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Sistema de Venta de Entradas del Teatro Moro
 * Este sistema permite la venta, promoción, búsqueda y eliminación de entradas
 * para diferentes ubicaciones, aplicando descuentos según el tipo de cliente.
 */
public class Main {
    // Variables estáticas para almacenar información del teatro
    // Decidimos hacer estas variables estáticas para acceder a ellas desde cualquier método
    static String nombreTeatro = "Teatro Moro";  // Nombre del teatro
    static int capacidadTotal = 350;            // Capacidad total del teatro

    // Variables para almacenar la disponibilidad de entradas en cada ubicación
    // Se inicializan con la capacidad máxima de cada zona
    static int disponiblesVIP = 50;
    static int disponiblesPlatea = 100;
    static int disponiblesGeneral = 200;

    // Precios unitarios de entradas según la ubicación
    // Usamos tipo double para manejar valores decimales en caso de ser necesario
    static double precioVIP = 30000.0;
    static double precioPlatea = 18000.0;
    static double precioGeneral = 12000.0;

    // Variables estáticas para estadísticas globales
    // Estas variables se irán actualizando a medida que se realicen operaciones
    static int totalEntradasVendidas = 0;        // Contador de entradas vendidas
    static double totalIngresos = 0.0;           // Total de ingresos por ventas

    // Arrays para almacenar la información de las entradas vendidas (máximo 100 entradas)
    // Usamos arrays paralelos en lugar de objetos para seguir un enfoque secuencial
    static int[] numerosEntrada = new int[100];          // Números de entrada
    static String[] ubicacionesEntrada = new String[100]; // Ubicaciones (VIP, Platea, General)
    static double[] preciosEntrada = new double[100];     // Precios finales
    static boolean[] esEstudiante = new boolean[100];     // Si el cliente es estudiante
    static boolean[] esTerceraEdad = new boolean[100];    // Si el cliente es de tercera edad

    /**
     * Método principal que ejecuta el programa
     * Muestra el menú principal y gestiona la navegación entre las diferentes opciones
     */
    public static void main(String[] args) {
        // Inicializar scanner para leer entrada del usuario
        // Es importante crear un solo scanner para toda la ejecución
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;  // Variable para controlar el ciclo principal

        // Mostrar información inicial del teatro
        // Esto se ejecuta una sola vez al iniciar el programa
        System.out.println("=== Sistema de Venta de Entradas ===");
        System.out.println(nombreTeatro);
        System.out.println("Capacidad total: " + capacidadTotal);

        // Ciclo principal del menú
        // Se ejecuta hasta que el usuario decida salir (opción 6)
        while (continuar) {
            // Mostrar opciones del menú principal
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Venta de entradas");
            System.out.println("2. Promociones");
            System.out.println("3. Búsqueda de entradas");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Mostrar estadísticas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            // Leer y validar la opción seleccionada
            // El método obtenerEntradaValida verifica que sea un número entre 1 y 6
            int opcion = obtenerEntradaValida(scanner, 1, 6);

            // Si la opción es inválida (-1), limpiar el buffer y continuar
            // Esto evita problemas con entradas incorrectas
            if (opcion == -1) {
                scanner.nextLine();  // Limpia el buffer de entrada
                continue;            // Vuelve al inicio del ciclo
            }

            // Procesar la opción seleccionada mediante condicionales
            // Usamos if-else en lugar de switch para mantener un enfoque secuencial básico
            if (opcion == 1) {
                // Opción 1: Vender entrada
                venderEntrada(scanner);
            } else if (opcion == 2) {
                // Opción 2: Mostrar promociones disponibles
                mostrarPromociones();
            } else if (opcion == 3) {
                // Opción 3: Buscar entradas según criterios
                buscarEntradas(scanner);
            } else if (opcion == 4) {
                // Opción 4: Eliminar una entrada existente
                eliminarEntrada(scanner);
            } else if (opcion == 5) {
                // Opción 5: Mostrar estadísticas de ventas
                mostrarEstadisticas();
            } else if (opcion == 6) {
                // Opción 6: Salir del programa
                System.out.println("Gracias por utilizar el sistema. ¡Hasta pronto!");
                continuar = false;  // Terminar el ciclo principal
            }
        }

        // Cerrar el scanner al finalizar el programa
        // Esto es una buena práctica para liberar recursos
        scanner.close();
    }

    /**
     * Método para vender una entrada
     * Solicita al usuario la ubicación y tipo de cliente, aplica descuentos y registra la venta
     */
    public static void venderEntrada(Scanner scanner) {
        // Variables locales para almacenar temporalmente los datos de la venta
        String ubicacionSeleccionada;  // Para guardar el nombre de la ubicación
        double precioBase;             // Precio antes de aplicar descuentos
        double descuento = 0.0;        // Valor del descuento a aplicar
        double precioFinal;            // Precio después de aplicar descuentos
        boolean clienteEstudiante = false;  // Si el cliente es estudiante
        boolean clienteTerceraEdad = false; // Si el cliente es de tercera edad

        // Mostrar información de ubicaciones disponibles
        System.out.println("\n=== VENTA DE ENTRADAS ===");
        System.out.println("Ubicaciones disponibles:");
        System.out.println("1. VIP (" + disponiblesVIP + " disponibles) - $" + precioVIP);
        System.out.println("2. Platea (" + disponiblesPlatea + " disponibles) - $" + precioPlatea);
        System.out.println("3. General (" + disponiblesGeneral + " disponibles) - $" + precioGeneral);

        // Seleccionar ubicación y verificar disponibilidad
        // Usamos un ciclo do-while para asegurar una selección válida
        int opcionUbicacion;
        do {
            System.out.print("Seleccione ubicación (1-3): ");
            opcionUbicacion = obtenerEntradaValida(scanner, 1, 3);

            // Si el valor es inválido, limpiar el buffer y continuar
            if (opcionUbicacion == -1) {
                scanner.nextLine();
                continue;
            }

            // Verificar disponibilidad según la ubicación seleccionada
            // Si no hay entradas disponibles, informar al usuario y solicitar otra selección
            if (opcionUbicacion == 1 && disponiblesVIP <= 0) {
                System.out.println("No hay entradas VIP disponibles. Por favor seleccione otra ubicación.");
                opcionUbicacion = -1;  // Forzar otra iteración del ciclo
            } else if (opcionUbicacion == 2 && disponiblesPlatea <= 0) {
                System.out.println("No hay entradas Platea disponibles. Por favor seleccione otra ubicación.");
                opcionUbicacion = -1;
            } else if (opcionUbicacion == 3 && disponiblesGeneral <= 0) {
                System.out.println("No hay entradas General disponibles. Por favor seleccione otra ubicación.");
                opcionUbicacion = -1;
            }
        } while (opcionUbicacion == -1);

        // Asignar ubicación y precio base según la selección del usuario
        // Usamos condicionales para determinar la ubicación seleccionada
        if (opcionUbicacion == 1) {
            ubicacionSeleccionada = "VIP";
            precioBase = precioVIP;
        } else if (opcionUbicacion == 2) {
            ubicacionSeleccionada = "Platea";
            precioBase = precioPlatea;
        } else {
            ubicacionSeleccionada = "General";
            precioBase = precioGeneral;
        }

        // Solicitar y verificar el tipo de cliente para aplicar descuentos
        System.out.println("\nTipo de cliente:");
        System.out.println("1. Regular");
        System.out.println("2. Estudiante (10% descuento)");
        System.out.println("3. Tercera Edad (15% descuento)");

        // Ciclo para obtener un tipo de cliente válido
        int tipoCliente;
        do {
            System.out.print("Seleccione tipo de cliente (1-3): ");
            tipoCliente = obtenerEntradaValida(scanner, 1, 3);

            // Si el valor es inválido, limpiar el buffer
            if (tipoCliente == -1) {
                scanner.nextLine();
            }
        } while (tipoCliente == -1);

        // Aplicar descuento según el tipo de cliente
        // Se usan los porcentajes especificados en el requerimiento
        if (tipoCliente == 2) {
            // 10% de descuento para estudiantes
            descuento = precioBase * 0.10;
            clienteEstudiante = true;
        } else if (tipoCliente == 3) {
            // 15% de descuento para tercera edad
            descuento = precioBase * 0.15;
            clienteTerceraEdad = true;
        }

        // Calcular precio final restando el descuento al precio base
        precioFinal = precioBase - descuento;

        // Registrar la venta en los arrays
        // El índice es totalEntradasVendidas porque aún no hemos incrementado el contador
        int numeroEntrada = totalEntradasVendidas + 1;  // Los números de entrada empiezan en 1
        numerosEntrada[totalEntradasVendidas] = numeroEntrada;
        ubicacionesEntrada[totalEntradasVendidas] = ubicacionSeleccionada;
        preciosEntrada[totalEntradasVendidas] = precioFinal;
        esEstudiante[totalEntradasVendidas] = clienteEstudiante;
        esTerceraEdad[totalEntradasVendidas] = clienteTerceraEdad;

        // Actualizar disponibilidad según la ubicación
        // Reducimos en 1 la disponibilidad de la ubicación seleccionada
        if (opcionUbicacion == 1) {
            disponiblesVIP--;
        } else if (opcionUbicacion == 2) {
            disponiblesPlatea--;
        } else if (opcionUbicacion == 3) {
            disponiblesGeneral--;
        }

        // Actualizar contadores globales
        totalEntradasVendidas++;  // Incrementar contador de entradas vendidas
        totalIngresos += precioFinal;  // Sumar el precio final a los ingresos totales

        // Mostrar resumen de la venta al usuario
        System.out.println("\n=== RESUMEN DE VENTA ===");
        System.out.println("Entrada #" + numeroEntrada);
        System.out.println("Ubicación: " + ubicacionSeleccionada);
        System.out.println("Precio original: $" + precioBase);
        System.out.println("Descuento aplicado: $" + descuento);
        System.out.println("Precio final: $" + precioFinal);
        System.out.println("¡Venta realizada con éxito!");
    }

    /**
     * Método para mostrar promociones disponibles
     * Muestra información sobre descuentos especiales por compra de múltiples entradas
     */
    public static void mostrarPromociones() {
        // Mostramos las diferentes promociones disponibles
        // Estas promociones son fijas y no requieren interacción del usuario
        System.out.println("\n=== PROMOCIONES DISPONIBLES ===");
        System.out.println("1. Compra 2 entradas General, obtén un 5% adicional en ambas");
        System.out.println("2. Compra 1 entrada VIP + 1 entrada Platea, obtén un 8% adicional en ambas");
        System.out.println("3. Grupo familiar (4+ entradas): 12% de descuento en todas las entradas");
        System.out.println("4. Día del espectador (miércoles): 20% en todas las ubicaciones");

        // Información adicional sobre las promociones
        System.out.println("\nEstas promociones se pueden combinar con los descuentos para estudiantes y tercera edad.");
        System.out.println("Consulte con nuestro personal para aplicar estas promociones en su compra.");
    }

    /**
     * Método para buscar entradas según diferentes criterios
     * Permite buscar por número, ubicación o tipo de cliente
     */
    public static void buscarEntradas(Scanner scanner) {
        // Verificar si hay entradas vendidas para buscar
        if (totalEntradasVendidas == 0) {
            System.out.println("No hay entradas vendidas para buscar.");
            return;
        }

        // Mostrar opciones de búsqueda
        System.out.println("\n=== BÚSQUEDA DE ENTRADAS ===");
        System.out.println("1. Buscar por número de entrada");
        System.out.println("2. Buscar por ubicación");
        System.out.println("3. Buscar por tipo de cliente");
        System.out.print("Seleccione una opción: ");

        // Leer y validar la opción de búsqueda
        int opcion = obtenerEntradaValida(scanner, 1, 3);

        // Si la opción es inválida, limpiar el buffer y salir
        if (opcion == -1) {
            scanner.nextLine();
            return;
        }

        // Variable para controlar si se encontraron resultados
        boolean encontrado = false;

        // Opción 1: Buscar por número de entrada
        if (opcion == 1) {
            System.out.print("Ingrese el número de entrada: ");
            int numeroEntrada = obtenerEntradaValida(scanner, 1, Integer.MAX_VALUE);

            if (numeroEntrada == -1) {
                scanner.nextLine(); // Limpiar buffer
                return;
            }

            // Recorrer todos los registros buscando coincidencia por número
            // Usamos un ciclo for para iterar por todos los elementos del array
            for (int i = 0; i < totalEntradasVendidas; i++) {
                if (numerosEntrada[i] == numeroEntrada) {
                    System.out.println("\nEntrada encontrada:");
                    mostrarEntrada(i);  // Mostrar información de la entrada encontrada
                    encontrado = true;
                    break;  // Salir del ciclo porque el número es único
                }
            }
        }
        // Opción 2: Buscar por ubicación
        else if (opcion == 2) {
            // Mostrar opciones de ubicación
            System.out.println("\nUbicaciones:");
            System.out.println("1. VIP");
            System.out.println("2. Platea");
            System.out.println("3. General");
            System.out.print("Seleccione ubicación: ");

            int ubicacionBusqueda = obtenerEntradaValida(scanner, 1, 3);

            if (ubicacionBusqueda == -1) {
                scanner.nextLine(); // Limpiar buffer
                return;
            }

            // Convertir la opción numérica a nombre de ubicación
            String ubicacion;
            if (ubicacionBusqueda == 1) {
                ubicacion = "VIP";
            } else if (ubicacionBusqueda == 2) {
                ubicacion = "Platea";
            } else {
                ubicacion = "General";
            }

            System.out.println("\nEntradas en ubicación " + ubicacion + ":");

            // Recorrer todos los registros buscando coincidencias por ubicación
            // Aquí podemos tener múltiples resultados, así que no usamos break
            for (int i = 0; i < totalEntradasVendidas; i++) {
                if (ubicacionesEntrada[i].equals(ubicacion)) {
                    mostrarEntrada(i);
                    encontrado = true;
                }
            }
        }
        // Opción 3: Buscar por tipo de cliente
        else if (opcion == 3) {
            // Mostrar opciones de tipo de cliente
            System.out.println("\nTipos de cliente:");
            System.out.println("1. Regular");
            System.out.println("2. Estudiante");
            System.out.println("3. Tercera Edad");
            System.out.print("Seleccione tipo de cliente: ");

            int tipoBusqueda = obtenerEntradaValida(scanner, 1, 3);

            if (tipoBusqueda == -1) {
                scanner.nextLine(); // Limpiar buffer
                return;
            }

            System.out.println("\nEntradas encontradas:");

            // Recorrer todos los registros buscando coincidencias por tipo de cliente
            for (int i = 0; i < totalEntradasVendidas; i++) {
                boolean coincide = false;

                // Determinar si hay coincidencia según el tipo de cliente
                if (tipoBusqueda == 1 && !esEstudiante[i] && !esTerceraEdad[i]) {
                    coincide = true; // Cliente regular (ni estudiante ni tercera edad)
                } else if (tipoBusqueda == 2 && esEstudiante[i]) {
                    coincide = true; // Cliente estudiante
                } else if (tipoBusqueda == 3 && esTerceraEdad[i]) {
                    coincide = true; // Cliente tercera edad
                }

                // Si hay coincidencia, mostrar la entrada
                if (coincide) {
                    mostrarEntrada(i);
                    encontrado = true;
                }
            }
        }

        // Si no se encontraron resultados, informar al usuario
        if (!encontrado) {
            System.out.println("No se encontraron entradas con los criterios especificados.");
        }
    }

    /**
     * Método para mostrar información de una entrada
     * Muestra los detalles de una entrada según su índice en los arrays
     */
    public static void mostrarEntrada(int indice) {
        // Determinar el tipo de cliente para mostrar
        String tipoCliente = "Regular";
        if (esEstudiante[indice]) tipoCliente = "Estudiante";
        if (esTerceraEdad[indice]) tipoCliente = "Tercera Edad";

        // Mostrar información completa de la entrada
        System.out.println("Entrada #" + numerosEntrada[indice] +
                " | Ubicación: " + ubicacionesEntrada[indice] +
                " | Precio: $" + preciosEntrada[indice] +
                " | Tipo de Cliente: " + tipoCliente);
    }

    /**
     * Método para eliminar una entrada
     * Elimina una entrada según su número y actualiza los contadores
     */
    public static void eliminarEntrada(Scanner scanner) {
        // Verificar si hay entradas para eliminar
        if (totalEntradasVendidas == 0) {
            System.out.println("No hay entradas para eliminar.");
            return;
        }

        // Solicitar número de entrada a eliminar
        System.out.println("\n=== ELIMINAR ENTRADA ===");
        System.out.print("Ingrese el número de entrada a eliminar: ");

        int numeroEliminar = obtenerEntradaValida(scanner, 1, Integer.MAX_VALUE);

        if (numeroEliminar == -1) {
            scanner.nextLine(); // Limpiar buffer
            return;
        }

        // Variable para almacenar el índice de la entrada a eliminar
        int indiceEliminar = -1;

        // Buscar el índice de la entrada a eliminar en el array
        for (int i = 0; i < totalEntradasVendidas; i++) {
            if (numerosEntrada[i] == numeroEliminar) {
                indiceEliminar = i;
                break;
            }
        }

        // Si encontramos la entrada, procedemos a eliminarla
        if (indiceEliminar != -1) {
            // Guardar la información antes de eliminar para actualizar contadores
            String ubicacionEliminada = ubicacionesEntrada[indiceEliminar];
            double precioEliminado = preciosEntrada[indiceEliminar];

            // Desplazar todos los elementos posteriores una posición hacia atrás
            // Esto elimina efectivamente el elemento en indiceEliminar
            for (int i = indiceEliminar; i < totalEntradasVendidas - 1; i++) {
                numerosEntrada[i] = numerosEntrada[i + 1];
                ubicacionesEntrada[i] = ubicacionesEntrada[i + 1];
                preciosEntrada[i] = preciosEntrada[i + 1];
                esEstudiante[i] = esEstudiante[i + 1];
                esTerceraEdad[i] = esTerceraEdad[i + 1];
            }

            // Actualizar contadores generales
            totalEntradasVendidas--;  // Reducir el total de entradas vendidas
            totalIngresos -= precioEliminado;  // Reducir los ingresos totales

            // Actualizar disponibilidad según la ubicación de la entrada eliminada
            if (ubicacionEliminada.equals("VIP")) {
                disponiblesVIP++;
            } else if (ubicacionEliminada.equals("Platea")) {
                disponiblesPlatea++;
            } else if (ubicacionEliminada.equals("General")) {
                disponiblesGeneral++;
            }

            System.out.println("Entrada #" + numeroEliminar + " eliminada con éxito.");
        } else {
            System.out.println("No se encontró ninguna entrada con el número " + numeroEliminar + ".");
        }
    }

    /**
     * Método para mostrar estadísticas
     * Muestra información general sobre ventas y disponibilidad
     */
    public static void mostrarEstadisticas() {
        // Mostrar información sobre ventas y disponibilidad
        System.out.println("\n=== ESTADÍSTICAS DE VENTAS ===");
        System.out.println("Total de entradas vendidas: " + totalEntradasVendidas);
        System.out.println("Total de ingresos: $" + totalIngresos);
        System.out.println("\nDisponibilidad actual:");
        System.out.println("- VIP: " + disponiblesVIP + "/50");
        System.out.println("- Platea: " + disponiblesPlatea + "/100");
        System.out.println("- General: " + disponiblesGeneral + "/200");
    }

    /**
     * Método para validar entrada numérica dentro de un rango
     * Verifica que el valor ingresado sea un número y esté dentro del rango especificado
     *
     * @param scanner El scanner para leer la entrada
     * @param minimo El valor mínimo aceptable (inclusivo)
     * @param maximo El valor máximo aceptable (inclusivo)
     * @return El valor válido o -1 si hay error
     */
    public static int obtenerEntradaValida(Scanner scanner, int minimo, int maximo) {
        try {
            // Intentar leer un entero
            int entrada = scanner.nextInt();

            // Verificar si está dentro del rango
            if (entrada >= minimo && entrada <= maximo) {
                return entrada;  // Valor válido
            } else {
                // Valor fuera de rango
                System.out.println("Error: Ingrese un valor entre " + minimo + " y " + maximo);
                return -1;  // Código de error
            }
        } catch (InputMismatchException e) {
            // Capturar excepción si no se ingresa un número
            System.out.println("Error: Ingrese un número válido");
            return -1;  // Código de error
        }
    }
}