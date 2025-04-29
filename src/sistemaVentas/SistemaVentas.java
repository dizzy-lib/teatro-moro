package sistemaVentas;

import SistemaDescuento.SistemaDescuentos;

import java.util.ArrayList;
import java.util.Optional;

public class SistemaVentas {
    public ArrayList<Venta> ventas = new ArrayList<Venta>();

    private SistemaDescuentos sistemaDescuentos;

    public SistemaVentas(SistemaDescuentos sistemaDescuentos) {
        this.sistemaDescuentos = sistemaDescuentos;
    }

    private float obtenerDescuentoAplicado(Entrada entrada) {
        String codigo = entrada.getCodigoDescuento();

        if (codigo == null || codigo.isEmpty()) {
            return 0;
        }

        return this.sistemaDescuentos.obtenerDescuento(codigo);
    }

    private float obtenerPrecioBase(Entrada entrada) {
        TipoAsiento tipoAsiento = entrada.getAsiento().getTipo();

        return switch (tipoAsiento) {
            case VIP -> 1000;
            case BALCON -> 500;
            case PLATEA -> 1500;
        };
    }

    private float obtenerCostoTotal(Entrada entrada) {
        float precioBase = this.obtenerPrecioBase(entrada);
        float descuentoAplicado = this.obtenerDescuentoAplicado(entrada);

        return this.sistemaDescuentos.aplicarDescuento(
                precioBase,
                descuentoAplicado
        );
    }

    public void generarVenta(Asiento asiento, Optional<String> codigoDescuento) {
        Entrada entrada = new Entrada(asiento);

        // Si se proporciona un código de descuento se agrega a la entrada presente
        if (codigoDescuento.isPresent()) {
            entrada.setCodigoDescuento(codigoDescuento.get());
        } else {
            entrada.setCodigoDescuento("");
        }

        float precioBase = this.obtenerPrecioBase(entrada);
        float descuentoAplicado = this.obtenerDescuentoAplicado(entrada);
        float costoTotal = this.obtenerCostoTotal(entrada);

        // Crear la venta con los valores correctos
        Venta venta = new Venta(asiento, descuentoAplicado, precioBase, costoTotal);

        this.ventas.add(venta);
    }
}