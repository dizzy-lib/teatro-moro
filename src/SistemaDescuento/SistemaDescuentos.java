package SistemaDescuento;

import java.util.ArrayList;

public class SistemaDescuentos {
    private ArrayList<Descuento> descuentosActivos;

    public SistemaDescuentos(ArrayList<Descuento> descuentosActivos) {
        this.descuentosActivos = descuentosActivos;
    }

    public float obtenerDescuento(String codigoStr) {
        if (codigoStr == null || codigoStr.isEmpty()) {
            return 0;
        }

        // Intentar convertir el string a enum
        Descuentos codigoEnum;
        try {
            codigoEnum = Descuentos.valueOf(codigoStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Código de descuento no reconocido: " + codigoStr);
            return 0;
        }

        // Ahora buscar el descuento con el código enum
        for (Descuento descuento : descuentosActivos) {
            if (descuento.codigo == codigoEnum) {
                return descuento.valor;
            }
        }

        return 0;
    }

    public float aplicarDescuento(float precioBase, float descuentoAplicado) {
        return precioBase - (precioBase * descuentoAplicado);
    }
}