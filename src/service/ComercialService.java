package service;

import dao.ComercialDAO;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ComercialService {

    private final ComercialDAO comercialDAO;

    public ComercialService() {
        this(new ComercialDAO());
    }

    public ComercialService(ComercialDAO comercialDAO) {
        this.comercialDAO = comercialDAO;
    }

    public List<ComercialResumen> obtenerComercialesActivos() {
        List<ComercialResumen> comerciales = new ArrayList<ComercialResumen>();
        for (Document doc : comercialDAO.obtenerActivosComoDocumentos()) {
            Object id = doc.get("_id");
            String nombre = doc.getString("nombre");
            if (id != null && nombre != null && !nombre.trim().isEmpty()) {
                comerciales.add(new ComercialResumen(id instanceof ObjectId ? ((ObjectId) id).toHexString() : id.toString(), nombre));
            }
        }
        return comerciales;
    }

    public static class ComercialResumen {
        private final String id;
        private final String nombre;

        public ComercialResumen(String id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public String getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }
    }
}
