package mapper;

import modelo.Direccion;
import org.bson.Document;

public final class DireccionMapper {

    private DireccionMapper() {
    }

    public static Direccion fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        return new Direccion(
                doc.getString("calle"),
                doc.getString("numero"),
                doc.getString("codigoPostal"),
                doc.getString("municipio"),
                doc.getString("provincia")
        );
    }

    public static Document toDocument(Direccion direccion) {
        if (direccion == null) {
            return new Document();
        }
        return new Document()
                .append("calle", direccion.getCalle())
                .append("numero", direccion.getNumero())
                .append("codigoPostal", direccion.getCodigoPostal())
                .append("municipio", direccion.getMunicipio())
                .append("provincia", direccion.getProvincia());
    }
}
