package dao;

import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class ComercialDAO extends MongoDAO {

    private static final String COLLECTION = "Comerciales";

    public List<Document> obtenerActivosComoDocumentos() {
        return find(COLLECTION, Filters.eq("activo", true));
    }

    public List<Document> obtenerTodosComoDocumentos() {
        return new ArrayList<Document>(findAll(COLLECTION));
    }
}
