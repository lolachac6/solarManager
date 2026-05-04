package mapper;

import modelo.InstalacionFotovoltaica;
import org.bson.Document;

public final class InstalacionMapper {

    private InstalacionMapper() {
    }

    public static InstalacionFotovoltaica fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        InstalacionFotovoltaica instalacion = new InstalacionFotovoltaica();
        Object id = doc.get("_id");
        instalacion.setId(id != null ? id.toString() : doc.getString("id"));
        instalacion.setIdCliente(valueAsString(doc.get("idCliente")));
        instalacion.setNombreCliente(doc.getString("nombreCliente"));
        instalacion.setPotenciaInstalada(getDouble(doc, "potenciaInstalada"));
        instalacion.setNumeroPaneles(getInteger(doc, "numeroPaneles"));
        instalacion.setProduccionEstimada(getDouble(doc, "produccionEstimada"));
        instalacion.setAhorroEstimado(getDouble(doc, "ahorroEstimado"));
        instalacion.setInversor(doc.getString("inversor"));
        Object bateria = doc.get("bateria");
        instalacion.setBateria(bateria instanceof Boolean ? (Boolean) bateria : false);
        instalacion.setDireccion(DireccionMapper.fromDocument((Document) doc.get("direccion")));
        return instalacion;
    }

    public static Document toDocument(InstalacionFotovoltaica instalacion) {
        Document doc = new Document()
                .append("id", instalacion.getId())
                .append("idCliente", instalacion.getIdCliente())
                .append("nombreCliente", instalacion.getNombreCliente())
                .append("potenciaInstalada", instalacion.getPotenciaInstalada())
                .append("numeroPaneles", instalacion.getNumeroPaneles())
                .append("produccionEstimada", instalacion.getProduccionEstimada())
                .append("ahorroEstimado", instalacion.getAhorroEstimado())
                .append("inversor", instalacion.getInversor())
                .append("bateria", instalacion.getBateria());
        doc.append("direccion", instalacion.getDireccion() != null ? DireccionMapper.toDocument(instalacion.getDireccion()) : null);
        return doc;
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
