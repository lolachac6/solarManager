package mapper;

import modelo.InstalacionFotovoltaica;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import static org.junit.Assert.*;

public class InstalacionMapperTest {

    @Test
    public void testFromDocument() {
        ObjectId id = new ObjectId();
        Document direccion = new Document("calle", "Calle Mayor")
                .append("numero", "10")
                .append("codigoPostal", "38001")
                .append("municipio", "Santa Cruz")
                .append("provincia", "Tenerife");

        Document doc = new Document("_id", id)
                .append("idCliente", "CLI001")
                .append("nombreCliente", "Cliente Demo")
                .append("potenciaInstalada", 5.5)
                .append("numeroPaneles", 12)
                .append("produccionEstimada", 7200.0)
                .append("ahorroEstimado", 950.0)
                .append("inversor", "Inversor 5kW")
                .append("bateria", true)
                .append("direccion", direccion);

        InstalacionFotovoltaica instalacion = InstalacionMapper.fromDocument(doc);

        assertEquals(id.toString(), instalacion.getId());
        assertEquals("CLI001", instalacion.getIdCliente());
        assertEquals("Cliente Demo", instalacion.getNombreCliente());
        assertEquals(5.5, instalacion.getPotenciaInstalada(), 0.001);
        assertEquals(12, instalacion.getNumeroPaneles());
        assertEquals(7200.0, instalacion.getProduccionEstimada(), 0.001);
        assertEquals(950.0, instalacion.getAhorroEstimado(), 0.001);
        assertEquals("Inversor 5kW", instalacion.getInversor());
        assertTrue(instalacion.getBateria());
        assertEquals("Calle Mayor", instalacion.getDireccion().getCalle());
    }
}
