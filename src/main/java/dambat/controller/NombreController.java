package dambat.controller;

import dambat.database.DatabaseManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

/**
 * NombreController klasea, jokalariaren izena kudeatzen du.
 */
public class NombreController {

    @FXML
    private TextField nombreField; // Jokalarien izena sartzeko testu eremua

    @FXML
    private Button gordeButton; // Izena gordetzeko botoia

    @FXML
    private Button hasiButton; // Jokoa hasteko botoia

    /**
     * Inicializazio metodoa. 
     * - Datu-baseko taulak sortzen ditu beharrezkoa bada.
     * - Azken gordetako izena berreskuratzen du.
     * - Izena sartzearen efektu bisuala gehitzen du.
     */
    @FXML
    public void initialize() {
        DatabaseManager.createTables(); // Taula existitzen dela ziurtatu

        // Gordetako azken izena kargatu
        String savedName = DatabaseManager.getLastSavedName();
        if (savedName != null) {
            nombreField.setText(savedName);
        }

        // Parpadeo animazioa izenaren barruan
        FadeTransition fadeAnimation = new FadeTransition(Duration.seconds(1.5), nombreField);
        fadeAnimation.setFromValue(0.5);
        fadeAnimation.setToValue(1.0);
        fadeAnimation.setCycleCount(FadeTransition.INDEFINITE);
        fadeAnimation.setAutoReverse(true);
        fadeAnimation.play();
    }

    /**
     * Jokalarien izena datu-basean gordetzen du.
     * 
     * - Izenik ez bada sartu, errorea erakusten du.
     * - Bestela, izena gordetzen du eta bistaratzen du.
     */
    @FXML
    private void guardarNombre() {
        String nombre = nombreField.getText().trim();

        if (!nombre.isEmpty()) {
            DatabaseManager.saveName(nombre);
            nombreField.setStyle("-fx-border-color: green; -fx-text-fill: black;");
            System.out.println("✅ Izena ondo gordeta: " + nombre);
        } else {
            nombreField.setStyle("-fx-border-color: red; -fx-text-fill: black;");
            System.out.println("❌ Errorea: Izena utzik dago, bete ezazu.");
        }
    }

    /**
     * Jokoa hasten du.
     * 
     * - Izenik ez bada sartu, errorea erakusten du.
     * - Bestela, eszena Jolasa.fxml fitxategira aldatzen du.
     */
    @FXML
    private void hasi() {
        String nombreGuardado = nombreField.getText().trim(); // Sartutako izena jaso

        if (nombreGuardado.isEmpty()) {
            nombreField.setStyle("-fx-border-color: red; -fx-text-fill: white;");
            System.out.println("❌ Errorea: Ez da izenik sartu.");
            return;
        }

        System.out.println("Eszenaz aldatzen: " + nombreGuardado);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dambat/fxml/Jolasa.fxml"));
            Parent root = loader.load();

            // Jolasacontroller jaso eta izena pasatu
            JolasaController jolasaController = loader.getController();
            jolasaController.setNombre(nombreGuardado); 

            // Eszena aldatu
            Stage stage = (Stage) nombreField.getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Jolasa");
            stage.setFullScreen(true);
            stage.show();

            System.out.println("✅ Eszena aldatu da.");
        } catch (IOException e) {
            System.out.println("❌ ERROREA eszena aldatzerakoan.");
            e.printStackTrace();
        }
    }
}