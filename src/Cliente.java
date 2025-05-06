/**
 * Representa un cliente del teatro
 */
public class Cliente {
    private int id;
    private String nombre;
    private int edad;
    private boolean esEstudiante;

    public Cliente(int id, String nombre, int edad, boolean esEstudiante) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.esEstudiante = esEstudiante;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public boolean esEstudiante() { return esEstudiante; }

    // Método para determinar automáticamente el tipo de descuento aplicable
    public TipoDescuento obtenerTipoDescuento() {
        if (this.edad < 18 || this.esEstudiante) {
            return TipoDescuento.ESTUDIANTE;
        } else if (this.edad >= 60) {
            return TipoDescuento.TERCERA_EDAD;
        }
        return TipoDescuento.NINGUNO;
    }
}