package report;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.MongoConnection;
import org.bson.Document;

public final class ReportRepository {

    private ReportRepository() {
    }

    public static MongoDatabase getConexion() {
        try {
            return MongoConnection.conectar();
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo conectar con MongoDB para generar informes", ex);
        }
    }

    public static MongoCollection<Document> getCollection(String nombreColeccion) {
        return getConexion().getCollection(nombreColeccion);
    }
}
