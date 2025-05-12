package ui;

import client.Client;
import descuentos.Descuento;
import descuentos.SistemaDescuento;
import enums.EstadoAsiento;
import enums.GeneroCliente;
import teatro.Asiento;
import teatro.Sala;
import ventas.Cotizacion;
import ventas.SistemaVentas;
import ventas.Venta;

import java.util.List;
import java.util.Scanner;

/**
 * Clase que maneja la interfaz de usuario para el sistema de venta de entradas
 * del Teatro Moro.
 */
public class Menu {
    private final Scanner scanner;
    private final Sala sala;
    private SistemaDescuento sistemaDescuento;
    private final SistemaVentas sistemaVentas;
    private static final String LINEA_DIVISORIA = "══════════════════════════════════════";
    private static final String LINEA_CORTA = "──────────────────────────────";

    /**
     * Constructor que inicializa los componentes del sistema.
     */
    public Menu() {
        this.scanner = new Scanner(System.in);
        this.sala = new Sala();
        inicializarSistemaDescuentos();
        this.sistemaVentas = new SistemaVentas(sistemaDescuento);
    }

    /**
     * Inicializa el sistema de descuentos con los diferentes tipos disponibles.
     */
    private void inicializarSistemaDescuentos() {
        this.sistemaDescuento = new SistemaDescuento();

        sistemaDescuento.agregarDescuento(new Descuento("DESCUENTO_FEMININO", 0.2f));
        sistemaDescuento.agregarDescuento(new Descuento("DESCUENTO_NIÑO", 0.1f));
        sistemaDescuento.agregarDescuento(new Descuento("DESCUENTO_TERCERA_EDAD", 0.25f));
        sistemaDescuento.agregarDescuento(new Descuento("DESCUENTO_ESTUDIANTE", 0.25f));
    }

