package utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class CifradoDatosTest {

    @Test
    public void testCifrarYDescifrar() {
        String textoOriginal = "12345678A";

        String textoCifrado = CifradoDatos.cifrar(textoOriginal);
        String textoDescifrado = CifradoDatos.descifrar(textoCifrado);

        assertNotNull(textoCifrado);
        assertNotEquals(textoOriginal, textoCifrado);
        assertEquals(textoOriginal, textoDescifrado);
    }

    @Test
    public void testCifrarValoresVacios() {
        assertNull(CifradoDatos.cifrar(null));
        assertEquals("", CifradoDatos.cifrar(""));
    }

    @Test
    public void testDescifrarValoresVacios() {
        assertNull(CifradoDatos.descifrar(null));
        assertEquals("", CifradoDatos.descifrar(""));
    }

    @Test
    public void testDescifrarSiEsPosibleConTextoNoCifrado() {
        assertEquals("Texto plano", CifradoDatos.descifrarSiEsPosible("Texto plano"));
    }

    @Test
    public void testCifradosConMismoTextoSonDistintos() {
        String primerCifrado = CifradoDatos.cifrar("dato sensible");
        String segundoCifrado = CifradoDatos.cifrar("dato sensible");

        assertNotEquals(primerCifrado, segundoCifrado);
        assertEquals("dato sensible", CifradoDatos.descifrar(primerCifrado));
        assertEquals("dato sensible", CifradoDatos.descifrar(segundoCifrado));
    }
}
