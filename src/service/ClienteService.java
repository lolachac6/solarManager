package service;

import dao.ClienteDAO;
import java.util.List;
import modelo.Cliente;

public class ClienteService {

    private final ClienteDAO clienteDAO;
    private final ValidacionService validacionService;

    public ClienteService() {
        this(new ClienteDAO(), new ValidacionService());
    }

    public ClienteService(ClienteDAO clienteDAO, ValidacionService validacionService) {
        this.clienteDAO = clienteDAO;
        this.validacionService = validacionService;
    }

    public List<Cliente> obtenerClientes() {
        return clienteDAO.obtenerTodos();
    }

    public Cliente obtenerPorId(String id) {
        return clienteDAO.obtenerPorId(id);
    }

    public void guardar(Cliente cliente) {
        validar(cliente);
        if (cliente.getId() == null || cliente.getId().trim().isEmpty()) {
            clienteDAO.guardar(cliente);
        } else {
            clienteDAO.actualizar(cliente);
        }
    }

    public void eliminar(String id) {
        clienteDAO.eliminar(id);
    }

    public void validar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        if (validacionService.estaVacio(cliente.getNombre())) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        }
        if (!validacionService.esEmailValido(cliente.getEmail())) {
            throw new IllegalArgumentException("El email del cliente no es válido");
        }
    }
}
