package ventas;

import client.Client;
import descuentos.SistemaDescuento;
import enums.GeneroCliente;
import teatro.Asiento;

import java.util.HashMap;
import java.util.Map;

public class Cotizacion {
    private final SistemaDescuento sistemaDescuento;
    private final Client cliente;
    private final Asiento asiento;

    private final int precioVenta;
    private final int precioNormal;
    private final double descuento;
    private final float tasaDescuento;

    /**
     * Constructor que recrea una cotización previa a la venta
     * @param cliente cliente solicitante de la cotizacion
     * @param asiento asiento seleccionado por el cliente
     * @param sistemaDescuento indica el sistema de descuento que se va a utilizar en la cotización
     */
    public Cotizacion(Client cliente, Asiento asiento, SistemaDescuento sistemaDescuento) {
        this.cliente = cliente;
        this.asiento = asiento;
        this.sistemaDescuento = sistemaDescuento;

        // Inicializar valores
        this.precioNormal = asiento.obtenerTipo().obtenerPrecio();
        this.tasaDescuento = calcularMejorTasaDescuento();
        this.descuento = this.precioNormal * this.tasaDescuento;
        this.precioVenta = this.precioNormal - (int)this.descuento;
    }

    /**
     * Obtiene el precio de venta (precio a pagar) por la entrada
     * @return precio de venta
     */
    public int obtenerPrecioVenta() {
        return precioVenta;
    }

    /**
     * Obtiene el precio original (previo descuentos) de la entrada
     * @return precio normal de la entrada
     */
    public int obtenerPrecioOriginal() {
        return precioNormal;
    }

    /**
     * Obtiene el descuento de la cotización
     * @return descuento
     */
    public double obtenerDescuento() {
        return descuento;
    }

    /**
     * Obtiene el cliente de la cotización
     * @return cliente de la cotización
     */
    public Client obtenerCliente() {
        return cliente;
    }

    /**
     * Obtiene el asiento reservado de la cotización
     * @return asiento seleccionado por el usuario
     */
    public Asiento obtenerAsiento() {
        return asiento;
    }

    /**
     * Obtiene la tasa de descuento de la cotización
     * @return tasa de descuento
     */
    public float obtenerDescuentoAplicado() {
        return tasaDescuento;
    }

    /**
     * Calcula la mejor tasa de descuento aplicable al cliente
     * @return La tasa de descuento más alta a la que el cliente tiene derecho
     */
    private float calcularMejorTasaDescuento() {
        // Usamos un Map para almacenar todos los descuentos aplicables
        Map<String, Float> descuentosAplicables = new HashMap<>();
        int edadCliente = this.cliente.obtenerEdad();

        // Cliente menor de edad (niño)
        if (edadCliente < 14) {
            String codigoDescuento = "DESCUENTO_NIÑO";
            float tasa = sistemaDescuento.obtenerTasaDescuento(codigoDescuento);
            descuentosAplicables.put(codigoDescuento, tasa);
        }

        // Cliente de tercera edad
        if (edadCliente > 55) {
            String codigoDescuento = "DESCUENTO_TERCERA_EDAD";
            float tasa = sistemaDescuento.obtenerTasaDescuento(codigoDescuento);
            descuentosAplicables.put(codigoDescuento, tasa);
        }

        // Descuento por género (femenino)
        if (cliente.obtenerGenero() == GeneroCliente.FEMININO) {
            String codigoDescuento = "DESCUENTO_FEMININO";
            float tasa = sistemaDescuento.obtenerTasaDescuento(codigoDescuento);
            descuentosAplicables.put(codigoDescuento, tasa);
        }

        // Descuento de estudiante
        if (cliente.esEstudiante()) {
            String codigoDescuento = "DESCUENTO_ESTUDIANTE";
            float tasa = sistemaDescuento.obtenerTasaDescuento(codigoDescuento);
            descuentosAplicables.put(codigoDescuento, tasa);
        }

        // Si no hay descuentos aplicables, retornar 0
        if (descuentosAplicables.isEmpty()) {
            return 0.0f;
        }

        // Encontrar el descuento con la mayor tasa
        String mejorDescuento = null;
        float mejorTasa = 0.0f;

        for (Map.Entry<String, Float> entrada : descuentosAplicables.entrySet()) {
            if (entrada.getValue() > mejorTasa) {
                mejorTasa = entrada.getValue();
                mejorDescuento = entrada.getKey();
            }
        }

        System.out.println("Se aplicó el descuento: " + mejorDescuento + " con una tasa de " + mejorTasa);

        return mejorTasa;
    }

    /**
     * Genera un resumen de la cotización
     * @return Un String con el resumen detallado
     */
    public String generarResumen() {
        return "Resumen de Cotización\n" +
                "====================\n" +
                "Cliente: " + cliente.obtenerNombre() + "\n" +
                "Asiento: " + asiento.obtenerCodigo() + "\n" +
                "Precio normal: $" + String.format("%,d", precioNormal) + "\n" +
                "Tasa de descuento: " + String.format("%.2f", tasaDescuento * 100) + "%\n" +
                "Monto de descuento: $" + String.format("%,.0f", descuento) + "\n" +
                "Precio final: $" + String.format("%,d", precioVenta) + "\n";
    }
}