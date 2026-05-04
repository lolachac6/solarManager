package service;

import dao.GastosInstalacionDAO;
import dao.PresupuestoDAO;
import dao.ProductoDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.InstalacionFotovoltaica;
import modelo.LineaPresupuesto;
import modelo.Presupuesto;
import modelo.Producto;
import org.bson.Document;

public class PresupuestoService {

    private final PresupuestoDAO presupuestoDAO;
    private final ProductoDAO productoDAO;
    private final GastosInstalacionDAO gastosInstalacionDAO;
    private final PresupuestoCalculoService calculoService;

    public PresupuestoService() {
        this(new PresupuestoDAO(), new ProductoDAO(), new GastosInstalacionDAO(), new PresupuestoCalculoService());
    }

    public PresupuestoService(PresupuestoDAO presupuestoDAO, ProductoDAO productoDAO,
            GastosInstalacionDAO gastosInstalacionDAO, PresupuestoCalculoService calculoService) {
        this.presupuestoDAO = presupuestoDAO;
        this.productoDAO = productoDAO;
        this.gastosInstalacionDAO = gastosInstalacionDAO;
        this.calculoService = calculoService;
    }

    public List<Presupuesto> obtenerPresupuestos() {
        return presupuestoDAO.obtenerTodos();
    }

    public void guardar(Presupuesto presupuesto) {
        presupuestoDAO.guardar(presupuesto);
    }

    public void eliminar(String id) {
        presupuestoDAO.eliminar(id);
    }

    public Presupuesto prepararPresupuesto(String idCliente, String idComercial,
            InstalacionFotovoltaica instalacion, String estado) {
        List<LineaPresupuesto> lineas = calcularLineas(instalacion);
        double subtotal = calculoService.calcularSubtotal(lineas);
        double iva = calculoService.calcularIva(subtotal);
        double total = calculoService.calcularTotal(subtotal, iva);

        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setIdCliente(idCliente);
        presupuesto.setIdComercial(idComercial);
        presupuesto.setFechaCreacion(LocalDate.now());
        presupuesto.setEstado(parseEstado(estado));
        presupuesto.setInstalacion(instalacion);
        presupuesto.setLineas(lineas);
        presupuesto.setSubtotal(subtotal);
        presupuesto.setIva(iva);
        presupuesto.setTotal(total);
        return presupuesto;
    }

    public List<LineaPresupuesto> calcularLineas(InstalacionFotovoltaica instalacion) {
        List<LineaPresupuesto> lineas = new ArrayList<LineaPresupuesto>();
        if (instalacion == null) {
            return lineas;
        }

        Producto panel = productoDAO.obtenerPorTipo("PANEL_SOLAR");
        Producto estructura = productoDAO.obtenerPorTipo("ESTRUCTURA");
        Producto inversor = productoDAO.obtenerPorTipo("INVERSOR");
        Producto bateria = productoDAO.obtenerPorTipo("BATERIA");
        Document gastos = gastosInstalacionDAO.obtenerConfiguracion();

        int numeroPaneles = instalacion.getNumeroPaneles();
        int cantidadInversores = calculoService.calcularCantidadInversores(instalacion.getPotenciaInstalada());
        int cantidadBateria = instalacion.getBateria() ? 1 : 0;
        int bloquesCadaSeisPaneles = calculoService.calcularBloquesCadaSeisPaneles(numeroPaneles);

        agregarLineaProducto(lineas, panel, numeroPaneles);
        agregarLineaProducto(lineas, estructura, numeroPaneles);
        agregarLineaProducto(lineas, inversor, cantidadInversores);
        agregarLineaProducto(lineas, bateria, cantidadBateria);
        agregarLineaGasto(lineas, gastos, "Material eléctrico", "materialElectrico", bloquesCadaSeisPaneles);
        agregarLineaGasto(lineas, gastos, "Mano de obra", "manoObra", bloquesCadaSeisPaneles);
        agregarLineaGasto(lineas, gastos, "Tramitación", "tramitacion", 1);

        return lineas;
    }

    public double obtenerImporteProductoPorTipo(List<LineaPresupuesto> lineas, String tipoProducto) {
        LineaPresupuesto linea = obtenerLineaProductoPorTipo(lineas, tipoProducto);
        return linea != null ? linea.getTotalLinea() : 0.0;
    }

    public int obtenerCantidadProductoPorTipo(List<LineaPresupuesto> lineas, String tipoProducto) {
        LineaPresupuesto linea = obtenerLineaProductoPorTipo(lineas, tipoProducto);
        return linea != null ? linea.getCantidad() : 0;
    }

    private LineaPresupuesto obtenerLineaProductoPorTipo(List<LineaPresupuesto> lineas, String tipoProducto) {
        if (lineas == null || tipoProducto == null) {
            return null;
        }

        Producto productoBuscado = productoDAO.obtenerPorTipo(tipoProducto);
        if (productoBuscado != null && productoBuscado.getId() != null) {
            for (LineaPresupuesto linea : lineas) {
                if (linea != null && productoBuscado.getId().equals(linea.getIdProducto())) {
                    return linea;
                }
            }
        }

        String tipoNormalizado = tipoProducto.toUpperCase();
        for (LineaPresupuesto linea : lineas) {
            if (linea != null && linea.getNombreProducto() != null
                    && linea.getNombreProducto().toUpperCase().contains(tipoNormalizado)) {
                return linea;
            }
        }

        return null;
    }

    private void agregarLineaProducto(List<LineaPresupuesto> lineas, Producto producto, int cantidad) {
        if (producto != null && cantidad > 0) {
            lineas.add(calculoService.crearLineaPresupuesto(
                    producto.getId(), producto.getNombre(), cantidad, producto.getPrecio()));
        }
    }

    private void agregarLineaGasto(List<LineaPresupuesto> lineas, Document gastos, String nombre, String campo, int cantidad) {
        if (cantidad > 0) {
            String id = gastos != null && gastos.getObjectId("_id") != null ? gastos.getObjectId("_id").toHexString() : "";
            double precio = obtenerDouble(gastos, campo);
            lineas.add(calculoService.crearLineaPresupuesto(id, nombre, cantidad, precio));
        }
    }

    private double obtenerDouble(Document doc, String campo) {
        if (doc == null) {
            return 0.0;
        }
        Object value = doc.get(campo);
        return value instanceof Number ? ((Number) value).doubleValue() : 0.0;
    }

    private Presupuesto.EstadoPresupuesto parseEstado(String estado) {
        try {
            return estado != null ? Presupuesto.EstadoPresupuesto.valueOf(estado) : Presupuesto.EstadoPresupuesto.BORRADOR;
        } catch (Exception ex) {
            return Presupuesto.EstadoPresupuesto.BORRADOR;
        }
    }
}
