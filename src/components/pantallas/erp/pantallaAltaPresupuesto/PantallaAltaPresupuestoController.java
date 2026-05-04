package components.pantallas.erp.pantallaAltaPresupuesto;

import components.navigation.SessionContext;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.InstalacionFotovoltaica;
import modelo.LineaPresupuesto;
import modelo.Presupuesto;
import utils.AlertasSolarManager;
import service.ClienteService;
import service.PresupuestoCalculoService;
import service.PresupuestoService;

/**
 * Controlador de la pantalla de alta de presupuesto.
 *
 * Se encarga de recibir la instalación seleccionada, recuperar los datos del
 * cliente asociado, calcular las líneas del presupuesto a partir de las
 * colecciones Productos y GastosInstalacion, calcular subtotal, IVA y total,
 * y guardar toda la información en la colección Presupuestos de MongoDB.
 *
 * @author Iván
 */
public class PantallaAltaPresupuestoController {

    @FXML private TextField txtNombreApellidos;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtTipoCliente;
    @FXML private TextField txtDireccionCompleta;
    @FXML private TextField txtPanelesSolares;
    @FXML private TextField txtEstructura;
    @FXML private TextField txtInversor;
    @FXML private CheckBox chkBateria;
    @FXML private TextField txtMaterialElectrico;
    @FXML private TextField txtManoObra;
    @FXML private TextField txtTramitacion;
    @FXML private TextField txtSubtotal;
    @FXML private TextField txtImpuesto;
    @FXML private TextField txtTotal;
    @FXML private ComboBox<String> comboEstado;

    private InstalacionFotovoltaica instalacionSeleccionada;
    private String idCliente;
    private String idComercial;
    private List<LineaPresupuesto> lineasCalculadas;
    private double subtotalCalculado;
    private double ivaCalculado;
    private double totalCalculado;

    private final ClienteService clienteService = new ClienteService();
    private final PresupuestoService presupuestoService = new PresupuestoService();
    private final PresupuestoCalculoService calculoService = new PresupuestoCalculoService();

    /**
     * Carga los datos de la instalación y del cliente asociado.
     *
     * @param instalacion instalación seleccionada
     */
    public void cargarDatosInstalacion(InstalacionFotovoltaica instalacion) {

        if (instalacion == null) {
            return;
        }

        this.instalacionSeleccionada = instalacion;
        this.idCliente = instalacion.getIdCliente();

        cargarDatosCliente();
        cargarDatosInstalacionBase();
        cargarEstados();
        calculoPresupuesto();
    }

    /**
     * Carga en pantalla los datos básicos de la instalación seleccionada.
     */
    private void cargarDatosInstalacionBase() {
        txtPanelesSolares.setText(String.valueOf(instalacionSeleccionada.getNumeroPaneles()));
        txtInversor.setText(instalacionSeleccionada.getInversor() != null ? instalacionSeleccionada.getInversor() : "");
        chkBateria.setSelected(instalacionSeleccionada.getBateria());
    }

    /**
     * Carga los posibles estados del presupuesto.
     */
    private void cargarEstados() {
        
            comboEstado.getItems().addAll(
                    Presupuesto.EstadoPresupuesto.BORRADOR.name(),
                    Presupuesto.EstadoPresupuesto.ENVIADO.name(),
                    Presupuesto.EstadoPresupuesto.ACEPTADO.name(),
                    Presupuesto.EstadoPresupuesto.RECHAZADO.name(),
                    Presupuesto.EstadoPresupuesto.FACTURADO.name()
            );
        

        if (comboEstado.getValue() == null) {
            comboEstado.setValue(Presupuesto.EstadoPresupuesto.BORRADOR.name());
        }
    }

    /**
     * Recupera y muestra los datos del cliente a partir de su identificador.
     */
    private void cargarDatosCliente() {
        try {
            Cliente cliente = clienteService.obtenerPorId(idCliente);

            if (cliente == null) {
                limpiarCamposCliente();
                return;
            }

            String nombre = cliente.getNombre() != null ? cliente.getNombre() : "";
            String apellidos = cliente.getApellidos() != null ? cliente.getApellidos() : "";
            txtNombreApellidos.setText((nombre + " " + apellidos).trim());
            idComercial = cliente.getIdComercialAsignado() != null ? cliente.getIdComercialAsignado() : "";
            txtTelefono.setText(cliente.getTelefono() != null ? cliente.getTelefono() : "");
            txtDocumento.setText(cliente.getDni() != null && !cliente.getDni().trim().isEmpty()
                    ? cliente.getDni()
                    : cliente.getCif() != null ? cliente.getCif() : "");
            txtTipoCliente.setText(cliente.getTipoCliente() != null ? cliente.getTipoCliente().name() : "");
            txtDireccionCompleta.setText(calculoService.formatearDireccion(cliente.getDireccion()));

        } catch (Exception e) {
            e.printStackTrace();
            limpiarCamposCliente();
        }
    }

