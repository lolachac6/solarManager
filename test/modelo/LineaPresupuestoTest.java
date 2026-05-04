package modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class LineaPresupuestoTest {

    @Test
    public void testConstructorCompleto() {
        LineaPresupuesto linea = new LineaPresupuesto("P001", "Panel solar", 4, 150.00, 600.00);

        assertEquals("P001", linea.getIdProducto());
        assertEquals("Panel solar", linea.getNombreProducto());
        assertEquals(4, linea.getCantidad());
        assertEquals(150.00, linea.getPrecioUnitario(), 0.001);
        assertEquals(600.00, linea.getTotalLinea(), 0.001);
    }
}
