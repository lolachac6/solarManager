package mapper;

import java.time.LocalDate;
import java.util.Arrays;
import modelo.InstalacionFotovoltaica;
import modelo.LineaPresupuesto;
import modelo.Presupuesto;
import modelo.Presupuesto.EstadoPresupuesto;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import static org.junit.Assert.*;

public class PresupuestoMapperTest {

    @Test
    public void testToDocumentYFromDocument() {
        ObjectId id = new ObjectId();
        InstalacionFotovoltaica instalacion = new InstalacionFotovoltaica();
        instalacion.setIdCliente("CLI001");
        instalacion.setNombreCliente("Cliente Demo");
        instalacion.setPotenciaInstalada(5.5);
        instalacion.setNumeroPaneles(12);

        Presupuesto presupuestoOriginal = new Presupuesto(
                id.toString(),
                "CLI001",
                "COM001",
                LocalDate.of(2026, 4, 1),
                EstadoPresupuesto.BORRADOR,
                instalacion,
                Arrays.asList(new LineaPresupuesto("P001", "Panel", 2, 150.0, 300.0)),
                300.0,
                63.0,
                363.0
        );

        Document doc = PresupuestoMapper.toDocument(presupuestoOriginal);
        doc.append("_id", id);

        Presupuesto presupuesto = PresupuestoMapper.fromDocument(doc);

        assertEquals(id.toString(), presupuesto.getId());
        assertEquals("CLI001", presupuesto.getIdCliente());
        assertEquals("COM001", presupuesto.getIdComercial());
        assertEquals(LocalDate.of(2026, 4, 1), presupuesto.getFechaCreacion());
        assertEquals(EstadoPresupuesto.BORRADOR, presupuesto.getEstado());
        assertEquals(1, presupuesto.getLineas().size());
        assertEquals(300.0, presupuesto.getSubtotal(), 0.001);
        assertEquals(63.0, presupuesto.getIva(), 0.001);
        assertEquals(363.0, presupuesto.getTotal(), 0.001);
    }
}
