package descuentos;

import java.util.ArrayList;
import java.util.List;

public class SistemaDescuento {
    // Inicializamos una lista dinámica (ArrayList) para que el sistema
    // pueda almacenar un número indefinido de códigos de descuentos
    private final List<Descuento> descuentos = new ArrayList<Descuento>();

    public SistemaDescuento() {}

    /**
     * Agrega un descuento al sistema
     * @param descuento descuento para agregar
     */
    public void agregarDescuento(Descuento descuento) {
        // Obtiene el código de descuento para verificar si el código ya existe
        // dentro del sistema
        String codigoDescuento = descuento.obtenerCodigoDescuento();

        boolean descuentoExistente = false;
        for (Descuento descuentoAux : this.descuentos) {
            if (descuentoAux.obtenerCodigoDescuento().equals(codigoDescuento)) {
                descuentoExistente = true;
                break;
            }
        }

        // Si el código de descuento no existe dentro del sistema lo agrega
        // a la lista
        if (!descuentoExistente) {
            this.descuentos.add(descuento);
        }
    }

    /**
     * Obtiene la tasa de descuento de un descuento existente dentro del sistema
     * de descuento
     * @param codigoDescuento código del descuento a buscar
     * @return tasa de descuento en caso de ser encontrado
     */
    public float obtenerTasaDescuento(String codigoDescuento) {
        // Se inicializa la variable como 0 para que si no encuentra el código
        // la tasa de descuento devuelta sea 0 y no tenga efecto en el cálculo
        // del descuento aplicado
        float tasaDescuento = 0;

        for (Descuento descuento : descuentos) {
            if (descuento.obtenerCodigoDescuento().equals(codigoDescuento)) {
                tasaDescuento = descuento.obtenerTasaDescuento();
            }
        }

        return tasaDescuento;
    }
}
