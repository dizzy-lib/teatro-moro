import java.util.Scanner;

public class UI {
    private Teatro teatro;
    private Scanner sc;

    public UI(Teatro teatro) {
        this.teatro = teatro;
        this.sc = new Scanner(System.in);
    }

    public void mostrarMenu() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n===== SISTEMA DE VENTA DE ENTRADAS - TEATRO " + teatro.nombre + " =====");
            System.out.println("1. Reservar asientos");
            System.out.println("2. Comprar entradas");
            System.out.println("3. Cancelar reserva");
            System.out.println("4. Imprimir boleta");
            System.out.println("5. Ver estado de salas");
            System.out.println("0. Salir");
            System.out.print("\nIngrese la opción que desea: ");

            try {
                int opcion = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1:
                        procesarReserva();
                        break;
                    case 2:
                        procesarCompra();
                        break;
                    case 3:
                        procesarCancelacionReserva();
                        break;
                    case 4:
                        imprimirBoleta();
                        break;
                    case 5:
                        mostrarSalas();
                        break;
                    case 0:
                        System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, intente de nuevo.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida. Por favor, ingrese un número.");
                sc.nextLine(); // Limpiar el buffer del scanner
            }
        }
    }

    private void procesarReserva() {
        if (teatro.salas.isEmpty()) {
            System.out.println("No hay salas disponibles en el teatro.");
            return;
        }

        System.out.println("\n===== RESERVA DE ASIENTOS =====");

        // Seleccionar sala
        int indiceSala = seleccionarSala();
        if (indiceSala == -1) return;

        Sala salaSeleccionada = teatro.getSala(indiceSala);

        // Mostrar sala
        System.out.println("\nEstado actual de la sala " + salaSeleccionada.getSalaId() + ":");
        mostrarTablaSala(salaSeleccionada);

        // Preguntar si es reserva individual o grupal
        System.out.println("\n¿Desea realizar una reserva individual o grupal?");
        System.out.println("1. Individual (un solo asiento)");
        System.out.println("2. Grupal (varios asientos)");
        System.out.print("Seleccione una opción: ");

        try {
            int tipoReserva = sc.nextInt();
            sc.nextLine(); // Consumir salto de línea

            if (tipoReserva == 1) {
                // Reserva individual
                procesarReservaIndividual(salaSeleccionada, indiceSala);
            } else if (tipoReserva == 2) {
                // Reserva grupal
                procesarReservaGrupal(salaSeleccionada, indiceSala);
            } else {
                System.out.println("Opción inválida. Volviendo al menú principal.");
            }
        } catch (Exception e) {
            System.out.println("Error: Entrada inválida. Por favor, ingrese un número.");
            sc.nextLine(); // Limpiar buffer
        }
    }

    private void procesarReservaIndividual(Sala sala, int indiceSala) {
        // Seleccionar asiento
        System.out.println("\nSeleccione el asiento que desea reservar:");
        int[] coordenadas = seleccionarAsiento(sala);
        if (coordenadas == null) return;

        // Realizar reserva
        boolean exito = sala.reservar(coordenadas[0], coordenadas[1]);

        if (exito) {
            System.out.println("\n¡Reserva exitosa del asiento en la fila " + coordenadas[0] +
                    ", columna " + coordenadas[1] + "!");
            System.out.println("Recuerde que dispone de 5 minutos para completar la compra antes de que expire la reserva.");

            // Mostrar nuevamente la sala para ver el cambio
            System.out.println("\nEstado actualizado de la sala:");
            mostrarTablaSala(sala);
        }
    }

    private void procesarReservaGrupal(Sala sala, int indiceSala) {
        int capacidadSala = sala.asientos.length * sala.asientos[0].length;
        int asientosDisponibles = contarAsientosDisponibles(sala);

        System.out.println("\nReserva grupal - Asientos disponibles: " + asientosDisponibles);
        System.out.print("Ingrese la cantidad de asientos a reservar (entre 2 y " +
                Math.min(10, asientosDisponibles) + "): ");

        try {
            int cantidadAsientos = sc.nextInt();
            sc.nextLine(); // Consumir salto de línea

            if (cantidadAsientos < 2) {
                System.out.println("Para reservas grupales debe seleccionar al menos 2 asientos.");
                return;
            }

            if (cantidadAsientos > asientosDisponibles) {
                System.out.println("No hay suficientes asientos disponibles.");
                return;
            }

            if (cantidadAsientos > 10) {
                System.out.println("El máximo de asientos por grupo es 10.");
                cantidadAsientos = 10;
                System.out.println("Se procesará la reserva para 10 asientos.");
            }

            // Guardar coordenadas de asientos reservados
            int[][] asientosReservados = new int[cantidadAsientos][2];
            int asientosExitosos = 0;

            // Proceso de reserva múltiple
            for (int i = 0; i < cantidadAsientos; i++) {
                System.out.println("\nSeleccionando asiento " + (i + 1) + " de " + cantidadAsientos);

                // Mostrar estado actual de la sala
                System.out.println("\nEstado actual de la sala:");
                mostrarTablaSala(sala);

                // Seleccionar asiento
                int[] coordenadas = seleccionarAsiento(sala);
                if (coordenadas == null) {
                    System.out.println("Operación cancelada.");

                    // Cancelar las reservas ya realizadas
                    for (int j = 0; j < asientosExitosos; j++) {
                        sala.cancelarReserva(asientosReservados[j][0], asientosReservados[j][1]);
                    }
                    return;
                }

                // Verificar que el asiento no esté ya reservado o vendido
                if (sala.asientos[coordenadas[0]-1][coordenadas[1]-1].estado() != EstadoAsiento.DISPONIBLE) {
                    System.out.println("Este asiento no está disponible. Por favor seleccione otro.");
                    i--; // Repetir esta iteración
                    continue;
                }

                // Realizar reserva
                boolean exito = sala.reservar(coordenadas[0], coordenadas[1]);

                if (exito) {
                    asientosReservados[asientosExitosos][0] = coordenadas[0];
                    asientosReservados[asientosExitosos][1] = coordenadas[1];
                    asientosExitosos++;

                    System.out.println("Asiento reservado: Fila " + coordenadas[0] + ", Columna " + coordenadas[1]);
                } else {
                    System.out.println("No se pudo reservar el asiento. Por favor seleccione otro.");
                    i--; // Repetir esta iteración
                }
            }

            System.out.println("\n¡Reserva grupal completada exitosamente!");
            System.out.println("Se han reservado " + asientosExitosos + " asientos.");
            System.out.println("Recuerde que dispone de 5 minutos para completar la compra antes de que expire la reserva.");

            // Mostrar estado final de la sala
            System.out.println("\nEstado final de la sala:");
            mostrarTablaSala(sala);

        } catch (Exception e) {
            System.out.println("Error: Entrada inválida. Por favor, ingrese un número.");
            sc.nextLine(); // Limpiar buffer
        }
    }

    private void procesarCompra() {
        if (teatro.salas.isEmpty()) {
            System.out.println("No hay salas disponibles en el teatro.");
            return;
        }

        System.out.println("\n===== COMPRA DE ENTRADAS =====");

        // Seleccionar sala
        int indiceSala = seleccionarSala();
        if (indiceSala == -1) return;

        Sala salaSeleccionada = teatro.getSala(indiceSala);

        // Mostrar sala
        System.out.println("\nEstado actual de la sala " + salaSeleccionada.getSalaId() + ":");
        mostrarTablaSala(salaSeleccionada);

        // Preguntar si es compra individual o grupal
        System.out.println("\n¿Desea realizar una compra individual o grupal?");
        System.out.println("1. Individual (un solo asiento)");
        System.out.println("2. Grupal (varios asientos)");
        System.out.print("Seleccione una opción: ");

        try {
            int tipoCompra = sc.nextInt();
            sc.nextLine(); // Consumir salto de línea

            if (tipoCompra == 1) {
                // Compra individual
                procesarCompraIndividual(salaSeleccionada, indiceSala);
            } else if (tipoCompra == 2) {
                // Compra grupal
                procesarCompraGrupal(salaSeleccionada, indiceSala);
            } else {
                System.out.println("Opción inválida. Volviendo al menú principal.");
            }
        } catch (Exception e) {
            System.out.println("Error: Entrada inválida. Por favor, ingrese un número.");
            sc.nextLine(); // Limpiar buffer
        }
    }

    private void procesarCompraIndividual(Sala sala, int indiceSala) {
        // Seleccionar asiento
        System.out.println("\nSeleccione el asiento que desea comprar:");
        int[] coordenadas = seleccionarAsiento(sala);
        if (coordenadas == null) return;

        // Realizar compra
        boolean exito = sala.comprar(coordenadas[0], coordenadas[1]);

        if (exito) {
            // Generar boleta
            Boleta boleta = new Boleta(indiceSala, coordenadas[0], coordenadas[1], 2000.0, 1);

            System.out.println("\n¡Compra exitosa del asiento en la fila " + coordenadas[0] +
                    ", columna " + coordenadas[1] + "!");

            // Mostrar estado actualizado de la sala
            System.out.println("\nEstado actualizado de la sala:");
            mostrarTablaSala(sala);

            // Preguntar si quiere ver la boleta
            System.out.print("\n¿Desea ver la boleta? (S/N): ");
            String respuesta = sc.nextLine().trim().toUpperCase();
            if (respuesta.equals("S")) {
                System.out.println("\n" + boleta.toString());
            }
        }
    }

    private void procesarCompraGrupal(Sala sala, int indiceSala) {
        int capacidadSala = sala.asientos.length * sala.asientos[0].length;
        int asientosDisponibles = contarAsientosDisponibles(sala);

        System.out.println("\nCompra grupal - Asientos disponibles: " + asientosDisponibles);
        System.out.print("Ingrese la cantidad de asientos a comprar (entre 2 y " +
                Math.min(10, asientosDisponibles) + "): ");

        try {
            int cantidadAsientos = sc.nextInt();
            sc.nextLine(); // Consumir salto de línea

            if (cantidadAsientos < 2) {
                System.out.println("Para compras grupales debe seleccionar al menos 2 asientos.");
                return;
            }

            if (cantidadAsientos > asientosDisponibles) {
                System.out.println("No hay suficientes asientos disponibles.");
                return;
            }

            if (cantidadAsientos > 10) {
                System.out.println("El máximo de asientos por grupo es 10.");
                cantidadAsientos = 10;
                System.out.println("Se procesará la compra para 10 asientos.");
            }

            // Guardar coordenadas de asientos comprados
            int[][] asientosComprados = new int[cantidadAsientos][2];
            int asientosExitosos = 0;

            // Proceso de compra múltiple
            for (int i = 0; i < cantidadAsientos; i++) {
                System.out.println("\nSeleccionando asiento " + (i + 1) + " de " + cantidadAsientos);

                // Mostrar estado actual de la sala
                System.out.println("\nEstado actual de la sala:");
                mostrarTablaSala(sala);

                // Seleccionar asiento
                int[] coordenadas = seleccionarAsiento(sala);
                if (coordenadas == null) {
                    System.out.println("Operación cancelada.");
                    return;
                }

                // Verificar que el asiento esté disponible o reservado
                EstadoAsiento estadoActual = sala.asientos[coordenadas[0]-1][coordenadas[1]-1].estado();
                if (estadoActual == EstadoAsiento.VENDIDO) {
                    System.out.println("Este asiento ya está vendido. Por favor seleccione otro.");
                    i--; // Repetir esta iteración
                    continue;
                }

                // Realizar compra
                boolean exito = sala.comprar(coordenadas[0], coordenadas[1]);

                if (exito) {
                    asientosComprados[asientosExitosos][0] = coordenadas[0];
                    asientosComprados[asientosExitosos][1] = coordenadas[1];
                    asientosExitosos++;

                    System.out.println("Asiento comprado: Fila " + coordenadas[0] + ", Columna " + coordenadas[1]);
                } else {
                    System.out.println("No se pudo comprar el asiento. Por favor seleccione otro.");
                    i--; // Repetir esta iteración
                }
            }

            if (asientosExitosos > 0) {
                System.out.println("\n¡Compra grupal completada exitosamente!");
                System.out.println("Se han comprado " + asientosExitosos + " asientos.");

                // Generar una boleta para todo el grupo
                double precio = 2000.0;
                if (asientosExitosos >= 3) {
                    precio = precio * 0.9; // 10% de descuento para grupos de 3 o más
                    System.out.println("¡Se ha aplicado un 10% de descuento por compra grupal!");
                }

                Boleta boleta = new Boleta(
                        indiceSala,
                        asientosComprados[0][0],
                        asientosComprados[0][1],
                        precio,
                        asientosExitosos
                );

                // Mostrar estado final de la sala
                System.out.println("\nEstado final de la sala:");
                mostrarTablaSala(sala);

                // Preguntar si quiere ver la boleta
                System.out.print("\n¿Desea ver la boleta? (S/N): ");
                String respuesta = sc.nextLine().trim().toUpperCase();
                if (respuesta.equals("S")) {
                    System.out.println("\n" + boleta.toString());
                }
            } else {
                System.out.println("\nNo se completó ninguna compra.");
            }

        } catch (Exception e) {
            System.out.println("Error: Entrada inválida. Por favor, ingrese un número.");
            sc.nextLine(); // Limpiar buffer
        }
    }

    // Método para contar asientos disponibles en una sala
    private int contarAsientosDisponibles(Sala sala) {
        int disponibles = 0;

        for (int i = 0; i < sala.asientos.length; i++) {
            for (int j = 0; j < sala.asientos[i].length; j++) {
                if (sala.asientos[i][j].estado() == EstadoAsiento.DISPONIBLE) {
                    disponibles++;
                }
            }
        }

        return disponibles;
    }

    private void procesarCancelacionReserva() {
        if (teatro.salas.isEmpty()) {
            System.out.println("No hay salas disponibles en el teatro.");
            return;
        }

        System.out.println("\n===== CANCELACIÓN DE RESERVA =====");

        // Seleccionar sala
        int indiceSala = seleccionarSala();
        if (indiceSala == -1) return;

        Sala salaSeleccionada = teatro.getSala(indiceSala);

        // Mostrar sala
        System.out.println("\nEstado actual de la sala " + salaSeleccionada.getSalaId() + ":");
        mostrarTablaSala(salaSeleccionada);

        // Seleccionar asiento
        System.out.println("\nSeleccione el asiento reservado que desea cancelar:");
        int[] coordenadas = seleccionarAsiento(salaSeleccionada);
        if (coordenadas == null) return;

        // Cancelar reserva
        boolean exito = salaSeleccionada.cancelarReserva(coordenadas[0], coordenadas[1]);

        if (exito) {
            System.out.println("\n¡Reserva cancelada exitosamente del asiento en la fila " +
                    coordenadas[0] + ", columna " + coordenadas[1] + "!");

            // Mostrar nuevamente la sala para ver el cambio
            System.out.println("\nEstado actualizado de la sala:");
            mostrarTablaSala(salaSeleccionada);
        }
    }

    private void imprimirBoleta() {
        System.out.println("\n===== IMPRESIÓN DE BOLETA =====");

        // Aquí podríamos tener una lista de boletas guardadas
        // Pero al no tener una implementación de almacenamiento de boletas en el Teatro original
        // Simplemente creamos una boleta de ejemplo

        System.out.println("1. Boleta individual");
        System.out.println("2. Boleta grupal (ejemplo)");
        System.out.print("Seleccione el tipo de boleta: ");

        try {
            int tipoBoleta = sc.nextInt();
            sc.nextLine(); // Consumir salto de línea

            Boleta boleta;

            if (tipoBoleta == 1) {
                boleta = new Boleta(0, 1, 2, 2000, 1);
            } else {
                boleta = new Boleta(0, 1, 2, 1800, 4); // Precio con descuento
            }

            System.out.println("\n" + boleta.toString());

        } catch (Exception e) {
            System.out.println("Error: Entrada inválida. Se mostrará una boleta individual de ejemplo.");
            Boleta boleta = new Boleta(0, 1, 2, 2000, 1);
            System.out.println("\n" + boleta.toString());
        }
    }

    public void mostrarSalas() {
        if (teatro.salas.isEmpty()) {
            System.out.println("No hay salas disponibles en el teatro.");
            return;
        }

        System.out.println("\n===== SALAS DISPONIBLES =====");

        // Iterar sobre todas las salas y mostrar cada una
        for (int i = 0; i < teatro.salas.size(); i++) {
            Sala sala = teatro.salas.get(i);
            System.out.println("\n----- Sala: " + sala.getSalaId() + " -----");

            // Mostrar tabla de la sala
            mostrarTablaSala(sala);

            // Agregar un separador entre salas
            if (i < teatro.salas.size() - 1) {
                System.out.println("\n" + "=".repeat(30));
            }
        }

        // Mostrar la leyenda una sola vez al final
        System.out.println("\nLeyenda:");
        System.out.println("O: Disponible");
        System.out.println("R: Reservado");
        System.out.println("X: Vendido");
    }

    // Método auxiliar para mostrar la tabla de una sala sin la leyenda
    private void mostrarTablaSala(Sala sala) {
        // Obtener dimensiones
        int filas = sala.asientos.length;
        int columnas = sala.asientos[0].length;

        // Imprimir encabezado con números de columna
        System.out.print("|   |");
        for (int j = 1; j <= columnas; j++) {
            System.out.printf(" %d |", j);
        }
        System.out.println();

        // Imprimir fila de separación
        System.out.print("|---|");
        for (int j = 0; j < columnas; j++) {
            System.out.print("---|");
        }
        System.out.println();

        // Imprimir filas con sus asientos
        for (int i = 0; i < filas; i++) {
            System.out.printf("| %d |", (i + 1)); // Número de fila

            for (int j = 0; j < columnas; j++) {
                char simbolo;
                switch (sala.asientos[i][j].estado()) {
                    case DISPONIBLE:
                        simbolo = 'O'; // 'O' para asiento disponible
                        break;
                    case RESERVADO:
                        simbolo = 'R'; // 'R' para asiento reservado
                        break;
                    case VENDIDO:
                        simbolo = 'X'; // 'X' para asiento vendido
                        break;
                    default:
                        simbolo = '?';
                }
                System.out.printf(" %c |", simbolo);
            }
            System.out.println();
        }
    }

    // Método auxiliar para seleccionar una sala
    private int seleccionarSala() {
        if (teatro.salas.size() == 1) {
            return 0; // Si solo hay una sala, seleccionarla automáticamente
        }

        System.out.println("\nSalas disponibles:");
        for (int i = 0; i < teatro.salas.size(); i++) {
            System.out.println((i + 1) + ". Sala " + teatro.getSala(i).getSalaId());
        }

        System.out.print("\nSeleccione el número de la sala (0 para volver): ");
        try {
            int seleccion = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            if (seleccion == 0) {
                return -1; // Volver al menú principal
            }

            if (seleccion < 1 || seleccion > teatro.salas.size()) {
                System.out.println("Número de sala inválido.");
                return -1;
            }

            return seleccion - 1; // Restar 1 para obtener el índice real

        } catch (Exception e) {
            System.out.println("Error: Debe ingresar un número válido.");
            sc.nextLine(); // Limpiar el buffer del scanner
            return -1;
        }
    }

    // Método auxiliar para seleccionar un asiento
    private int[] seleccionarAsiento(Sala sala) {
        int filas = sala.asientos.length;
        int columnas = sala.asientos[0].length;

        System.out.print("Ingrese la fila (1-" + filas + ") o 0 para volver: ");
        try {
            int fila = sc.nextInt();

            if (fila == 0) {
                return null; // Volver al menú anterior
            }

            if (fila < 1 || fila > filas) {
                System.out.println("Fila inválida.");
                return null;
            }

            System.out.print("Ingrese la columna (1-" + columnas + ") o 0 para volver: ");
            int columna = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            if (columna == 0) {
                return null; // Volver al menú anterior
            }

            if (columna < 1 || columna > columnas) {
                System.out.println("Columna inválida.");
                return null;
            }

            return new int[]{fila, columna};

        } catch (Exception e) {
            System.out.println("Error: Debe ingresar un número válido.");
            sc.nextLine(); // Limpiar el buffer del scanner
            return null;
        }
    }
}