package dao;

import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import mapper.ClienteMapper;
import modelo.Cliente;
import org.bson.Document;

public class ClienteDAO extends MongoDAO {

    private static final String COLLECTION = "Clientes";

    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        for (Document doc : findAll(COLLECTION)) {
            clientes.add(ClienteMapper.fromDocument(doc));
        }
        return clientes;
    }

    public Cliente obtenerPorId(String id) {
        return ClienteMapper.fromDocument(findById(COLLECTION, id));
    }

    public Cliente obtenerPorEmail(String email) {
        return ClienteMapper.fromDocument(findFirst(COLLECTION, Filters.eq("email", email)));
    }

    public void guardar(Cliente cliente) {
        insert(COLLECTION, ClienteMapper.toDocument(cliente));
    }

    public void actualizar(Cliente cliente) {
        updateById(COLLECTION, cliente.getId(), ClienteMapper.toDocument(cliente));
    }

    public void eliminar(String id) {
        deleteById(COLLECTION, id);
    }
}
