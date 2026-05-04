package mapper;

import modelo.Producto;
import org.bson.Document;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductoMapperTest {

    @Test
    public void testParseaTipoProductoDesdeCampoTipo() {
        Document doc = new Document("nombre", "Inversor Huawei")
                .append("tipo", "INVERSOR")
                .append("precio", 900.0)
                .append("stock", 3);

        Producto producto = ProductoMapper.fromDocument(doc);

        assertEquals(Producto.TipoProducto.INVERSOR, producto.getTipoProducto());
        assertEquals(900.0, producto.getPrecio(), 0.001);
    }

    @Test
    public void testParseaTipoProductoConTextoNormalizado() {
        Document doc = new Document("nombre", "Inversor Huawei")
                .append("tipoProducto", "Inversor solar")
                .append("precio", 900.0)
                .append("stock", 3);

        Producto producto = ProductoMapper.fromDocument(doc);

        assertEquals(Producto.TipoProducto.INVERSOR, producto.getTipoProducto());
    }

    @Test
    public void testParseaPanelSolarConEspacios() {
        Document doc = new Document("nombre", "Panel 550W")
                .append("tipoProducto", "Panel solar")
                .append("precio", 150.0)
                .append("stock", 20);

        Producto producto = ProductoMapper.fromDocument(doc);

        assertEquals(Producto.TipoProducto.PANEL_SOLAR, producto.getTipoProducto());
    }
}
