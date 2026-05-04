package mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.LineaPresupuesto;
import modelo.Presupuesto;
import modelo.Presupuesto.EstadoPresupuesto;
import org.bson.Document;

public final class PresupuestoMapper {

    private PresupuestoMapper() {
    }

    public static Presupuesto fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        Presupuesto presupuesto = new Presupuesto();
        Object id = doc.get("_id");
        presupuesto.setId(id != null ? id.toString() : null);
        presupuesto.setIdCliente(valueAsString(doc.get("idCliente")));
        presupuesto.setIdComercial(valueAsString(doc.get("idComercial")));
        presupuesto.setFechaCreacion(parseFecha(doc.getString("fechaCreacion")));
        presupuesto.setEstado(parseEstado(doc.getString("estado")));
        presupuesto.setInstalacion(InstalacionMapper.fromDocument((Document) doc.get("instalacion")));
        presupuesto.setLineas(lineasFromDocuments((List<Document>) doc.get("lineas")));
        presupuesto.setSubtotal(getDouble(doc, "subtotal"));
        presupuesto.setIva(getDouble(doc, "iva"));
        presupuesto.setTotal(getDouble(doc, "total"));
        return presupuesto;
    }

    public static Document toDocument(Presupuesto presupuesto) {
        return new Document()
                .append("idCliente", presupuesto.getIdCliente())
                .append("idComercial", presupuesto.getIdComercial())
                .append("fechaCreacion", presupuesto.getFechaCreacion() != null ? presupuesto.getFechaCreacion().toString() : null)
                .append("estado", presupuesto.getEstado() != null ? presupuesto.getEstado().name() : null)
                .append("instalacion", presupuesto.getInstalacion() != null ? InstalacionMapper.toDocument(presupuesto.getInstalacion()) : null)
                .append("lineas", lineasToDocuments(presupuesto.getLineas()))
                .append("subtotal", presupuesto.getSubtotal())
                .append("iva", presupuesto.getIva())
                .append("total", presupuesto.getTotal());
    }

    public static List<Document> lineasToDocuments(List<LineaPresupuesto> lineas) {
        List<Document> documents = new ArrayList<Document>();
        if (lineas != null) {
            for (LineaPresupuesto linea : lineas) {
                documents.add(LineaPresupuestoMapper.toDocument(linea));
            }
        }
        return documents;
    }

    private static List<LineaPresupuesto> lineasFromDocuments(List<Document> documents) {
        List<LineaPresupuesto> lineas = new ArrayList<LineaPresupuesto>();
        if (documents != null) {
            for (Document document : documents) {
                lineas.add(LineaPresupuestoMapper.fromDocument(document));
            }
        }
        return lineas;
    }

    private static LocalDate parseFecha(String value) {
        try {
            return value != null ? LocalDate.parse(value) : null;
        } catch (Exception ex) {
            return null;
        }
    }

    private static EstadoPresupuesto parseEstado(String value) {
        try {
            return value != null ? EstadoPresupuesto.valueOf(value) : null;
        } catch (Exception ex) {
            return EstadoPresupuesto.BORRADOR;
        }
    }

    private static double getDouble(Document doc, String field) {
        Object value = doc.get(field);
        return value instanceof Number ? ((Number) value).doubleValue() : 0.0;
    }

    private static String valueAsString(Object value) {
        return value != null ? value.toString() : null;
    }
}
