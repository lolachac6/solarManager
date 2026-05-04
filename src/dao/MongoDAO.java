package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import db.MongoConnection;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class MongoDAO {

    protected MongoDatabase getDatabase() {
        try {
            return MongoConnection.conectar();
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo conectar con MongoDB", ex);
        }
    }

    protected MongoCollection<Document> getCollection(String collectionName) {
        return getDatabase().getCollection(collectionName);
    }

    protected List<Document> findAll(String collectionName) {
        List<Document> documents = new ArrayList<Document>();
        for (Document document : getCollection(collectionName).find()) {
            documents.add(document);
        }
        return documents;
    }

    protected List<Document> find(String collectionName, Bson filter) {
        List<Document> documents = new ArrayList<Document>();
        FindIterable<Document> iterable = getCollection(collectionName).find(filter);
        for (Document document : iterable) {
            documents.add(document);
        }
        return documents;
    }

    protected Document findFirst(String collectionName, Bson filter) {
        return getCollection(collectionName).find(filter).first();
    }

    protected Document findById(String collectionName, String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        try {
            return getCollection(collectionName).find(Filters.eq("_id", new ObjectId(id))).first();
        } catch (IllegalArgumentException ex) {
            return getCollection(collectionName).find(Filters.eq("_id", id)).first();
        }
    }

    protected void insert(String collectionName, Document document) {
        getCollection(collectionName).insertOne(document);
    }

    protected void updateById(String collectionName, String id, Document document) {
        try {
            getCollection(collectionName).updateOne(Filters.eq("_id", new ObjectId(id)), new Document("$set", document));
        } catch (IllegalArgumentException ex) {
            getCollection(collectionName).updateOne(Filters.eq("_id", id), new Document("$set", document));
        }
    }

    protected void deleteById(String collectionName, String id) {
        try {
            getCollection(collectionName).deleteOne(Filters.eq("_id", new ObjectId(id)));
        } catch (IllegalArgumentException ex) {
            getCollection(collectionName).deleteOne(Filters.eq("_id", id));
        }
    }
}
