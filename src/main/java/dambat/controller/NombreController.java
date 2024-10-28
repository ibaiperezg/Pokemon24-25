package dambat.controller;

import java.io.IOException;
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
        System.out.println("Nombre ingresado: " + nombre); // Debugging line
    
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Jolasa.fxml"));
            Parent root = loader.load();
    
            JolasaController jolasaController = loader.getController();
            jolasaController.setNombre(nombre);
    
            Stage stage = (Stage) guardarButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Jolasa");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading the scene: " + e.getMessage());
            alert.showAndWait();
        }
    }
    

    

    public void movePikachu(double moveDelta2, double moveDelta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'movePikachu'");
    }

}
