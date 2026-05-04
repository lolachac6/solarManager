package service;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidacionServiceTest {

    private ValidacionService service;

    @Before
    public void setUp() {
        service = new ValidacionService();
    }

    @Test
    public void testEmailValido() {
        assertTrue(service.esEmailValido("usuario@correo.com"));
        assertTrue(service.esEmailValido("usuario.nombre@subdominio.com"));
    }

    @Test
    public void testEmailInvalido() {
        assertFalse(service.esEmailValido(null));
        assertFalse(service.esEmailValido(""));
        assertFalse(service.esEmailValido("usuario-correo.com"));
        assertFalse(service.esEmailValido("usuario@"));
    }

    @Test
    public void testTelefonoValido() {
        assertTrue(service.esTelefonoValido("666777888"));
    }

    @Test
    public void testTelefonoInvalido() {
        assertFalse(service.esTelefonoValido(null));
        assertFalse(service.esTelefonoValido("123"));
        assertFalse(service.esTelefonoValido("66677788A"));
        assertFalse(service.esTelefonoValido("666 777 888"));
    }

    @Test
    public void testDniValido() {
        assertTrue(service.esDniValido("12345678Z"));
        assertTrue(service.esDniValido("12345678z"));
    }

    @Test
    public void testDniInvalido() {
        assertFalse(service.esDniValido(null));
        assertFalse(service.esDniValido("12345678A"));
        assertFalse(service.esDniValido("1234567Z"));
        assertFalse(service.esDniValido("ABCDEFGHZ"));
    }

    @Test
    public void testTextoVacio() {
        assertTrue(service.estaVacio(null));
        assertTrue(service.estaVacio(""));
        assertTrue(service.estaVacio("   "));
        assertFalse(service.estaVacio("dato"));
    }

    @Test
    public void testNormalizarTexto() {
        assertEquals("", service.normalizarTexto(null));
        assertEquals("texto", service.normalizarTexto("  texto  "));
    }
}
