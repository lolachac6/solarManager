package service;

import modelo.Producto;

public class StockService {

    public boolean hayStockSuficiente(Producto producto, int cantidadNecesaria) {
        return producto != null && cantidadNecesaria >= 0 && producto.getStock() >= cantidadNecesaria;
    }

    public int calcularStockRestante(Producto producto, int cantidadConsumida) {
        if (producto == null) {
            return 0;
        }
        return producto.getStock() - cantidadConsumida;
    }

    public void descontarStock(Producto producto, int cantidadConsumida) {
        if (producto != null) {
            producto.setStock(calcularStockRestante(producto, cantidadConsumida));
        }
    }
}
