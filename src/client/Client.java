package client;

import enums.GeneroCliente;

public class Client {
    private final String nombre;
    private final int edad;
    private final GeneroCliente genero;
    private final boolean esEstudiante;

    /**
     * Constructor para crear una instancia de cliente que almacena la
     * infromación básica del mismo
     * @param nombre nombre del cliente
     * @param edad edad del cliente
     * @param genero genero del cliente
     * @param esEstudiante indica si es estudiante o no
     */
    public Client(String nombre, int edad, GeneroCliente genero, Boolean esEstudiante) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.esEstudiante = esEstudiante;
    }

    /**
     * Obtiene el nombre del cliente
     * @return nombre del cliente
     */
    public String obtenerNombre() {
        return this.nombre;
    }

    /**
     * Obtiene la edad del cliente
     * @return edad del cliente
     */
    public int obtenerEdad() {
        return this.edad;
    }

    /**
     * Obtiene el género del cliente
     * @return si es masculino o femenino
     */
    public GeneroCliente obtenerGenero() {
        return this.genero;
    }

    /**
     * Método que indica si es estudiante o no
     * @return flag indicativo si el cliente es o no estudiante
     */
    public boolean esEstudiante() {
        return this.esEstudiante;
    }
}
