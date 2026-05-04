package mapper;

import modelo.Cliente;
import modelo.Cliente.TipoCliente;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClienteMapperTest {

    @Test
    public void testFromDocumentConDatosCifrados() {
        ObjectId id = new ObjectId();
        Document direccion = new Document("calle", "Calle Mayor")
                .append("numero", "10")
                .append("codigoPostal", "38001")
                .append("municipio", "Santa Cruz")
                .append("provincia", "Tenerife");

        Cliente clienteOriginal = new Cliente(id.toString(), "Iván", "Galán", "666777888", "ivan@email.com",
                DireccionMapper.fromDocument(direccion), TipoCliente.PARTICULAR, "12345678Z", null,
                "ES0000000000000000000000", "Observaciones", "COM001");

        Document doc = ClienteMapper.toDocument(clienteOriginal);
        doc.append("_id", id);

        Cliente cliente = ClienteMapper.fromDocument(doc);

        assertEquals(id.toString(), cliente.getId());
        assertEquals("Iván", cliente.getNombre());
        assertEquals("Galán", cliente.getApellidos());
        assertEquals("666777888", cliente.getTelefono());
        assertEquals("ivan@email.com", cliente.getEmail());
        assertEquals(TipoCliente.PARTICULAR, cliente.getTipoCliente());
        assertEquals("12345678Z", cliente.getDni());
        assertEquals("ES0000000000000000000000", cliente.getNumeroCuenta());
        assertEquals("COM001", cliente.getIdComercialAsignado());
    }
}
