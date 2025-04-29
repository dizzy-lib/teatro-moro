package Analitica;

import sistemaVentas.SistemaVentas;
import sistemaVentas.TipoAsiento;
import sistemaVentas.Venta;

import java.util.ArrayList;

public class Analitica {
    SistemaVentas sistemaVentas;

    public Analitica(SistemaVentas sistemaVentas) {
        this.sistemaVentas = sistemaVentas;
    }

    public ArrayList<Venta> obtenerVentas() {
        return sistemaVentas.ventas;
    }

    private ArrayList<Venta> filtrarPorTipo(TipoAsiento tipo) {
        ArrayList<Venta> ventas = this.sistemaVentas.ventas;
        ArrayList<Venta> filtradas = new ArrayList<>();

        for (Venta v : ventas) {
            if (v.ubicacion.equals(tipo)) {
                filtradas.add(v);
            }
        }

        return filtradas;
    }

    public ArrayList<Venta> obtenerVentasPorTipo(TipoAsiento tipo) {
        return filtrarPorTipo(tipo);
    }

    public float obtenerTotalIngresos() {
        float totalIngreso = 0;

        for (Venta v: this.sistemaVentas.ventas) {
            totalIngreso = totalIngreso + v.costoFinal;
        }

        return totalIngreso;
    }
}
