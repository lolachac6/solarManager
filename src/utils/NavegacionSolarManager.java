package utils;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Gestiona la navegación y la apertura uniforme de ventanas en Solar Manager.
 */
public final class NavegacionSolarManager {

    public static final double ANCHO_MODAL = 1200.0;
    public static final double ALTO_MODAL = 800.0;

    private NavegacionSolarManager() {
    }

    public static void abrirPantallaPrincipal(Node nodoOrigen, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(NavegacionSolarManager.class.getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = (Stage) nodoOrigen.getScene().getWindow();
            Scene scene = stage.getScene();

            if (scene == null) {
                scene = new Scene(root);
                stage.setScene(scene);
            } else {
                scene.setRoot(root);
            }

            stage.setFullScreen(false);
            stage.setResizable(true);
            stage.setMaximized(true);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stage.setFullScreen(false);
                    stage.setMaximized(true);
                    stage.centerOnScreen();
                }
            });

            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException ex) {
            AlertasSolarManager.error("Error de navegación", "No se pudo abrir la pantalla:\n" + rutaFXML + "\n\n" + ex.getMessage());
        }
    }

    public static ModalFXML prepararModal(Node nodoOrigen, String rutaFXML, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(NavegacionSolarManager.class.getResource(rutaFXML));
        Parent root = loader.load();

        Stage ventanaPadre = (Stage) nodoOrigen.getScene().getWindow();
        Stage modal = new Stage();

        modal.initOwner(ventanaPadre);
        modal.initModality(Modality.WINDOW_MODAL);
        modal.setTitle(titulo);
        modal.setResizable(false);
        modal.setScene(new Scene(root, ANCHO_MODAL, ALTO_MODAL));
        modal.setWidth(ANCHO_MODAL);
        modal.setHeight(ALTO_MODAL);
        modal.centerOnScreen();

        return new ModalFXML(loader, modal);
    }

    public static void configurarModal(Stage modal, Window propietario, Parent root, String titulo) {
        modal.initOwner(propietario);
        modal.initModality(Modality.WINDOW_MODAL);
        modal.setTitle(titulo);
        modal.setResizable(false);
        modal.setScene(new Scene(root, ANCHO_MODAL, ALTO_MODAL));
        modal.setWidth(ANCHO_MODAL);
        modal.setHeight(ALTO_MODAL);
        modal.centerOnScreen();
    }

    public static void cerrarVentana(Node nodoOrigen) {
        Stage stage = (Stage) nodoOrigen.getScene().getWindow();
        stage.close();
    }

    public static final class ModalFXML {

        private final FXMLLoader loader;
        private final Stage stage;

        private ModalFXML(FXMLLoader loader, Stage stage) {
            this.loader = loader;
            this.stage = stage;
        }

        public FXMLLoader getLoader() {
            return loader;
        }

        public Stage getStage() {
            return stage;
        }

        public void mostrarYEsperar() {
            stage.showAndWait();
        }
    }
}
