package Presetation;

import SistemaDescuento.Descuentos;
import sistemaVentas.Asiento;
import sistemaVentas.EstadoAsiento;
import sistemaVentas.SistemaVentas;
import sistemaVentas.TipoAsiento;
import sistemaVentas.Venta;
import sistemaVentas.Boleta;
import utils.Validador;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class SistemaVentasUI {
    private Scanner sc = new Scanner(System.in);
    private Validador validador = new Validador();
    private SistemaVentas sistemaVentas;

    // Estructura para almacenar asientos disponibles
    private ArrayList<Asiento> asientosVIP = new ArrayList<>();
    private ArrayList<Asiento> asientosPLATEA = new ArrayList<>();
    private ArrayList<Asiento> asientosBALCON = new ArrayList<>();

    // Contador de asientos por tipo
    private static final int MAX_ASIENTOS_POR_TIPO = 10;

    public SistemaVentasUI(SistemaVentas sistemaVentas) {
        this.sistemaVentas = sistemaVentas;

        // Inicializar asientos
        inicializarAsientos();
    }

    private void inicializarAsientos() {
        // Crear asientos VIP
        for (int i = 0; i < MAX_ASIENTOS_POR_TIPO; i++) {
            asientosVIP.add(new Asiento(TipoAsiento.VIP));
        }

        // Crear asientos PLATEA
        for (int i = 0; i < MAX_ASIENTOS_POR_TIPO; i++) {
            asientosPLATEA.add(new Asiento(TipoAsiento.PLATEA));
        }

        // Crear asientos BALCON
        for (int i = 0; i < MAX_ASIENTOS_POR_TIPO; i++) {
            asientosBALCON.add(new Asiento(TipoAsiento.BALCON));
        }
    }

    public void mostrarVentaEntradas() {
        System.out.println("\n\n--------------------------------");
        System.out.println("       Venta de Entradas        ");
        System.out.println("--------------------------------");

        // Variable para almacenar el tipo de asiento seleccionado
        TipoAsiento tipoSeleccionado = null;

        // Variables para aplicar descuentos
        boolean esEstudiante = false;
        boolean esTerceraEdad = false;
        String codigoDescuento = "";

        // Paso 1: Seleccionar ubicación
        while (tipoSeleccionado == null) {
            try {
                // Mostrar disponibilidad de asientos
                System.out.println("Asientos disponibles:");
                System.out.println("1. VIP: " + contarAsientosDisponibles(asientosVIP) + " disponibles");
                System.out.println("2. PLATEA: " + contarAsientosDisponibles(asientosPLATEA) + " disponibles");
                System.out.println("3. BALCON: " + contarAsientosDisponibles(asientosBALCON) + " disponibles");
                System.out.println("0. Volver al menú principal");

                System.out.print("Seleccione el tipo de asiento: ");
                int opcion = sc.nextInt();
                sc.nextLine(); // Limpiar buffer

                if (!validador.validarInputMenu(0, 3, opcion)) {
                    System.out.println("Error: La opción ingresada no es válida, por favor intente de nuevo");
                    continue;
                }

                if (opcion == 0) {
                    return; // Volver al menú principal
                }

                // Asignar tipo según selección
                switch (opcion) {
                    case 1:
                        tipoSeleccionado = TipoAsiento.VIP;
                        if (contarAsientosDisponibles(asientosVIP) == 0) {
                            System.out.println("Lo sentimos, no hay asientos VIP disponibles.");
                            tipoSeleccionado = null;
                        }
                        break;
                    case 2:
                        tipoSeleccionado = TipoAsiento.PLATEA;
                        if (contarAsientosDisponibles(asientosPLATEA) == 0) {
                            System.out.println("Lo sentimos, no hay asientos de PLATEA disponibles.");
                            tipoSeleccionado = null;
                        }
                        break;
                    case 3:
                        tipoSeleccionado = TipoAsiento.BALCON;
                        if (contarAsientosDisponibles(asientosBALCON) == 0) {
                            System.out.println("Lo sentimos, no hay asientos de BALCÓN disponibles.");
                            tipoSeleccionado = null;
                        }
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida. Por favor, ingrese un número.");
                sc.nextLine(); // Limpiar buffer
            }
        }

        // Paso 2: Preguntar la edad y aplicar descuento automáticamente
        int edad = 0;
        boolean edadValida = false;

        while (!edadValida) {
            try {
                System.out.print("\nPor favor, ingrese la edad del cliente: ");
                edad = sc.nextInt();
                sc.nextLine(); // Limpiar buffer

                // Validar que la edad sea un valor razonable
                if (edad <= 0 || edad > 110) {
                    System.out.println("Error: La edad debe ser un valor positivo entre 1 y 110 años.");
                    continue;
                }

                edadValida = true;
            } catch (Exception e) {
                System.out.println("Error: Debe ingresar un número entero para la edad.");
                sc.nextLine(); // Limpiar buffer
            }
        }

        // Una vez validada la edad, aplicar el descuento correspondiente
        if (edad < 18) {
            System.out.println("Se aplicará descuento de Estudiante (10%)");
            codigoDescuento = Descuentos.ESTUDIANTES.toString();
            esEstudiante = true;
        } else if (edad >= 60) {
            System.out.println("Se aplicará descuento de Tercera Edad (15%)");
            codigoDescuento = Descuentos.TERCERA_EDAD.toString();
            esTerceraEdad = true;
        } else {
            System.out.println("No se aplica ningún descuento por edad");
            codigoDescuento = "";
        }

        // Paso 3: Seleccionar un asiento disponible y marcarlo como ocupado
        Asiento asientoSeleccionado = null;

        switch (tipoSeleccionado) {
            case VIP:
                asientoSeleccionado = obtenerPrimerAsientoDisponible(asientosVIP);
                break;
            case PLATEA:
                asientoSeleccionado = obtenerPrimerAsientoDisponible(asientosPLATEA);
                break;
            case BALCON:
                asientoSeleccionado = obtenerPrimerAsientoDisponible(asientosBALCON);
                break;
        }

        if (asientoSeleccionado != null) {
            // Marcar el asiento como ocupado
            asientoSeleccionado.setEstado(EstadoAsiento.OCUPADO);

            // Paso 4: Realizar la venta
            // Aseguramos que el código de descuento se pase correctamente
            Optional<String> codigoDescuentoOptional =
                    codigoDescuento.isEmpty() ? Optional.empty() : Optional.of(codigoDescuento);

            sistemaVentas.generarVenta(asientoSeleccionado, codigoDescuentoOptional);

            // Mostrar confirmación
            System.out.println("\n¡Venta realizada con éxito!");

            // Obtener la última venta para mostrar la boleta
            Venta ultimaVenta = sistemaVentas.ventas.get(sistemaVentas.ventas.size() - 1);
            Boleta boleta = ultimaVenta.generarBoleta();

            // Mostrar la boleta
            boleta.mostrarBoleta();

            // Esperar a que el usuario presione Enter para continuar
            System.out.println("\nPresione Enter para continuar...");
            sc.nextLine();
        } else {
            System.out.println("Error: No se pudo seleccionar un asiento. Venta cancelada.");
        }
    }

    private int contarAsientosDisponibles(ArrayList<Asiento> asientos) {
        int disponibles = 0;
        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == EstadoAsiento.DISPONIBLE) {
                disponibles++;
            }
        }
        return disponibles;
    }

    private Asiento obtenerPrimerAsientoDisponible(ArrayList<Asiento> asientos) {
        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == EstadoAsiento.DISPONIBLE) {
                return asiento;
            }
        }
        return null;
    }

    public void mostrarHistorialBoletas() {
        System.out.println("\n\n--------------------------------");
        System.out.println("     Historial de Boletas      ");
        System.out.println("--------------------------------");

        ArrayList<Venta> ventas = sistemaVentas.ventas;

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            System.out.println("Total de boletas: " + ventas.size());

            for (int i = 0; i < ventas.size(); i++) {
                System.out.println("\nBoleta #" + (i + 1) + ":");
                Boleta boleta = ventas.get(i).generarBoleta();
                boleta.mostrarBoleta();
            }
        }

        // Esperar a que el usuario presione Enter para continuar
        System.out.println("\nPresione Enter para continuar...");
        sc.nextLine();
    }
}