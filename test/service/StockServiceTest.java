package service;

import modelo.Producto;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StockServiceTest {

    private StockService service;

    @Before
    public void setUp() {
        service = new StockService();
    }

    @Test
    public void testHayStockSuficiente() {
        Producto producto = new Producto();
        producto.setStock(10);

        assertTrue(service.hayStockSuficiente(producto, 5));
        assertTrue(service.hayStockSuficiente(producto, 10));
        assertFalse(service.hayStockSuficiente(producto, 12));
    }

    @Test
    public void testHayStockSuficienteConDatosInvalidos() {
        Producto producto = new Producto();
        producto.setStock(10);

        assertFalse(service.hayStockSuficiente(null, 1));
        assertFalse(service.hayStockSuficiente(producto, -1));
    }

    @Test
    public void testCalcularStockRestante() {
        Producto producto = new Producto();
        producto.setStock(10);

        assertEquals(7, service.calcularStockRestante(producto, 3));
        assertEquals(0, service.calcularStockRestante(null, 3));
    }

    @Test
    public void testDescontarStock() {
        Producto producto = new Producto();
        producto.setStock(10);

        service.descontarStock(producto, 3);

        assertEquals(7, producto.getStock());
    }
}
