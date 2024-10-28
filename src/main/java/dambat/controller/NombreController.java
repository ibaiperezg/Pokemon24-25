package dambat.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        try {
            // Cargar la nueva escena
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dambat/Jolasa.fxml")); // Use absolute path if necessary
            Parent root = loader.load();

            // Obtener el controlador de Jolasa
            JolasaController jolasaController = loader.getController();
            jolasaController.setNombre(nombre); // Pasar el nombre

            // Obtener el escenario actual
            Stage stage = (Stage) guardarButton.getScene().getWindow();
            // Cambiar la escena
            stage.setScene(new Scene(root));
            stage.setTitle("Jolasa");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    public void movePikachu(double moveDelta2, double moveDelta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'movePikachu'");
    }
}
