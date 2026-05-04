package mapper;

import modelo.Cliente;
import modelo.Cliente.TipoCliente;
import org.bson.Document;
import utils.CifradoDatos;

public final class ClienteMapper {

    private ClienteMapper() {
    }

    public static Cliente fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        Object id = doc.get("_id");
        cliente.setId(id != null ? id.toString() : null);
        cliente.setNombre(doc.getString("nombre"));
        cliente.setApellidos(doc.getString("apellidos"));
        cliente.setTelefono(CifradoDatos.descifrarSiEsPosible(valueAsString(doc.get("telefono"))));
        cliente.setEmail(doc.getString("email"));
        cliente.setDireccion(DireccionMapper.fromDocument((Document) doc.get("direccion")));
        cliente.setTipoCliente(parseTipo(doc.getString("tipoCliente")));
        cliente.setDni(CifradoDatos.descifrarSiEsPosible(valueAsString(doc.get("dni"))));
        cliente.setCif(CifradoDatos.descifrarSiEsPosible(valueAsString(doc.get("cif"))));
        cliente.setNumeroCuenta(CifradoDatos.descifrarSiEsPosible(valueAsString(doc.get("numeroCuenta"))));
        cliente.setObservaciones(doc.getString("observaciones"));
        cliente.setIdComercialAsignado(valueAsString(doc.get("idComercialAsignado")));
        return cliente;
    }

    public static Document toDocument(Cliente cliente) {
        return new Document()
                .append("nombre", cliente.getNombre())
                .append("apellidos", cliente.getApellidos())
                .append("telefono", CifradoDatos.cifrar(cliente.getTelefono()))
                .append("email", cliente.getEmail())
                .append("direccion", DireccionMapper.toDocument(cliente.getDireccion()))
                .append("tipoCliente", cliente.getTipoCliente() != null ? cliente.getTipoCliente().name() : null)
                .append("dni", CifradoDatos.cifrar(cliente.getDni()))
                .append("cif", CifradoDatos.cifrar(cliente.getCif()))
                .append("numeroCuenta", CifradoDatos.cifrar(cliente.getNumeroCuenta()))
                .append("observaciones", cliente.getObservaciones())
                .append("idComercialAsignado", cliente.getIdComercialAsignado());
    }

    private static TipoCliente parseTipo(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return TipoCliente.valueOf(value);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private static String valueAsString(Object value) {
        return value != null ? value.toString() : null;
    }
}
