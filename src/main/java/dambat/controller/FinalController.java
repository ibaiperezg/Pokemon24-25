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
        Node scrollPane = rankingArea.lookup(".scroll-pane");
        if (scrollPane != null) {
            scrollPane.setVisible(false);
            scrollPane.setManaged(false);
        }
        mostrarRanking(null, 0);
        System.out.println("📊 Rankinga rekuperatzen...");
        // Parpadeo moduko animazioa Puntuazioan
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
            
            String rutaFXML = "/dambat/fxml/escenaNombre.fxml"; 
            System.out.println("🔎 Eszena kargatzen hemendik: " + getClass().getResource(rutaFXML));

            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            // Eszena berriko kontroladorea hartu
            NombreController nombreController = loader.getController();
            if (nombreController != null) {
                System.out.println("✅ NombreController ONDO kargatu da.");
            } else {
                System.out.println("❌ ERROREA: NombreController ez da inizializatu.");
            }

            // NombreControllerrera aldatu
            Stage stage = (Stage) restartButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Izena sartu"); // Titulua
            stage.setFullScreen(true);
            stage.show();

            System.out.println("🔄 Lehenengo eszenara aldatu da.");
        } catch (IOException e) {
            System.out.println("❌ ERROREA: Ezin izan da lehenengo eszena kargatu.");
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
        // 5 denbora onenak jaso
        List<String> topTiempos = DatabaseManager.obtenerTopTiempos();
    
        // Debug: Konsolan ikusi jasotako datuak
        System.out.println("📊 Recuperando ranking...");
    
        StringBuilder rankingText = new StringBuilder();
        
        // 1Oraingo jokalariaren denbora
        rankingText.append("🕒 ZURE DENBORA: ").append(nombreJugador).append(" - ").append(tiempoJugador).append("s\n\n");
    
        // Ranking generala
        rankingText.append("🏆 DENBORA ONENAK:\n");
        
        if (topTiempos.isEmpty()) {
            System.out.println("⚠️ Ez dago denborarik datubasean.");
            rankingText.append("⚠️ Oraindik ez dago denborarik.\n¡Lehenengoa izan zaitez!");
        } else {
            for (String tiempo : topTiempos) {
                System.out.println("🔹 " + tiempo);
                rankingText.append(tiempo).append("\n");
            }
        }
    
        rankingArea.setText(rankingText.toString());
    }
    

}
