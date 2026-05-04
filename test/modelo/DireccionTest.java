package modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class DireccionTest {

    @Test
    public void testConstructorCompleto() {
        Direccion direccion = new Direccion("Calle Mayor", "10", "38001", "Santa Cruz", "Tenerife");

        assertEquals("Calle Mayor", direccion.getCalle());
        assertEquals("10", direccion.getNumero());
        assertEquals("38001", direccion.getCodigoPostal());
        assertEquals("Santa Cruz", direccion.getMunicipio());
        assertEquals("Tenerife", direccion.getProvincia());
    }

    @Test
    public void testToString() {
        Direccion direccion = new Direccion("Calle Mayor", "10", "38001", "Santa Cruz", "Tenerife");

        assertEquals("Calle Mayor 10, 38001 Santa Cruz, Tenerife", direccion.toString());
    }
}
