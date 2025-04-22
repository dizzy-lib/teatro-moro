import java.util.ArrayList;

public class Teatro {
    String nombre;
    ArrayList<Sala> salas = new ArrayList<>();
    private ArrayList<Boleta> boletas = new ArrayList<>();

    // Variables estáticas para estadísticas globales
    private static int totalEntradasVendidas = 0;
    private static double totalIngresos = 0;
    private static int totalReservasRealizadas = 0;

    public Teatro(String nombre) {
        this.nombre = nombre;
    }

    public void addSala(Sala sala) {
        salas.add(sala);
    }

    public Sala getSala(int index) {
        return salas.get(index);
    }

    public void agregarBoleta(Boleta boleta) {
        boletas.add(boleta);

        // Actualizar estadísticas
        totalEntradasVendidas += boleta.getCantidad();
        totalIngresos += boleta.getTotal();
    }

    public ArrayList<Boleta> getBoletas() {
        return boletas;
    }

    public void incrementarReservas() {
        totalReservasRealizadas++;
    }

    // Getters para estadísticas globales
    public static int getTotalEntradasVendidas() {
        return totalEntradasVendidas;
    }

    public static double getTotalIngresos() {
        return totalIngresos;
    }

    public static int getTotalReservasRealizadas() {
        return totalReservasRealizadas;
    }
}