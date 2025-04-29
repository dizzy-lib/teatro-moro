import Presetation.Menu;
import SistemaDescuento.Descuento;
import SistemaDescuento.Descuentos;
import SistemaDescuento.SistemaDescuentos;
import sistemaVentas.SistemaVentas;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Crear sistema de descuentos
        ArrayList<Descuento> listaDescuentos = new ArrayList<>();
        listaDescuentos.add(new Descuento(Descuentos.ESTUDIANTES, 0.10F));
        listaDescuentos.add(new Descuento(Descuentos.TERCERA_EDAD, 0.15F));

        // Inicializar sistemas
        SistemaDescuentos sistemaDescuentos = new SistemaDescuentos(listaDescuentos);
        SistemaVentas sistemaVentas = new SistemaVentas(sistemaDescuentos);

        // Crear e iniciar el menú - ahora usamos un solo método que maneja todo
        Menu menu = new Menu(sistemaVentas);
        menu.iniciar();  // Este método único maneja toda la lógica del menú

        System.out.println("El programa ha finalizado. ¡Hasta pronto!");
    }
}