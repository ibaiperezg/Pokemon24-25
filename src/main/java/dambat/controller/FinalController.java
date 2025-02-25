package dambat.controller;

import javafx.util.Duration;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FinalController {

    @FXML
    private Label player1Score; // Jokalariaren puntuazioa erakusten duen Label-a

    @FXML
    private Button restartButton; // Berriro jolasteko botoia

    @FXML
    private Button exitButton; // Jokotik irteteko botoia

    /**
     * Eszena kargatzen denean exekutatzen da.
     */
    
    @FXML
    public void initialize() {
        player1Score.setText("X: 7 PUNTU"); // Datu dinamikoak erabiliz aldatu

        // Animación de parpadeo en la puntuación
        FadeTransition fadeAnimation = new FadeTransition(Duration.seconds(1.5), player1Score);
        fadeAnimation.setFromValue(0.5);
        fadeAnimation.setToValue(1.0);
        fadeAnimation.setCycleCount(FadeTransition.INDEFINITE);
        fadeAnimation.setAutoReverse(true);
        fadeAnimation.play();
    }

    /**
     * "Berriro jolastu" botoiaren ekintza kudeatzen du.
     * Jokalarien izena sartzeko eszena kargatzen du.
     */
    @FXML
    private void restartGame() {
        try {
            // "escenaNombre.fxml" fitxategia kargatu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Jolasa.fxml"));// Zure proiektuaren arabera bidea aldatu
            Parent root = loader.load();

            // Eszena berriko kontroladorea lortu
            JolasaController jolasaController = loader.getController();
            if (jolasaController != null) {
                System.out.println("Kontroladorea behar bezala kargatu da.");

                // Eszena berrian jokalariaren izena ezarri (datu dinamikoetatik har daiteke)
                String nombre = "Jokalaria1"; // Aldatu "Jokalaria1" datu dinamiko batekin
                jolasaController.setNombre(nombre);
            }

            // Uneko Stage-a lortu eta eszena aldatu
            Stage stage = (Stage) restartButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Jolasa"); // Leihoaren izenburua
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Akatsen kudeaketa FXML-a kargatzerakoan
        }
    }

    /**
     * "Irten" botoiaren ekintza kudeatzen du.
     * Uneko leihoa ixten du.
     */
    @FXML
    private void exitGame() {
        System.out.println("Jokoa amaitzen...");
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close(); // Uneko leihoa itxi
    }
}