    /**
     * Calcula todas las líneas del presupuesto y actualiza los importes en
     * pantalla.
     */
    private void calculoPresupuesto() {
        try {
            lineasCalculadas = presupuestoService.calcularLineas(instalacionSeleccionada);

            int numeroPaneles = instalacionSeleccionada.getNumeroPaneles();
            double potenciaInstalada = instalacionSeleccionada.getPotenciaInstalada();
            int cantidadInversores = calculoService.calcularCantidadInversores(potenciaInstalada);
            int bloquesCadaSeisPaneles = calculoService.calcularBloquesCadaSeisPaneles(numeroPaneles);

            subtotalCalculado = calculoService.calcularSubtotal(lineasCalculadas);
            ivaCalculado = calculoService.calcularIva(subtotalCalculado);
            totalCalculado = calculoService.calcularTotal(subtotalCalculado, ivaCalculado);

            txtEstructura.setText(calculoService.formatearImporte(obtenerImporteLinea("ESTRUCTURA")));
            txtInversor.setText(calculoService.formatearImporte(presupuestoService.obtenerImporteProductoPorTipo(lineasCalculadas, "INVERSOR")));
            chkBateria.setSelected(instalacionSeleccionada.getBateria());
            txtMaterialElectrico.setText(calculoService.formatearImporte(obtenerImporteLinea("Material eléctrico")));
            txtManoObra.setText(calculoService.formatearImporte(obtenerImporteLinea("Mano de obra")));
            txtTramitacion.setText(calculoService.formatearImporte(obtenerImporteLinea("Tramitación")));
            txtSubtotal.setText(calculoService.formatearImporte(subtotalCalculado));
            txtImpuesto.setText(calculoService.formatearImporte(ivaCalculado));
            txtTotal.setText(calculoService.formatearImporte(totalCalculado));

        } catch (Exception e) {
            e.printStackTrace();
            txtEstructura.setText("");
            txtMaterialElectrico.setText("");
            txtManoObra.setText("");
            txtTramitacion.setText("");
            txtSubtotal.setText("");
            txtImpuesto.setText("");
            txtTotal.setText("");
        }
    }

    private double obtenerImporteLinea(String nombre) {
        if (lineasCalculadas == null || nombre == null) {
            return 0.0;
        }
        for (LineaPresupuesto linea : lineasCalculadas) {
            if (linea.getNombreProducto() != null
                    && linea.getNombreProducto().toUpperCase().contains(nombre.toUpperCase())) {
                return linea.getTotalLinea();
            }
        }
        return 0.0;
    }

    /**
     * Limpia los campos visuales del bloque de cliente.
     */
    private void limpiarCamposCliente() {
        txtNombreApellidos.setText("");
        txtDocumento.setText("");
        txtTipoCliente.setText("");
        txtDireccionCompleta.setText("");
    }

    /**
     * Formatea un importe numérico como texto.
     *
     * @param valor valor a formatear
     * @return importe formateado
     */
    private String formatearImporte(double valor) {
        return String.format("%.2f", valor);
    }

    /**
     * Guarda el presupuesto calculado en la colección Presupuestos.
     *
     * @param event evento del botón
     */
    @FXML
    private void guardarPresupuesto(ActionEvent event) {

        if (instalacionSeleccionada == null) {
            AlertasSolarManager.warning("Aviso", "No hay instalación seleccionada.");
            return;
        }

        try {
            calculoPresupuesto();

            Presupuesto presupuesto = presupuestoService.prepararPresupuesto(
                    idCliente,
                    idComercial,
                    instalacionSeleccionada,
                    comboEstado.getValue() != null ? comboEstado.getValue() : Presupuesto.EstadoPresupuesto.BORRADOR.name()
            );
            presupuestoService.guardar(presupuesto);

            AlertasSolarManager.operacionCorrecta();

            Stage stage = (Stage) txtNombreApellidos.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            AlertasSolarManager.errorGenerico("No se pudo guardar el presupuesto.");
        }
    }

    /**
     * Cierra la ventana actual.
     *
     * @param event evento del botón
     */
    @FXML
    private void cancelar(ActionEvent e) {
        if (!AlertasSolarManager.confirmar(
                "Salir sin guardar",
                "¿Desea salir sin guardar los cambios?"
        )) {
            return;
        }

        volver(e);
    }
    
    /**
     * Vuelve a la pantalla anterior según rol.
     *
     * @param event evento del botón
     */
    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}