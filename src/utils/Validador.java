package utils;

public class Validador {
    public boolean validarInputMenu(int min, int max, int opcion) {
        if (opcion < min || opcion > max) {
            return false;
        }

        return true;
    }
}
