package dao;

import java.util.ArrayList;
import java.util.List;
import mapper.PresupuestoMapper;
import modelo.Presupuesto;
import org.bson.Document;

public class PresupuestoDAO extends MongoDAO {

    private static final String COLLECTION = "Presupuestos";

    public List<Presupuesto> obtenerTodos() {
        List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
        for (Document doc : findAll(COLLECTION)) {
            presupuestos.add(PresupuestoMapper.fromDocument(doc));
        }
        return presupuestos;
    }

    public Presupuesto obtenerPorId(String id) {
        return PresupuestoMapper.fromDocument(findById(COLLECTION, id));
    }

    public void guardar(Presupuesto presupuesto) {
        insert(COLLECTION, PresupuestoMapper.toDocument(presupuesto));
    }

    public void actualizar(Presupuesto presupuesto) {
        updateById(COLLECTION, presupuesto.getId(), PresupuestoMapper.toDocument(presupuesto));
    }

    public void eliminar(String id) {
        deleteById(COLLECTION, id);
    }
}
