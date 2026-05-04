package mapper;

import modelo.LineaPresupuesto;
import org.bson.Document;

public final class LineaPresupuestoMapper {

    private LineaPresupuestoMapper() {
    }

    public static LineaPresupuesto fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        return new LineaPresupuesto(
                valueAsString(doc.get("idProducto")),
                doc.getString("nombreProducto"),
                getInteger(doc, "cantidad"),
                getDouble(doc, "precioUnitario"),
                getDouble(doc, "totalLinea")
        );
    }

    public static Document toDocument(LineaPresupuesto linea) {
        return new Document()
                .append("idProducto", linea.getIdProducto())
                .append("nombreProducto", linea.getNombreProducto())
                .append("cantidad", linea.getCantidad())
                .append("precioUnitario", linea.getPrecioUnitario())
                .append("totalLinea", linea.getTotalLinea());
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
