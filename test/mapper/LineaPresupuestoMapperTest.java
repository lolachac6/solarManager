package mapper;

import modelo.LineaPresupuesto;
import org.bson.Document;
import org.junit.Test;
import static org.junit.Assert.*;

public class LineaPresupuestoMapperTest {

    @Test
    public void testFromDocument() {
        Document doc = new Document("idProducto", "P001")
                .append("nombreProducto", "Panel solar")
                .append("cantidad", 4)
                .append("precioUnitario", 150.0)
                .append("totalLinea", 600.0);

        LineaPresupuesto linea = LineaPresupuestoMapper.fromDocument(doc);

        assertEquals("P001", linea.getIdProducto());
        assertEquals("Panel solar", linea.getNombreProducto());
        assertEquals(4, linea.getCantidad());
        assertEquals(150.0, linea.getPrecioUnitario(), 0.001);
        assertEquals(600.0, linea.getTotalLinea(), 0.001);
    }

    @Test
    public void testToDocument() {
        LineaPresupuesto linea = new LineaPresupuesto("P001", "Panel solar", 4, 150.0, 600.0);

        Document doc = LineaPresupuestoMapper.toDocument(linea);

        assertEquals("P001", doc.getString("idProducto"));
        assertEquals("Panel solar", doc.getString("nombreProducto"));
        assertEquals(4, doc.getInteger("cantidad").intValue());
        assertEquals(150.0, doc.getDouble("precioUnitario"), 0.001);
        assertEquals(600.0, doc.getDouble("totalLinea"), 0.001);
    }
}
