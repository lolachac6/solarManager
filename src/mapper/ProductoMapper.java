package mapper;

import java.text.Normalizer;
import modelo.Producto;
import modelo.Producto.TipoProducto;
import org.bson.Document;

public final class ProductoMapper {

    private ProductoMapper() {
    }

    public static Producto fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        Producto producto = new Producto();
        Object id = doc.get("_id");
        producto.setId(id != null ? id.toString() : null);
        producto.setNombre(doc.getString("nombre"));
        producto.setDescripcion(doc.getString("descripcion"));
        producto.setTipoProducto(parseTipo(doc));
        producto.setPrecio(getDouble(doc, "precio"));
        producto.setIdProveedor(valueAsString(doc.get("idProveedor")));
        if (producto.getIdProveedor() == null || producto.getIdProveedor().trim().isEmpty()) {
            producto.setIdProveedor(valueAsString(doc.get("proveedor")));
        }
        producto.setStock(getInteger(doc, "stock"));
        return producto;
    }

    public static Document toDocument(Producto producto) {
        return new Document()
                .append("nombre", producto.getNombre())
                .append("descripcion", producto.getDescripcion())
                .append("tipoProducto", producto.getTipoProducto() != null ? producto.getTipoProducto().name() : null)
                .append("precio", producto.getPrecio())
                .append("idProveedor", producto.getIdProveedor())
                .append("stock", producto.getStock());
    }

    public static TipoProducto parseTipoProducto(String value) {
        String tipoNormalizado = normalizarTipo(value);
        if (tipoNormalizado.isEmpty()) {
            return null;
        }

        if ("PANEL_SOLAR".equals(tipoNormalizado)
                || "PANEL".equals(tipoNormalizado)
                || "PANELES".equals(tipoNormalizado)
                || "PANELES_SOLARES".equals(tipoNormalizado)) {
            return TipoProducto.PANEL_SOLAR;
        }

        if ("INVERSOR".equals(tipoNormalizado)
                || "INVERSORES".equals(tipoNormalizado)
                || "INVERSOR_SOLAR".equals(tipoNormalizado)
                || "INVERSORES_SOLARES".equals(tipoNormalizado)) {
            return TipoProducto.INVERSOR;
        }

        if ("BATERIA".equals(tipoNormalizado)
                || "BATERIAS".equals(tipoNormalizado)) {
            return TipoProducto.BATERIA;
        }

        if ("ESTRUCTURA".equals(tipoNormalizado)
                || "ESTRUCTURAS".equals(tipoNormalizado)) {
            return TipoProducto.ESTRUCTURA;
        }

        if ("MATERIAL_ELECTRICO".equals(tipoNormalizado)
                || "MATERIAL".equals(tipoNormalizado)) {
            return TipoProducto.MATERIAL_ELECTRICO;
        }

        try {
            return TipoProducto.valueOf(tipoNormalizado);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private static TipoProducto parseTipo(Document doc) {
        String value = doc.getString("tipoProducto");
        if (value == null) {
            value = doc.getString("tipo");
        }
        return parseTipoProducto(value);
    }

    private static String normalizarTipo(String value) {
        if (value == null) {
            return "";
        }
        String texto = Normalizer.normalize(value.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toUpperCase();
        texto = texto.replace('-', '_')
                .replace('/', '_')
                .replace(' ', '_');
        texto = texto.replaceAll("_+", "_");
        return texto;
    }

    private static double getDouble(Document doc, String field) {
        Object value = doc.get(field);
        return value instanceof Number ? ((Number) value).doubleValue() : 0.0;
    }

    private static int getInteger(Document doc, String field) {
        Object value = doc.get(field);
        return value instanceof Number ? ((Number) value).intValue() : 0;
    }

    private static String valueAsString(Object value) {
        return value != null ? value.toString() : null;
    }
}
