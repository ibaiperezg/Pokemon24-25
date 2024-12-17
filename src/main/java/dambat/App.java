package dambat;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Aplikazioaren nagusi klasea
public class App extends Application {

    // Eszena aplikazioa
    private static Scene scene;
    // Kontrolatzailea aplikazioa
    

    // Aplikazioa hasi beharreko nagusiki metodoa
    @Override
    public void start(Stage stage) throws IOException {
        // FXML fitxategitik interfazea kargatu
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/escenaNombre.fxml"));
        Parent root = fxmlLoader.load();
        
       

        // Eszena kargatu FXML-tik lortu den erroretik
        scene = new Scene(root);

        // Teklatu ekintzak kudeatu eszenan
        

        // Eszena konfiguratu eta bistaratu
        stage.setScene(scene);
        stage.show();
        
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
