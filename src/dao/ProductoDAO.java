package dao;

import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import mapper.ProductoMapper;
import modelo.Producto;
import modelo.Producto.TipoProducto;
import org.bson.Document;

public class ProductoDAO extends MongoDAO {

    private static final String COLLECTION = "Productos";

    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<Producto>();
        for (Document doc : findAll(COLLECTION)) {
            productos.add(ProductoMapper.fromDocument(doc));
        }
        return productos;
    }

    public Producto obtenerPorId(String id) {
        return ProductoMapper.fromDocument(findById(COLLECTION, id));
    }

    public Producto obtenerPorTipo(String tipo) {
        TipoProducto tipoBuscado = ProductoMapper.parseTipoProducto(tipo);
        if (tipoBuscado == null) {
            return null;
        }

        Document doc = findFirst(COLLECTION, Filters.eq("tipoProducto", tipoBuscado.name()));
        if (doc == null) {
            doc = findFirst(COLLECTION, Filters.eq("tipo", tipoBuscado.name()));
        }
        if (doc != null) {
            return ProductoMapper.fromDocument(doc);
        }

        for (Document productoDoc : findAll(COLLECTION)) {
            Producto producto = ProductoMapper.fromDocument(productoDoc);
            if (producto != null && tipoBuscado.equals(producto.getTipoProducto())) {
                return producto;
            }
        }

        return null;
    }

    public void guardar(Producto producto) {
        insert(COLLECTION, ProductoMapper.toDocument(producto));
    }

    public void actualizar(Producto producto) {
        updateById(COLLECTION, producto.getId(), ProductoMapper.toDocument(producto));
    }

    public void eliminar(String id) {
        deleteById(COLLECTION, id);
    }
}
