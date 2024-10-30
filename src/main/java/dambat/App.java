package dambat;


import java.io.IOException;

import dambat.controller.NombreController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

// Aplikazioaren nagusi klasea
public class App extends Application {

    // Eszena aplikazioa
    private static Scene scene;
    // Kontrolatzailea aplikazioa
    private NombreController controller;

    // Aplikazioa hasi beharreko nagusiki metodoa
    @Override
    public void start(Stage stage) throws IOException {
        // FXML fitxategitik interfazea kargatu
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/escenaNombre.fxml"));
        Parent root = fxmlLoader.load();
        
        // FXML-tik kargatu ondoren kontrolatzailea lortu
        controller = fxmlLoader.getController();

        // Eszena kargatu FXML-tik lortu den erroretik
        scene = new Scene(root);
        // Teklatu ekintzak kudeatu eszenan
        scene.setOnKeyPressed(arg0 -> {
            try {
                handleKeyPress(arg0);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        // Eszena konfiguratu eta bistaratu
        stage.setScene(scene);
        stage.show();
    }

    // Teklatu ekintzak kudeatzeko metodoa
    private void handleKeyPress(KeyEvent event) throws Exception {
        // Sakatutako tekla lortu
        KeyCode keyCode = event.getCode();
        // Mugimenduaren neurria zehaztu
        double moveDelta = 10;

        // Kontrolatzailea ez bada null, bere metodoak erabili ahal izateko
        if (controller != null) {
            // Sakatutako tekla pantailan bistaratu
            System.out.println("Sakatutako tekla: " + keyCode);
            // Sakatutako tekla arabera ekintzak egin
            switch (keyCode) {
                case W:
                    controller.movePikachu(0, -moveDelta); // Goranzko mugimendua
                    break;
                case A:
                    controller.movePikachu(-moveDelta, 0); // Ezkerreko mugimendua
                    break;
                case S:
                    controller.movePikachu(0, moveDelta); // Beheranzko mugimendua
                    break;
                case D:
                    controller.movePikachu(moveDelta, 0); // Eskumako mugimendua
                    break;
                default:
                    // Beste tekla batzuetarako ezer egin
                    break;
            }
        }
    }

    // Aplikazio nagusiaren metoda
    public static void main(String[] args) {
        // Aplikazioa hasi
        launch();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
   
}
