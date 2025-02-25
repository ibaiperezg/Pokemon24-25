package dambat.controller;

import dambat.database.DatabaseManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    // Crear la base de datos si no existe
    DatabaseManager.createTable(); // Asegurar que la tabla "jugadores" existe
    DatabaseManager.createRankingTable(); // Asegurar que la tabla "ranking" existe

    // Cargar el último nombre guardado si existe
    String savedName = DatabaseManager.getLastSavedName();
    if (savedName != null) {
        nombreField.setText(savedName);
    }

    // Animación de parpadeo en el campo de texto
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
            nombreField.setStyle("-fx-border-color: green; -fx-text-fill: white;");
            System.out.println("✅ Nombre guardado correctamente: " + nombre);
        } else {
            nombreField.setStyle("-fx-border-color: red; -fx-text-fill: white;");
            System.out.println("❌ Error: El nombre está vacío.");
        }
    }

    @FXML
private void hasi() {
    String nombreGuardado = nombreField.getText().trim(); // Obtener el nombre ingresado

    if (nombreGuardado.isEmpty()) {
        nombreField.setStyle("-fx-border-color: red; -fx-text-fill: white;");
        System.out.println("❌ Error: No se ha introducido ningún nombre.");
        return;
    }

    System.out.println("🔹 Cambiando a Jolasa con el nombre: " + nombreGuardado);

    try {
        // Cargar la nueva escena
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dambat/fxml/Jolasa.fxml"));
        Parent root = loader.load();

        // Obtener el controlador de Jolasa
        JolasaController jolasaController = loader.getController();
        jolasaController.setNombre(nombreGuardado); // Pasar el nombre al controlador de Jolasa

        // Cambiar la escena
        Stage stage = (Stage) nombreField.getScene().getWindow(); // Obtener la ventana actual
        stage.setScene(new Scene(root));
        stage.setTitle("Jolasa - El Juego");
        stage.setFullScreen(true);
        stage.show();

        System.out.println("✅ Se ha cambiado a la escena del juego.");
    } catch (IOException e) {
        System.out.println("❌ ERROR al cargar la escena de juego.");
        e.printStackTrace();
    }
}


}

    