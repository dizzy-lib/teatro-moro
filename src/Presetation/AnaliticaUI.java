package Presetation;

import Analitica.Analitica;
import sistemaVentas.TipoAsiento;
import sistemaVentas.Venta;
import utils.Validador;

import java.util.ArrayList;
import java.util.Scanner;

public class AnaliticaUI {
    Scanner sc = new Scanner(System.in);
    Validador validador = new Validador();

    public void mostrarResumenDeVentas(Analitica analitica) {
        ArrayList<Venta> ventas = analitica.obtenerVentas();

        System.out.println("\n\n--------------------------------");
        System.out.println("       Analítica de ventas      ");
        System.out.println("--------------------------------");
        System.out.println("# Total de ventas: " + ventas.size());
        System.out.println("--------------------------------");
        System.out.println("Vendidas VIP: " + analitica.obtenerVentasPorTipo(TipoAsiento.VIP).size());
        System.out.println("Vendidas Balcón: " + analitica.obtenerVentasPorTipo(TipoAsiento.BALCON).size());
        System.out.println("Vendidas Platea: " + analitica.obtenerVentasPorTipo(TipoAsiento.PLATEA).size());
        System.out.println("--------------------------------");
        System.out.println("$ Total recaudado: $" + analitica.obtenerTotalIngresos()+"\n\n");


        while (true) {
            try {
                System.out.print("Ingrese (0) para volver al menú: ");
                int opcion = sc.nextInt();

                if (!validador.validarInputMenu(0, 0, opcion)) {
                    System.out.println("Error: La opción ingresada no es valida, por favor intente de nuevo");
                    continue;
                }

                switch (opcion) {
                    case 0:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida. Por favor, ingrese un número.");
                sc.nextLine(); // Limpiar buffer
            }
        }
    }
    public void mostrarTotalIngresos(Analitica analitica) {
        System.out.println("\nIngresos totales: $" + analitica.obtenerTotalIngresos());
    }
}
