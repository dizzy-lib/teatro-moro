import java.util.Optional;
import java.util.Scanner;

/**
 * Menú principal optimizado para el Teatro Moro
 */
public class MenuOptimizado {
    private SistemaAsientos sistemaAsientos;
    private SistemaClientes sistemaClientes;
    private SistemaVentas sistemaVentas;
    private SistemaEstadisticas sistemaEstadisticas;
    private Scanner scanner;

    // Constructor
    public MenuOptimizado() {
        this.sistemaAsientos = new SistemaAsientos();
        this.sistemaClientes = new SistemaClientes();
        this.sistemaVentas = new SistemaVentas(sistemaAsientos);
        this.sistemaEstadisticas = new SistemaEstadisticas(sistemaAsientos, sistemaVentas);
        this.scanner = new Scanner(System.in);
    }

    // Método principal para iniciar el menú
    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion(0, 4);

            switch (opcion) {
                case 1:
                    mostrarVentaEntradas();
                    break;
                case 2:
                    sistemaVentas.mostrarBoletasVentas();
                    esperarEnter();
                    break;
                case 3:
                    sistemaEstadisticas.mostrarResumen();
                    esperarEnter();
                    break;
                case 4:
                    System.out.println("Gracias por usar el sistema del Teatro Moro");
                    salir = true;
                    break;
            }
        }
    }

    // Método para mostrar el menú principal
    private void mostrarMenu() {
        System.out.println("\n-------------------------------");
        System.out.println("         MENU TEATRO MORO      ");
        System.out.println("-------------------------------");
        System.out.println("1. Venta de entradas");
        System.out.println("2. Ver historial de boletas");
        System.out.println("3. Mostrar estadísticas");
        System.out.println("4. Salir");
        System.out.println("-------------------------------");

        // Mostrar estadísticas rápidas
        float ocupacionTotal = sistemaEstadisticas.calcularOcupacion();
        float ingresosTotal = sistemaVentas.calcularIngresos();

        System.out.println("Estadísticas actuales:");
        System.out.println("- Ocupación: " + String.format("%.1f", ocupacionTotal) + "%");
        System.out.println("- Ingresos totales: $" + String.format("%.1f", ingresosTotal));
        System.out.println("-------------------------------");
    }

    // Método para leer una opción del usuario y validarla
    private int leerOpcion(int min, int max) {
        int opcion = -1;
        boolean opcionValida = false;

        while (!opcionValida) {
            try {
                System.out.print("Ingrese su opción: ");
                opcion = Integer.parseInt(scanner.nextLine());

                if (opcion >= min && opcion <= max) {
                    opcionValida = true;
                } else {
                    System.out.println("Error: La opción debe estar entre " + min + " y " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero");
            }
        }

        return opcion;
    }

    // Método para mostrar la venta de entradas
    private void mostrarVentaEntradas() {
        System.out.println("\n\n--------------------------------");
        System.out.println("       Venta de Entradas        ");
        System.out.println("--------------------------------");

        // Paso 1: Seleccionar tipo de asiento
        TipoAsiento tipoSeleccionado = seleccionarTipoAsiento();
        if (tipoSeleccionado == null) return;

        // Paso 2: Registrar cliente
        Cliente cliente = registrarCliente();
        if (cliente == null) return;

        // Paso 3: Obtener asiento disponible
        Optional<Asiento> asientoOpcional = sistemaAsientos.obtenerAsientoDisponible(tipoSeleccionado);

        if (asientoOpcional.isPresent()) {
            Asiento asiento = asientoOpcional.get();

            // Paso 4: Realizar venta
            Optional<Venta> ventaOpcional = sistemaVentas.realizarVenta(asiento, cliente);

            if (ventaOpcional.isPresent()) {
                Venta venta = ventaOpcional.get();
                System.out.println("\n¡Venta realizada con éxito!");
                venta.generarBoleta().mostrar();
            } else {
                System.out.println("Error: No se pudo realizar la venta.");
            }
        } else {
            System.out.println("Error: No hay asientos disponibles de tipo " + tipoSeleccionado);
        }

        esperarEnter();
    }

    // Método auxiliar para seleccionar un tipo de asiento
    private TipoAsiento seleccionarTipoAsiento() {
        System.out.println("Asientos disponibles:");
        System.out.println("1. VIP: " + sistemaAsientos.contarAsientosDisponibles(TipoAsiento.VIP) + " disponibles - $" + TipoAsiento.VIP.getPrecio());
        System.out.println("2. PLATEA: " + sistemaAsientos.contarAsientosDisponibles(TipoAsiento.PLATEA) + " disponibles - $" + TipoAsiento.PLATEA.getPrecio());
        System.out.println("3. BALCON: " + sistemaAsientos.contarAsientosDisponibles(TipoAsiento.BALCON) + " disponibles - $" + TipoAsiento.BALCON.getPrecio());
        System.out.println("0. Volver al menú principal");

        System.out.print("Seleccione el tipo de asiento: ");
        int opcion = leerOpcion(0, 3);

        if (opcion == 0) {
            return null;
        }

        TipoAsiento tipoSeleccionado = null;

        switch (opcion) {
            case 1:
                tipoSeleccionado = TipoAsiento.VIP;
                if (sistemaAsientos.contarAsientosDisponibles(TipoAsiento.VIP) == 0) {
                    System.out.println("Lo sentimos, no hay asientos VIP disponibles.");
                    tipoSeleccionado = null;
                }
                break;
            case 2:
                tipoSeleccionado = TipoAsiento.PLATEA;
                if (sistemaAsientos.contarAsientosDisponibles(TipoAsiento.PLATEA) == 0) {
                    System.out.println("Lo sentimos, no hay asientos de PLATEA disponibles.");
                    tipoSeleccionado = null;
                }
                break;
            case 3:
                tipoSeleccionado = TipoAsiento.BALCON;
                if (sistemaAsientos.contarAsientosDisponibles(TipoAsiento.BALCON) == 0) {
                    System.out.println("Lo sentimos, no hay asientos de BALCÓN disponibles.");
                    tipoSeleccionado = null;
                }
                break;
        }

        return tipoSeleccionado;
    }

    // Método auxiliar para registrar un cliente
    private Cliente registrarCliente() {
        System.out.println("\n--- Registro de Cliente ---");

        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();

        if (nombre.trim().isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return null;
        }

        System.out.print("Edad del cliente: ");
        int edad = leerEntero();

        if (edad <= 0 || edad > 110) {
            System.out.println("Error: La edad debe ser un valor positivo entre 1 y 110 años.");
            return null;
        }

        boolean esEstudiante = false;

        // Si tiene entre 18 y 60 años, preguntar si es estudiante
        if (edad >= 18 && edad < 60) {
            System.out.print("¿Es estudiante? (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();
            esEstudiante = respuesta.equals("S");
        }

        // Determinar tipo de descuento automáticamente
        TipoDescuento tipoDescuento;
        if (edad < 18 || esEstudiante) {
            tipoDescuento = TipoDescuento.ESTUDIANTE;
            System.out.println("Se aplicará descuento de Estudiante (" +
                    Math.round(TipoDescuento.ESTUDIANTE.getValor() * 100) + "%)");
        } else if (edad >= 60) {
            tipoDescuento = TipoDescuento.TERCERA_EDAD;
            System.out.println("Se aplicará descuento de Tercera Edad (" +
                    Math.round(TipoDescuento.TERCERA_EDAD.getValor() * 100) + "%)");
        } else {
            tipoDescuento = TipoDescuento.NINGUNO;
            System.out.println("No se aplica ningún descuento por edad");
        }

        return sistemaClientes.registrarCliente(nombre, edad, esEstudiante);
    }

    // Método auxiliar para leer un entero
    private int leerEntero() {
        int valor = 0;
        boolean valorValido = false;

        while (!valorValido) {
            try {
                valor = Integer.parseInt(scanner.nextLine());
                valorValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero");
                System.out.print("Intente nuevamente: ");
            }
        }

        return valor;
    }

    // Método para esperar a que el usuario presione Enter
    private void esperarEnter() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}