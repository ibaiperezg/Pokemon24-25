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

public class NombreController {

    @FXML
    private TextField nombreField;

    @FXML
    private Button gordeButton;

    @FXML
    private Button hasiButton;

    @FXML
public void initialize() {
    // Datubasea sortu ez baldinbada existitzen
    DatabaseManager.createTables(); // Taula dagoen ziurtatu
     // ranking taula dagoela ziurtatu

    // Gordetako azken izena kargatu
    String savedName = DatabaseManager.getLastSavedName();
    if (savedName != null) {
        nombreField.setText(savedName);
    }

    // Parpadeoa animazioa izenaren barruan
    FadeTransition fadeAnimation = new FadeTransition(Duration.seconds(1.5), nombreField);
    fadeAnimation.setFromValue(0.5);
    fadeAnimation.setToValue(1.0);
    fadeAnimation.setCycleCount(FadeTransition.INDEFINITE);
    fadeAnimation.setAutoReverse(true);
    fadeAnimation.play();
}


    @FXML
    private void guardarNombre() {
        String nombre = nombreField.getText().trim();

        if (!nombre.isEmpty()) {
            DatabaseManager.saveName(nombre);
            nombreField.setStyle("-fx-border-color: green; -fx-text-fill: black;");
            System.out.println("✅ Izena ondo gordeta: " + nombre);
        } else {
            nombreField.setStyle("-fx-border-color: red; -fx-text-fill: black;");
            System.out.println("❌ Errorea:Izena utzik dago, bete ezazu.");
        }
    }

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

        // eszena aldatu
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

    