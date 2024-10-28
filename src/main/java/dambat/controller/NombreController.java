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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/jolasa.fxml"));
            Parent root = loader.load();

            JolasaController jolasaController = loader.getController();
            jolasaController.setNombre(nombre); // Pasar el nombre

            Stage stage = (Stage) guardarButton.getScene().getWindow();
            stage.setScene(new Scene(root, 300, 200));
            stage.setTitle("Jolasa");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void movePikachu(double moveDelta, double d) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'movePikachu'");
    }
}
