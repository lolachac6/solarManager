package mapper;

import modelo.Direccion;
import org.bson.Document;
import org.junit.Test;
import static org.junit.Assert.*;

public class DireccionMapperTest {

    @Test
    public void testFromDocument() {
        Document doc = new Document("calle", "Calle Mayor")
                .append("numero", "10")
                .append("codigoPostal", "38001")
                .append("municipio", "Santa Cruz")
                .append("provincia", "Tenerife");

        Direccion direccion = DireccionMapper.fromDocument(doc);

        assertEquals("Calle Mayor", direccion.getCalle());
        assertEquals("10", direccion.getNumero());
        assertEquals("38001", direccion.getCodigoPostal());
        assertEquals("Santa Cruz", direccion.getMunicipio());
        assertEquals("Tenerife", direccion.getProvincia());
    }

    @Test
    public void testToDocument() {
        Direccion direccion = new Direccion("Calle Mayor", "10", "38001", "Santa Cruz", "Tenerife");

        Document doc = DireccionMapper.toDocument(direccion);

        assertEquals("Calle Mayor", doc.getString("calle"));
        assertEquals("10", doc.getString("numero"));
        assertEquals("38001", doc.getString("codigoPostal"));
        assertEquals("Santa Cruz", doc.getString("municipio"));
        assertEquals("Tenerife", doc.getString("provincia"));
    }

    @Test
    public void testFromDocumentNull() {
        assertNull(DireccionMapper.fromDocument(null));
    }
}
