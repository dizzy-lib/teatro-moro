public class Main {
    public static void main(String[] args) {
        // Crear el teatro
        Teatro teatro = new Teatro("Teatro Moro");

        // Crear diferentes salas
        Sala salaVip = new Sala("VIP", 5, 5);
        Sala salaGeneral = new Sala("General", 8, 10);
        Sala salaPreferencial = new Sala("Preferencial", 6, 8);

        // Establecer precios base diferentes para cada sala
        salaVip.setPrecioBase(5000.0);
        salaGeneral.setPrecioBase(2000.0);
        salaPreferencial.setPrecioBase(3500.0);

        // Agregar salas al teatro
        teatro.addSala(salaVip);
        teatro.addSala(salaGeneral);
        teatro.addSala(salaPreferencial);

        // Iniciar la interfaz de usuario
        UI ui = new UI(teatro);
        ui.mostrarMenu();
    }
}