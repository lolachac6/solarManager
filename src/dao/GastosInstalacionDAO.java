package dao;

import org.bson.Document;

public class GastosInstalacionDAO extends MongoDAO {

    private static final String COLLECTION = "GastosInstalacion";

    public Document obtenerConfiguracion() {
        return getCollection(COLLECTION).find().first();
    }
}
