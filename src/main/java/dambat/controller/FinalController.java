package dambat.controller;

import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

import dambat.database.DatabaseManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FinalController {

    @FXML
    private Label player1Score; // Jokalariaren puntuazioa erakusten duen Label-a

    @FXML
    private Button restartButton; // Berriro jolasteko botoia

    @FXML
    private Button exitButton; // Jokotik irteteko botoia

    @FXML
    private TextArea rankingArea;

    /**
     * Eszena kargatzen denean exekutatzen da.
     */

    @FXML
    public void initialize() {
        
        mostrarRanking(null, 0);
        System.out.println("📊 Recuperando ranking...");
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
            // 📌 Ruta del archivo FXML de la pantalla de ingreso del nombre
            String rutaFXML = "/dambat/fxml/escenaNombre.fxml"; // Cambiar la ruta al FXML de NombreController
            System.out.println("🔎 Cargando escena desde: " + getClass().getResource(rutaFXML));

            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            // 🎯 Obtener el controlador de la nueva escena (NombreController)
            NombreController nombreController = loader.getController();
            if (nombreController != null) {
                System.out.println("✅ NombreController cargado correctamente.");
            } else {
                System.out.println("❌ ERROR: NombreController no se ha inicializado.");
            }

            // 🚀 Cambiar la escena a la de NombreController
            Stage stage = (Stage) restartButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Introduce tu Nombre"); // Personalizar el título de la ventana
            stage.setFullScreen(true);
            stage.show();

            System.out.println("🔄 Se ha cambiado a la escena del Nombre.");
        } catch (IOException e) {
            System.out.println("❌ ERROR: No se pudo cargar la escena del Nombre.");
            e.printStackTrace();
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

    public void mostrarRanking(String nombreJugador, double tiempoJugador) {
        // Obtener los 5 mejores tiempos
        List<String> topTiempos = DatabaseManager.obtenerTopTiempos();
    
        // Debug: Ver en consola los datos obtenidos
        System.out.println("📊 Recuperando ranking...");
    
        StringBuilder rankingText = new StringBuilder();
        
        // 1️⃣ Primero, mostramos el tiempo del jugador actual
        rankingText.append("🕒 Tu tiempo: ").append(nombreJugador).append(" - ").append(tiempoJugador).append("s\n\n");
    
        // 2️⃣ Luego, mostramos el ranking general
        rankingText.append("🏆 Mejores tiempos:\n");
        
        if (topTiempos.isEmpty()) {
            System.out.println("⚠️ No hay tiempos guardados en la base de datos.");
            rankingText.append("⚠️ No hay tiempos registrados aún.\n¡Sé el primero en jugar!");
        } else {
            for (String tiempo : topTiempos) {
                System.out.println("🔹 " + tiempo);
                rankingText.append(tiempo).append("\n");
            }
        }
    
        rankingArea.setText(rankingText.toString());
    }
    

}
