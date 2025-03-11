package dambat;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Aplikazioaren klase nagusia, JavaFX aplikazioa exekutatzen du.
 */
public class App extends Application {

    private static Scene eszena; // Aplikazioaren eszena nagusia

    /**
     * Aplikazioa abiarazteko metodoa.
     * 
     * @param eszenatokia Aplikazioaren eszena bistaratuko duen Stage objektua.
     * @throws IOException FXML fitxategia kargatzean errorea gerta daiteke.
     */
    @Override
    public void start(Stage eszenatokia) throws IOException {
        FXMLLoader fxmlKargatzailea = new FXMLLoader(getClass().getResource("fxml/escenaNombre.fxml"));
        Parent erroa = fxmlKargatzailea.load();
    
        // Hasierako tamaina egokia esplizituki zehazten du
        eszena = new Scene(erroa, 1560, 900); // Doitu balio hauek zure beharretara egokitzeko
    
        eszenatokia.setScene(eszena);
        eszenatokia.sizeToScene(); // Edukira automatikoki egokitzen da
        eszenatokia.setResizable(true); // Erabiltzaileak tamaina aldatzea baimentzen du
        eszenatokia.show();
    }
    
    /**
     * Aplikazioaren abiarazteko metodo nagusia.
     * 
     * @param args Komando lerroko argumentuak.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Eszena nagusia aldatu.
     * 
     * @param fxml Kargatu nahi den FXML fitxategiaren izena.
     * @throws IOException FXML fitxategia kargatzean errorea gerta daiteke.
     */
    public static void setRoot(String fxml) throws IOException {
        eszena.setRoot(kargatuFXML(fxml));
    }
    
    /**
     * FXML fitxategia kargatzen du.
     * 
     * @param fxml Kargatu nahi den FXML fitxategiaren izena.
     * @return Kargatutako FXML fitxategiko erro elementua.
     * @throws IOException FXML fitxategia kargatzean errorea gerta daiteke.
     */
    private static Parent kargatuFXML(String fxml) throws IOException {
        FXMLLoader fxmlKargatzailea = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlKargatzailea.load();
    }
}