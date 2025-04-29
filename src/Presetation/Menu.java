package Presetation;

import Analitica.Analitica;
import sistemaVentas.SistemaVentas;
import utils.Validador;

import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    private Validador validador = new Validador();
    private SistemaVentas sistemaVentas;
    private AnaliticaUI analiticaUI;
    private SistemaVentasUI sistemaVentasUI;
    private Analitica analitica;

    // Variables estáticas para estadísticas globales
    private static int totalVentasRealizadas = 0;
    private static float totalIngresosAcumulados = 0;
    private static int totalAsientosDisponibles = 30; // 10 de cada tipo

    public Menu(SistemaVentas sistemaVentas) {
        this.sistemaVentas = sistemaVentas;
        this.analiticaUI = new AnaliticaUI();
        this.sistemaVentasUI = new SistemaVentasUI(sistemaVentas);
        this.analitica = new Analitica(sistemaVentas);
    }

    /**
     * Inicia el menú principal y maneja la interacción con el usuario
     */
    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            // Mostrar el menú principal
            mostrarOpciones();

            try {
                System.out.print("Ingrese su opción: ");
                int opcion = sc.nextInt();
                sc.nextLine(); // Limpiar buffer

                if (!validador.validarInputMenu(1, 5, opcion)) {
                    System.out.println("Error: La opción ingresada no es válida, por favor intente de nuevo");
                    continue;
                }

                switch (opcion) {
                    case 1:
                        this.sistemaVentasUI.mostrarVentaEntradas();
                        break;
                    case 2:
                        this.analiticaUI.mostrarResumenDeVentas(analitica);
                        break;
                    case 3:
                        this.sistemaVentasUI.mostrarHistorialBoletas();
                        break;
                    case 4:
                        this.analiticaUI.mostrarTotalIngresos(analitica);

                        // Esperar entrada del usuario para continuar
                        System.out.println("\nPresione Enter para continuar...");
                        sc.nextLine();
                        break;
                    case 5:
                        System.out.println("Gracias por su compra");
                        salir = true;
                        break;
                }

                // Actualizar estadísticas si no hemos salido
                if (!salir) {
                    actualizarEstadisticas();
                }
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida. Por favor, ingrese un número.");
                sc.nextLine(); // Limpiar buffer
            }
        }
    }

    /**
     * Actualiza las estadísticas globales
     */
    private void actualizarEstadisticas() {
        totalVentasRealizadas = this.sistemaVentas.ventas.size();
        totalIngresosAcumulados = this.analitica.obtenerTotalIngresos();
        totalAsientosDisponibles = 30 - totalVentasRealizadas;
    }

    /**
     * Muestra las opciones del menú principal
     */
    private void mostrarOpciones() {
        System.out.println("\n-------------------------------");
        System.out.println("         MENU TEATRO MORO      ");
        System.out.println("-------------------------------");
        System.out.println("1. Venta de entrada");
        System.out.println("2. Mostrar resumen de ventas");
        System.out.println("3. Ver historial de boletas");
        System.out.println("4. Ver ingresos totales");
        System.out.println("5. Salir");
        System.out.println("-------------------------------");

        // Mostrar estadísticas actuales
        System.out.println("Estadísticas actuales:");
        System.out.println("- Ventas realizadas: " + totalVentasRealizadas);
        System.out.println("- Ingresos totales: $" + totalIngresosAcumulados);
        System.out.println("- Asientos disponibles: " + totalAsientosDisponibles);
        System.out.println("-------------------------------");
    }
}