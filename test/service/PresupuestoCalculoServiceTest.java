package service;

import java.util.ArrayList;
import java.util.List;
import modelo.Direccion;
import modelo.LineaPresupuesto;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PresupuestoCalculoServiceTest {

    private PresupuestoCalculoService service;

    @Before
    public void setUp() {
        service = new PresupuestoCalculoService();
    }

    @Test
    public void testCalcularCantidadInversoresRedondeaHaciaArriba() {
        assertEquals(1, service.calcularCantidadInversores(5.0));
        assertEquals(2, service.calcularCantidadInversores(5.1));
        assertEquals(0, service.calcularCantidadInversores(0));
    }

    @Test
    public void testCalcularBloquesCadaSeisPaneles() {
        assertEquals(1, service.calcularBloquesCadaSeisPaneles(6));
        assertEquals(2, service.calcularBloquesCadaSeisPaneles(7));
        assertEquals(0, service.calcularBloquesCadaSeisPaneles(0));
    }

    @Test
    public void testCrearLineaPresupuestoCalculaTotalLinea() {
        LineaPresupuesto linea = service.crearLineaPresupuesto("P001", "Panel solar", 4, 150.00);

        assertEquals("P001", linea.getIdProducto());
        assertEquals("Panel solar", linea.getNombreProducto());
        assertEquals(4, linea.getCantidad());
        assertEquals(150.00, linea.getPrecioUnitario(), 0.001);
        assertEquals(600.00, linea.getTotalLinea(), 0.001);
    }

    @Test
    public void testCalcularSubtotalIvaYTotal() {
        List<LineaPresupuesto> lineas = new ArrayList<LineaPresupuesto>();
        lineas.add(service.crearLineaPresupuesto("P001", "Panel solar", 4, 150.00));
        lineas.add(service.crearLineaPresupuesto("I001", "Inversor", 1, 900.00));

        double subtotal = service.calcularSubtotal(lineas);
        double iva = service.calcularIva(subtotal);
        double total = service.calcularTotal(subtotal, iva);

        assertEquals(1500.00, subtotal, 0.001);
        assertEquals(315.00, iva, 0.001);
        assertEquals(1815.00, total, 0.001);
    }

    @Test
    public void testFormatearDireccion() {
        Direccion direccion = new Direccion("Calle Mayor", "10", "38001", "Santa Cruz", "Tenerife");

        assertEquals("Calle Mayor 10, 38001 Santa Cruz, Tenerife", service.formatearDireccion(direccion));
    }
}
