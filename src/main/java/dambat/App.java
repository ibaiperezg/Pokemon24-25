package dambat;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Aplikazioaren klase nagusia
public class App extends Application {

    // Eszena aplikazioan
    private static Scene eszena;
    // Kontrolatzailea aplikazioan
    

    // Aplikazioa hasteko metodo nagusia
    @Override
    public void start(Stage eszenatokia) throws IOException {
        FXMLLoader fxmlKargatzailea = new FXMLLoader(getClass().getResource("fxml/escenaNombre.fxml"));
        Parent erroa = fxmlKargatzailea.load();
    
        // Hasierako tamaina egokia esplizituki zehazten du
        eszena = new Scene(erroa, 1560, 900); // doitu balio hauek zure beharretara egokitzeko
    
        eszenatokia.setScene(eszena);
        eszenatokia.sizeToScene(); // edukira automatikoki egokitzen da
        eszenatokia.setResizable(true); // erabiltzaileak tamaina aldatzea baimentzen du
        eszenatokia.show();
    }
    
    // Aplikazio nagusiaren metodoa
    public static void main(String[] args) {
        // Aplikazioa abiarazi
        launch();
    }

    public static void setRoot(String fxml) throws IOException {
        eszena.setRoot(kargatuFXML(fxml));
    }
    
    private static Parent kargatuFXML(String fxml) throws IOException {
        FXMLLoader fxmlKargatzailea = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlKargatzailea.load();
    }
}
