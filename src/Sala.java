import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Sala {
    private final String salaId;
    Asiento[][] asientos;
    private Map<String, Timer> reservasActivas = new HashMap<>();
    public static final int TIEMPO_RESERVA_MINUTOS = 5;

    // Variables de instancia para estadísticas de sala
    private int asientosDisponibles;
    private int asientosReservados;
    private int asientosVendidos;
    private double precioBase;

    public Sala(String id, int filas, int columnas) {
        this.salaId = id;
        this.asientos = new Asiento[filas][columnas];
        this.asientosDisponibles = filas * columnas;
        this.asientosReservados = 0;
        this.asientosVendidos = 0;
        this.precioBase = 2000.0; // Precio base predeterminado

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                asientos[i][j] = new Asiento();
            }
        }
    }

    public String getSalaId() {
        return salaId;
    }

    public int getAsientosDisponibles() {
        return asientosDisponibles;
    }

    public int getAsientosReservados() {
        return asientosReservados;
    }

    public int getAsientosVendidos() {
        return asientosVendidos;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public boolean reservar(int fila, int columna) {
        int filaTemp = fila - 1;
        int columnaTemp = columna - 1;

        // Validar índices dentro de límites
        if (filaTemp < 0 || filaTemp >= asientos.length) {
            System.out.println("Error: Fila inválida");
            return false;
        }

        if (columnaTemp < 0 || columnaTemp >= asientos[0].length) {
            System.out.println("Error: Columna inválida");
            return false;
        }

        Asiento asiento = asientos[filaTemp][columnaTemp];

        if (asiento.estado() == EstadoAsiento.DISPONIBLE) {
            asiento.setEstado(EstadoAsiento.RESERVADO);
            System.out.println("Reservado por " + TIEMPO_RESERVA_MINUTOS + " minutos");

            // Actualizar estadísticas
            asientosDisponibles--;
            asientosReservados++;

            // Crear un timer para cancelar la reserva automáticamente después de 5 minutos
            String clave = filaTemp + "," + columnaTemp;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (asiento.estado() == EstadoAsiento.RESERVADO) {
                        asiento.setEstado(EstadoAsiento.DISPONIBLE);
                        System.out.println("La reserva del asiento " + fila + "," + columna + " ha expirado");

                        // Actualizar estadísticas cuando expira la reserva
                        asientosDisponibles++;
                        asientosReservados--;

                        reservasActivas.remove(clave);
                    }
                }
            }, TIEMPO_RESERVA_MINUTOS * 60 * 1000); // Convertir minutos a milisegundos

            // Guardar el timer en el mapa
            reservasActivas.put(clave, timer);
            return true;
        }

        System.out.println("Error: El asiento no está disponible");
        return false;
    }

    public boolean comprar(int fila, int columna) {
        int filaTemp = fila - 1;
        int columnaTemp = columna - 1;

        // Validar índices dentro de límites
        if (filaTemp < 0 || filaTemp >= asientos.length) {
            System.out.println("Error: Fila inválida");
            return false;
        }

        if (columnaTemp < 0 || columnaTemp >= asientos[0].length) {
            System.out.println("Error: Columna inválida");
            return false;
        }

        Asiento asiento = asientos[filaTemp][columnaTemp];
        String clave = filaTemp + "," + columnaTemp;

        if (asiento.estado() == EstadoAsiento.DISPONIBLE) {
            asiento.setEstado(EstadoAsiento.VENDIDO);

            // Actualizar estadísticas
            asientosDisponibles--;
            asientosVendidos++;

            System.out.println("Asiento comprado exitosamente");
            return true;
        } else if (asiento.estado() == EstadoAsiento.RESERVADO) {
            // Si hay un timer activo para este asiento, cancelarlo
            if (reservasActivas.containsKey(clave)) {
                reservasActivas.get(clave).cancel();
                reservasActivas.remove(clave);
            }

            asiento.setEstado(EstadoAsiento.VENDIDO);

            // Actualizar estadísticas
            asientosReservados--;
            asientosVendidos++;

            System.out.println("Reserva convertida a compra exitosamente");
            return true;
        } else {
            System.out.println("Error: El asiento ya está vendido");
            return false;
        }
    }

    public boolean cancelarReserva(int fila, int columna) {
        int filaTemp = fila - 1;
        int columnaTemp = columna - 1;

        if (filaTemp < 0 || filaTemp >= asientos.length ||
                columnaTemp < 0 || columnaTemp >= asientos[0].length) {
            System.out.println("Error: Coordenadas inválidas");
            return false;
        }

        Asiento asiento = asientos[filaTemp][columnaTemp];
        String clave = filaTemp + "," + columnaTemp;

        if (asiento.estado() == EstadoAsiento.RESERVADO) {
            asiento.setEstado(EstadoAsiento.DISPONIBLE);

            // Actualizar estadísticas
            asientosReservados--;
            asientosDisponibles++;

            // Cancelar el timer si existe
            if (reservasActivas.containsKey(clave)) {
                reservasActivas.get(clave).cancel();
                reservasActivas.remove(clave);
            }

            System.out.println("Reserva cancelada exitosamente");
            return true;
        } else {
            System.out.println("Error: El asiento no está reservado");
            return false;
        }
    }

    // Método para obtener el porcentaje de ocupación de la sala
    public double getPorcentajeOcupacion() {
        int totalAsientos = asientos.length * asientos[0].length;
        int asientosOcupados = asientosReservados + asientosVendidos;

        return (double) asientosOcupados / totalAsientos * 100.0;
    }
}