package dambat.controller;

import java.io.IOException;

import dambat.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NombreController {

    @FXML
    private TextField nombreField;

    @FXML
    private Button guardarButton;

    
    @FXML
public void guardarNombre() {
    String nombre = nombreField.getText();
    System.out.println("ZURE IZENA: " + nombre); // Debugging line

    try {
        System.out.println("Attempting to load Jolasa.fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Jolasa.fxml"));

        Parent root = loader.load();

        // Check if the loader successfully loaded the controller
        JolasaController jolasaController = loader.getController();
        System.out.println("Controller loaded: " + (jolasaController != null));
        jolasaController.setNombre(nombre);

        Stage stage = (Stage) guardarButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Jolasa");
        stage.show();
        System.out.println("Scene switched to Jolasa.fxml");
    } catch (IOException e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading the scene: " + e.getMessage());
        alert.showAndWait();
    }
    
}


    

    

    
@FXML
    private void hasi() {
        try {
            App.setRoot("Jolasa");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
