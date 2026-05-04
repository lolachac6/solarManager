package service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import modelo.Direccion;
import modelo.LineaPresupuesto;

public class PresupuestoCalculoService {

    private static final double IVA = 0.21;

    public int calcularCantidadInversores(double potenciaInstalada) {
        if (potenciaInstalada <= 0) {
            return 0;
        }
        return (int) Math.ceil(potenciaInstalada / 5.0);
    }

    public int calcularBloquesCadaSeisPaneles(int numeroPaneles) {
        if (numeroPaneles <= 0) {
            return 0;
        }
        return (int) Math.ceil(numeroPaneles / 6.0);
    }

    public LineaPresupuesto crearLineaPresupuesto(String idProducto, String nombreProducto, int cantidad, double precioUnitario) {
        double totalLinea = cantidad * precioUnitario;
        return new LineaPresupuesto(idProducto, nombreProducto, cantidad, precioUnitario, totalLinea);
    }

    public double calcularSubtotal(List<LineaPresupuesto> lineas) {
        if (lineas == null) {
            return 0.0;
        }
        double subtotal = 0.0;
        for (LineaPresupuesto linea : lineas) {
            if (linea != null) {
                subtotal += linea.getTotalLinea();
            }
        }
        return subtotal;
    }

    public double calcularIva(double subtotal) {
        return subtotal * IVA;
    }

    public double calcularTotal(double subtotal, double iva) {
        return subtotal + iva;
    }

    public List<LineaPresupuesto> copiarLineas(List<LineaPresupuesto> lineas) {
        List<LineaPresupuesto> copia = new ArrayList<LineaPresupuesto>();
        if (lineas != null) {
            copia.addAll(lineas);
        }
        return copia;
    }

    public String formatearDireccion(Direccion direccion) {
        if (direccion == null) {
            return "";
        }
        return valor(direccion.getCalle()) + " "
                + valor(direccion.getNumero()) + ", "
                + valor(direccion.getCodigoPostal()) + " "
                + valor(direccion.getMunicipio()) + ", "
                + valor(direccion.getProvincia());
    }

    public String formatearImporte(double importe) {
        return new DecimalFormat("0.00").format(importe).replace(',', '.');
    }

    private String valor(String texto) {
        return texto == null ? "" : texto.trim();
    }
}