    /**
     * Método principal que inicia el menú interactivo.
     */
    public void iniciar() {
        boolean salir = false;
        limpiarPantalla();
        mostrarBienvenida();

        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion(1, 4);

            switch (opcion) {
                case 1:
                    procesarVentaEntrada();
                    break;
                case 2:
                    mostrarHistorialBoletas();
                    break;
                case 3:
                    mostrarEstadisticas();
                    break;
                case 4:
                    confirmarSalida();
                    salir = true;
                    break;
            }
        }
    }

    /**
     * Muestra un mensaje de bienvenida al sistema.
     */
    private void mostrarBienvenida() {
        System.out.println(LINEA_DIVISORIA);
        System.out.println("    ✧ BIENVENIDO AL TEATRO MORO ✧    ");
        System.out.println("      Sistema de Venta de Entradas     ");
        System.out.println(LINEA_DIVISORIA);
        System.out.println("\nEl lugar donde la magia del teatro cobra vida");
        esperarEnter();
    }

    /**
     * Muestra el menú principal con las opciones disponibles.
     */
    private void mostrarMenu() {
        limpiarPantalla();
        System.out.println(LINEA_DIVISORIA);
        System.out.println("          TEATRO MORO - MENÚ           ");
        System.out.println(LINEA_DIVISORIA);
        System.out.println("1. ✓ Venta de entradas");
        System.out.println("2. 📋 Ver historial de boletas");
        System.out.println("3. 📊 Mostrar estadísticas");
        System.out.println("4. 🚪 Salir");
        System.out.println(LINEA_DIVISORIA);
    }

    /**
     * Método para procesar la venta de una entrada, dividido en pasos para
     * mejorar la experiencia del usuario.
     */
    private void procesarVentaEntrada() {
        limpiarPantalla();
        mostrarEncabezado("VENTA DE ENTRADAS");

        // Paso 1: Selección de asiento
        Asiento asientoSeleccionado = seleccionarAsiento();
        if (asientoSeleccionado == null) {
            return; // Volver al menú principal si se cancela
        }

        // Paso 2: Registro del cliente
        Client cliente = registrarCliente();

        // Paso 3: Generación y confirmación de cotización
        Cotizacion cotizacion = sistemaVentas.generarCotizacion(cliente, asientoSeleccionado);
        mostrarResumenCotizacion(cotizacion);

        if (!confirmarCompra()) {
            System.out.println("\n▶ Venta cancelada. Volviendo al menú principal...");
            esperarEnter();
            return;
        }

        // Paso 4: Procesamiento de la venta
        limpiarPantalla();

        sistemaVentas.generarVenta(cotizacion);
        mostrarVentaExitosa(cotizacion);

        esperarEnter();
    }

    /**
     * Muestra un encabezado formateado.
     */
    private void mostrarEncabezado(String titulo) {
        System.out.println(LINEA_DIVISORIA);
        System.out.println("          " + titulo + "          ");
        System.out.println(LINEA_DIVISORIA);
    }

    /**
     * Permite al usuario seleccionar un asiento disponible.
     */
    private Asiento seleccionarAsiento() {
        System.out.println("\n▶ PASO 1: SELECCIÓN DE ASIENTO");
        System.out.println(LINEA_CORTA);
        System.out.println("Seleccione un asiento disponible del plano:");

        this.sala.dibujarPlanoTeatroNumerado();

        System.out.println("\n📋 Guía de asientos:");
        System.out.println("😎 Los asientos coloreados están disponibles");
        System.out.println("\nEscriba 'cancelar' para volver al menú principal");

        Asiento asientoSeleccionado = null;
        boolean asientoCorrecto = false;

        while (!asientoCorrecto) {
            System.out.print("\n➤ Ingrese código de asiento: ");
            String entrada = scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("cancelar")) {
                return null;
            }

            asientoSeleccionado = this.sala.obtenerAsiento(entrada);

            if (asientoSeleccionado == null) {
                System.out.println("❌ Asiento no encontrado. Verifique el código e inténtelo nuevamente.");
                continue;
            }

            if (asientoSeleccionado.obtenerEstado() == EstadoAsiento.OCUPADO) {
                System.out.println("❌ El asiento seleccionado ya está ocupado. Por favor, elija otro.");
                continue;
            }

            asientoCorrecto = true;
            System.out.println("✓ Asiento " + entrada + " seleccionado correctamente.");
        }

        return asientoSeleccionado;
    }

    /**
     * Registra los datos del cliente y aplica los descuentos correspondientes.
     */
    private Client registrarCliente() {
        System.out.println("\n\n▶ PASO 2: DATOS DEL CLIENTE");
        System.out.println(LINEA_CORTA);

        // Nombre
        String nombre = "";
        boolean nombreValido = false;

        while (!nombreValido) {
            System.out.print("➤ Nombre completo: ");
            nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("❌ El nombre no puede estar vacío.");
            } else if (nombre.length() < 3) {
                System.out.println("❌ El nombre debe tener al menos 3 caracteres.");
            } else {
                nombreValido = true;
            }
        }

        // Edad
        int edad = 0;
        boolean edadValida = false;

        while (!edadValida) {
            System.out.print("➤ Edad: ");
            try {
                edad = Integer.parseInt(scanner.nextLine().trim());
                if (edad <= 0 || edad > 110) {
                    System.out.println("❌ La edad debe estar entre 1 y 110 años.");
                } else {
                    edadValida = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
            }
        }

        // Género
        System.out.println("➤ Género:");
        System.out.println("   1. Masculino");
        System.out.println("   2. Femenino");
        int opcionGenero = leerOpcion(1, 2);
        GeneroCliente genero = GeneroCliente.values()[opcionGenero - 1];

        // Estudiante (solo si está en el rango de edad apropiado)
        boolean esEstudiante = false;

        if (edad >= 18 && edad < 60) {
            System.out.print("➤ ¿Es estudiante? (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();

            while (!respuesta.equals("S") && !respuesta.equals("N")) {
                System.out.print("  Por favor, responda S o N: ");
                respuesta = scanner.nextLine().trim().toUpperCase();
            }

            esEstudiante = respuesta.equals("S");
        }

        return new Client(nombre, edad, genero, esEstudiante);
    }

    /**
     * Muestra el resumen de la cotización con todos los detalles relevantes.
     */
    private void mostrarResumenCotizacion(Cotizacion cotizacion) {
        limpiarPantalla();
        System.out.println("\n▶ PASO 3: RESUMEN DE COMPRA");
        System.out.println(LINEA_CORTA);
        System.out.println(cotizacion.generarResumen());

        // Aquí añadimos información más detallada y amigable
        System.out.println("\n📋 DETALLE DE DESCUENTOS APLICADOS:");

        if (cotizacion.obtenerDescuentoAplicado() > 0) {
            System.out.println("  ✓ Descuento aplicado: " +
                    String.format("%.1f%%", cotizacion.obtenerDescuentoAplicado() * 100));
            System.out.println("  ✓ Ahorro: $" +
                    Math.round(cotizacion.obtenerPrecioOriginal() * cotizacion.obtenerDescuentoAplicado()));
        } else {
            System.out.println("  ✗ No se aplicaron descuentos para esta compra.");
        }
    }

    /**
     * Solicita confirmación al usuario para proceder con la compra.
     */
    private boolean confirmarCompra() {
        System.out.print("\n➤ ¿Confirma la compra? (S/N): ");
        String respuesta = scanner.nextLine().trim().toUpperCase();

        while (!respuesta.equals("S") && !respuesta.equals("N")) {
            System.out.print("  Por favor, responda S o N: ");
            respuesta = scanner.nextLine().trim().toUpperCase();
        }

        return respuesta.equals("S");
    }

    /**
     * Muestra un mensaje cuando la venta se ha completado con éxito.
     */
    private void mostrarVentaExitosa(Cotizacion cotizacion) {

        System.out.println("\n" + LINEA_DIVISORIA);
        System.out.println("        ¡VENTA REALIZADA CON ÉXITO!       ");
        System.out.println(LINEA_DIVISORIA);

        System.out.println("\n🎫 DETALLES DE SU ENTRADA:");
        System.out.println("  Cliente: " + cotizacion.obtenerCliente().obtenerNombre());
        System.out.println("  Asiento: " + cotizacion.obtenerAsiento().obtenerCodigo());
        System.out.println("  Precio final: $" + cotizacion.obtenerPrecioVenta());

        System.out.println("\n¡Gracias por su compra! Disfrute de la función.");
    }

    /**
     * Muestra el historial de boletas (a implementar).
     */
    private void mostrarHistorialBoletas() {
        limpiarPantalla();
        mostrarEncabezado("HISTORIAL DE BOLETAS");

        List<Venta> ventas = sistemaVentas.obtenerHistorialVentas();

        if (ventas == null || ventas.isEmpty()) {
            System.out.println("\nNo hay ventas registradas en el sistema.");
        } else {
            System.out.println("\nÚltimas ventas realizadas:");
            System.out.println(LINEA_CORTA);

            for (int i = 0; i < ventas.size(); i++) {
                Venta venta = ventas.get(i);
                System.out.println("Boleta #" + (i + 1));
                System.out.println("  Cliente: " + venta.obtenerCotizacion().obtenerCliente().obtenerNombre());
                System.out.println("  Asiento: " + venta.obtenerCotizacion().obtenerAsiento().obtenerCodigo());
                System.out.println("  Precio: $" + venta.obtenerCotizacion().obtenerPrecioVenta());
                System.out.println(LINEA_CORTA);
            }
        }

        esperarEnter();
    }

    /**
     * Muestra estadísticas de ventas e información relevante (a implementar).
     */
    private void mostrarEstadisticas() {
        limpiarPantalla();
        mostrarEncabezado("ESTADÍSTICAS");

        List<Venta> ventas = sistemaVentas.obtenerHistorialVentas();

        if (ventas == null || ventas.isEmpty()) {
            System.out.println("\nNo hay datos suficientes para mostrar estadísticas.");
            esperarEnter();
            return;
        }

        int totalVentas = ventas.size();
        double ingresoTotal = 0;
        int asientosOcupados = 0;
        int asientosDisponibles = 0;

        // Calcular estadísticas básicas
        for (Venta venta : ventas) {
            ingresoTotal += venta.obtenerCotizacion().obtenerPrecioVenta();
        }

        // Contar asientos ocupados y disponibles
        for (Asiento asiento : sala.obtenerTodosLosAsientos()) {
            if (asiento.obtenerEstado() == EstadoAsiento.OCUPADO) {
                asientosOcupados++;
            } else {
                asientosDisponibles++;
            }
        }

        int capacidadTotal = asientosOcupados + asientosDisponibles;
        double porcentajeOcupacion = (double) asientosOcupados / capacidadTotal * 100;

        // Mostrar estadísticas
        System.out.println("\n📊 RESUMEN DE VENTAS:");
        System.out.println("  Total de ventas: " + totalVentas);
        System.out.println("  Ingreso total: $" + ingresoTotal);
        System.out.println("  Precio promedio por entrada: $" +
                String.format("%.2f", ingresoTotal / totalVentas));

        System.out.println("\n📊 OCUPACIÓN DEL TEATRO:");
        System.out.println("  Asientos ocupados: " + asientosOcupados + " de " + capacidadTotal);
        System.out.println("  Asientos disponibles: " + asientosDisponibles);
        System.out.println("  Porcentaje de ocupación: " + String.format("%.1f%%", porcentajeOcupacion));

        System.out.println("\n📊 DETALLE DE DESCUENTOS:");
        System.out.println("  Niños: %" + sistemaVentas.calcularDescuentosPorTipo("DESCUENTO_NIÑO") * 100);
        System.out.println("  Femenino: %" + sistemaVentas.calcularDescuentosPorTipo("DESCUENTO_FEMININO") * 100);
        System.out.println("  Tercera edad: %" + sistemaVentas.calcularDescuentosPorTipo("DESCUENTO_TERCERA_EDAD") * 100);
        System.out.println("  Estudiantes: %" + sistemaVentas.calcularDescuentosPorTipo("DESCUENTO_ESTUDIANTE") * 100);

        esperarEnter();
    }

    /**
     * Confirma si el usuario realmente desea salir del sistema.
     */
    private void confirmarSalida() {
        limpiarPantalla();
        System.out.println(LINEA_DIVISORIA);
        System.out.println("      ¡GRACIAS POR USAR TEATRO MORO!      ");
        System.out.println("          Esperamos su regreso            ");
        System.out.println(LINEA_DIVISORIA);
    }

    /**
     * Lee una opción del usuario dentro de un rango válido.
     */
    private int leerOpcion(int min, int max) {
        int opcion = -1;
        boolean opcionValida = false;

        while (!opcionValida) {
            try {
                System.out.print("\n➤ Ingrese su opción: ");
                opcion = Integer.parseInt(scanner.nextLine().trim());

                if (opcion >= min && opcion <= max) {
                    opcionValida = true;
                } else {
                    System.out.println("❌ La opción debe estar entre " + min + " y " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Debe ingresar un número entero");
            }
        }

        return opcion;
    }

    /**
     * Lee un entero del usuario con validación.
     */
    private int leerEntero() {
        int valor = 0;
        boolean valorValido = false;

        while (!valorValido) {
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                valorValido = true;
            } catch (NumberFormatException e) {
                System.out.println("❌ Debe ingresar un número entero");
                System.out.print("➤ Intente nuevamente: ");
            }
        }

        return valor;
    }

    /**
     * Espera a que el usuario presione Enter para continuar.
     */
    private void esperarEnter() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Limpia la pantalla de la consola (funciona mejor en terminales compatibles).
     */
    private void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Alternativa compatible con más consolas
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}