import java.util.ArrayList;
import java.util.Optional;

/**
 * Sistema para la gestión de clientes del teatro
 * Utiliza ArrayList para los clientes ya que su cantidad puede crecer indefinidamente
 */
public class SistemaClientes {
    // ArrayList para almacenar los clientes
    private ArrayList<Cliente> clientes;
    private int ultimoId;

    public SistemaClientes() {
        this.clientes = new ArrayList<>();
        this.ultimoId = 0;
    }

    // Método para registrar un nuevo cliente
    public Cliente registrarCliente(String nombre, int edad, boolean esEstudiante) {
        Cliente cliente = new Cliente(++ultimoId, nombre, edad, esEstudiante);
        clientes.add(cliente);
        return cliente;
    }

    // Método para obtener un cliente por ID
    public Optional<Cliente> obtenerCliente(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return Optional.of(cliente);
            }
        }

        return Optional.empty();
    }

    // Método para actualizar un cliente
    public boolean actualizarCliente(Cliente clienteActualizado) {
        Optional<Cliente> clienteOpt = obtenerCliente(clienteActualizado.getId());

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            int index = clientes.indexOf(cliente);
            clientes.set(index, clienteActualizado);
            return true;
        }

        return false;
    }

    // Método para eliminar un cliente
    public boolean eliminarCliente(int id) {
        Optional<Cliente> clienteOpt = obtenerCliente(id);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            clientes.remove(cliente);
            return true;
        }

        return false;
    }

    // Método para obtener todos los clientes
    public ArrayList<Cliente> obtenerTodosLosClientes() {
        return new ArrayList<>(clientes);
    }
}