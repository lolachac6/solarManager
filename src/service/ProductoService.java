package service;

import dao.ProductoDAO;
import java.util.List;
import modelo.Producto;

public class ProductoService {

    private final ProductoDAO productoDAO;
    private final ValidacionService validacionService;

    public ProductoService() {
        this(new ProductoDAO(), new ValidacionService());
    }

    public ProductoService(ProductoDAO productoDAO, ValidacionService validacionService) {
        this.productoDAO = productoDAO;
        this.validacionService = validacionService;
    }

    public List<Producto> obtenerProductos() {
        return productoDAO.obtenerTodos();
    }

    public Producto obtenerPorId(String id) {
        return productoDAO.obtenerPorId(id);
    }

    public Producto obtenerPorTipo(String tipo) {
        return productoDAO.obtenerPorTipo(tipo);
    }

    public void guardar(Producto producto) {
        validar(producto);
        if (producto.getId() == null || producto.getId().trim().isEmpty()) {
            productoDAO.guardar(producto);
        } else {
            productoDAO.actualizar(producto);
        }
    }

    public void eliminar(String id) {
        productoDAO.eliminar(id);
    }

    public void validar(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (validacionService.estaVacio(producto.getNombre())) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (!validacionService.esNumeroPositivo(producto.getPrecio())) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        if (!validacionService.esEnteroNoNegativo(producto.getStock())) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
}
