package dambat.controller;

import java.io.IOException;

import dambat.App;
import dambat.Config;
import dambat.model.Arbusto;
import dambat.model.Duskull;
import dambat.model.Escalera;
import dambat.model.Gengar;
import dambat.model.Haunter;
import dambat.model.Pikachu;
import dambat.model.Terrain;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

// Jolasaren kontrolatzailea
public class JolasaController {

    // GridPane elementua FXML-tik kargatzeko
    @FXML
    public GridPane borrokaEremua;
    @FXML
    public VBox jolaseremua;
    @FXML
    private Label nombreLabel;
    // Pikachu objetua
    private Pikachu pikachu;
    private Duskull duskull;
    private Stage pikapikaStage;
    private Gengar gengar;
    private Timeline gengarTimeline;
    private Haunter haunter;
    private Timeline haunterTimeline;
    private Escalera escalera;
    private boolean IstouchinEscalera = false;

    // Inizializazioa
    @FXML
    protected void initialize() {
        displayPath();
        createAndPlacePikachu();
        createAndPlaceDuskull();
        createPikapikaStage();
        createAndPlaceGengar();
        createAndPlaceHaunter();
        createEscalera();
        /*
         * if (borrokaEremua != null) {
         * FadeTransition fadeAnimation = new FadeTransition(Duration.seconds(1.5),
         * borrokaEremua);
         * fadeAnimation.setFromValue(0.5);
         * fadeAnimation.setToValue(1.0);
         * fadeAnimation.setCycleCount(FadeTransition.INDEFINITE);
         * fadeAnimation.setAutoReverse(true);
         * fadeAnimation.play();
         * } else {
         * System.out.
         * println("⚠️ ADVERTENCIA: `borrokaEremua` es NULL. No se aplicará FadeTransition."
         * );
         * }
         */
    }

    @FXML
    public void jolastenHasi() {
        startGengarAnimation();
        startHaunterAnimation();

        // Eszenan gauden bitartean, teklatua entzuten jarri
        Scene unekoEszena = borrokaEremua.getScene();
        unekoEszena.setOnKeyPressed(Event -> {
            try {
                handleKeyPress(Event);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    // Erremintaren bidezko bideen konfigurazioa
    int[][] path = { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 1, 2 }, { 2, 2 }, { 3, 2 }, { 3, 1 }, { 3, 0 }, { 4, 0 },
            { 5, 0 }, { 6, 0 }, { 6, 1 }, { 6, 2 }, { 6, 3 }, { 6, 4 }, { 5, 4 }, { 5, 5 }, { 5, 4 }, { 4, 5 },
            { 3, 5 }, { 2, 5 }, { 2, 4 }, { 1, 4 }, { 0, 4 }, { 0, 5 }, { 0, 6 }, { 0, 7 }, { 1, 7 }, { 2, 7 },
            { 3, 7 }, { 4, 7 }, { 5, 7 }, { 6, 7 }, { 7, 7 } };

    private String nombre;

    private void switchToSecondary() throws IOException {
        App.setRoot("hasiera");
    }

    // Erremintaren bideak erakutsi
    public void displayPath() {
        for (int i = 0; i < Config.BOARD_SIZE; i++) {
            for (int j = 0; j < Config.BOARD_SIZE; j++) {
                boolean isPath = false;

                for (int x = 0; x < path.length; x++) {
                    int[] coordinates = path[x];
                    if (coordinates[0] == i && coordinates[1] == j) {
                        isPath = true;
                        break;
                    }
                }

                // Erremintaren bideak edo arbustoak gehitu GridPane-an
                if (isPath) {
                    borrokaEremua.add(new Terrain(), i, j);
                } else {
                    borrokaEremua.add(new Arbusto(), i, j);
                }
            }
        }
    }

    public void createEscalera() {
        escalera = new Escalera();
        borrokaEremua.add(escalera, 7, 7);

    }

    // Pikachu objetua sortu eta kokatu
    public void createAndPlacePikachu() {
        pikachu = new Pikachu();
        borrokaEremua.add(pikachu, 0, 0);

    }

    public void createAndPlaceDuskull() {
        duskull = new Duskull(borrokaEremua);

    }

    public void createAndPlaceGengar() {
        gengar = new Gengar();
        borrokaEremua.add(gengar, 4, 4);
    }

    public void createAndPlaceHaunter() {
        haunter = new Haunter();
        borrokaEremua.add(haunter, 5, 2);
    }

    private void startHaunterAnimation() {
        haunterTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.25), e -> moveHaunter(1)));
        haunterTimeline.setCycleCount(Timeline.INDEFINITE);
        haunterTimeline.play();
    }

    /**
     * Duskulla berantza mugitzen dua
     * 
     * @param deltaY
     */

    public void moveHaunter(double deltaX) {
        Integer currentX = GridPane.getColumnIndex(haunter);
        Integer currentY = GridPane.getRowIndex(haunter);

        if (currentX == null || currentY == null) {
            return;
        }

        int newCellX = currentX + 1;

        if (newCellX >= 0 && newCellX <= 7) {
            GridPane.setColumnIndex(haunter, newCellX);
        } else {
            GridPane.setColumnIndex(haunter, 0);
        }
    }

    private void startGengarAnimation() {
        gengarTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.35), e -> moveGengar(1)));
        gengarTimeline.setCycleCount(Timeline.INDEFINITE);
        gengarTimeline.play();
    }

    /**
     * Duskulla berantza mugitzen du
     * 
     * @param deltaY
     */

    public void moveGengar(double deltaY) {
        Integer currentX = GridPane.getColumnIndex(gengar);
        Integer currentY = GridPane.getRowIndex(gengar);

        if (currentX == null || currentY == null) {
            return;
        }

        int newCellY = currentY + (int) Math.signum(deltaY);

        if (newCellY >= 4 && newCellY <= 7) {
            GridPane.setRowIndex(gengar, newCellY);
        } else {
            // Gengar hasierako puntura eramaten du
            GridPane.setRowIndex(gengar, 4);
        }
        checkCollision(pikachu);

    }

    private void checkCollision(Pikachu pikachu2) {
        if (pikachu.getBoundsInParent().intersects(gengar.getBoundsInParent()) ||
                pikachu.getBoundsInParent().intersects(duskull.getBoundsInParent()) ||
                pikachu.getBoundsInParent().intersects(haunter.getBoundsInParent())) {

            resetGame();
        }
    }

    public void movePikachu(double deltaX, double deltaY) throws Exception {
        // Pikachu posizioa lortu
        Integer currentX = GridPane.getColumnIndex(pikachu);
        Integer currentY = GridPane.getRowIndex(pikachu);

        // Posizioa balidatzeko
        if (currentX == null || currentY == null) {
            return; // Posizioa nula bada, irten
        }

        // Mugimendua egin
        int newCellX = currentX + (int) Math.signum(deltaX);
        int newCellY = currentY + (int) Math.signum(deltaY);

        // Bidearen barruan dagoen celda bila
        if (newCellX >= 0 && newCellX < Config.BOARD_SIZE && newCellY >= 0 && newCellY < Config.BOARD_SIZE) {
            for (int i = 0; i < path.length; i++) {
                if (path[i][0] == newCellX && path[i][1] == newCellY) {
                    // Bidearen celdarekin bat dator, pikachu mugitu
                    GridPane.setColumnIndex(pikachu, newCellX);
                    GridPane.setRowIndex(pikachu, newCellY);

                    // Pikachu azkenengo kasillan dagoen begiratzen du (7, 7)
                    if (newCellX == 7 && newCellY == 7) {
                        boolean pikachuReachedEscalera = true;

                        if (!pikachuReachedEscalera) {
                            showPikapikaImage();
                            pikachuReachedEscalera = true;
                        }
                    }
                    GridPane.setColumnIndex(pikachu, newCellX);
                    GridPane.setRowIndex(pikachu, newCellY);
                    System.out.println("📌 Pikachu se ha movido a: (" + newCellX + ", " + newCellY + ")");

                    // Kolisioa begiratzen du
                    checkCollision();
                    checkEscaleraCollision(newCellX, newCellY);
                    return; // Mugimendua amaitu, irten
                }
            }
        }
    }

    private void createPikapikaStage() {
        pikapikaStage = new Stage();
        pikapikaStage.initModality(Modality.APPLICATION_MODAL);

        StackPane pikapikaPane = new StackPane();
        Scene pikapikaScene = new Scene(pikapikaPane, Color.TRANSPARENT);
        pikapikaStage.setScene(pikapikaScene);
    }

    // ...

    private void showPikapikaImage() {
        try {

            Scene currentScene = borrokaEremua.getScene();
            Group root = (Group) currentScene.getRoot();

            StackPane pikapikaPane = (StackPane) pikapikaStage.getScene().getRoot();
            pikapikaPane.getChildren().clear(); // Limpiar cualquier contenido anterior
            pikapikaPane.getChildren().add(new ImageView(new Image("pikapika.png")));

            pikapikaStage.setFullScreen(true);
            pikapikaStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkCollision() throws Exception {
        if (pikachu.getBoundsInParent().intersects(duskull.getBoundsInParent()) ||
                pikachu.getBoundsInParent().intersects(gengar.getBoundsInParent()) ||
                pikachu.getBoundsInParent().intersects(haunter.getBoundsInParent()) ||
                duskull.getBoundsInParent().intersects(pikachu.getBoundsInParent()) ||
                gengar.getBoundsInParent().intersects(pikachu.getBoundsInParent()) ||
                haunter.getBoundsInParent().intersects(pikachu.getBoundsInParent())) {

            resetGame();

        }
    }

    @FXML

    public void start() throws IOException {
        App.setRoot("Jolasa2");

    }

    private void checkEscaleraCollision(int pikachuX, int pikachuY) {
        // Obtener las coordenadas actuales de la escalera
        Integer escaleraX = GridPane.getColumnIndex(escalera);
        Integer escaleraY = GridPane.getRowIndex(escalera);

        // Si las coordenadas son nulas, asignar la posición correcta manualmente
        if (escaleraX == null || escaleraY == null) {
            escaleraX = 7;
            escaleraY = 7;
        }

        // Imprimir coordenadas de depuración
        System.out.println("📌 Pikachu en: (" + pikachuX + "," + pikachuY + ")");
        System.out.println("🏆 Escalera en: (" + escaleraX + "," + escaleraY + ")");

        // Verificar si Pikachu ha llegado a la escalera
        if (pikachuX == escaleraX && pikachuY == escaleraY) {
            System.out.println("🎉 Pikachu ha llegado a la escalera. Cambiando de escena...");
            cambiarAEscenaFinal();
        }
    }

    private void cambiarAEscenaFinal() {
        try {
            // Aseguramos que la ruta sea correcta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dambat/fxml/escenaFinal.fxml"));

            // Verificar si el archivo realmente se encuentra
            if (loader.getLocation() == null) {
                System.out.println("❌ ERROR: No se encuentra escenaFinal.fxml en /dambat/fxml/");
                return;
            }

            Parent root = loader.load();

            Stage stage = (Stage) borrokaEremua.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Final - Puntuación");
            stage.setFullScreen(true);
            stage.show();

            System.out.println("✅ Se ha cambiado a la escena de Final.");
        } catch (IOException e) {
            System.out.println("❌ ERROR: No se pudo cargar la escena Final.");
            e.printStackTrace();
        }
    }

    private void resetGame() {
        pikapikaStage.hide();

        duskull.duskullTimeline.stop();
        gengarTimeline.stop();
        haunterTimeline.stop();

        GridPane.setColumnIndex(pikachu, 0);
        GridPane.setRowIndex(pikachu, 0);

        GridPane.setColumnIndex(duskull, 2);
        GridPane.setRowIndex(duskull, 1);

        GridPane.setColumnIndex(gengar, 4);
        GridPane.setRowIndex(gengar, 4);

        GridPane.setColumnIndex(haunter, 5);
        GridPane.setRowIndex(haunter, 2);

        duskull.defineDuskullAnimation();
        startGengarAnimation();
        startHaunterAnimation();
    }

    public void setNombre(String nombre) {
        if (nombreLabel != null) {
            nombreLabel.setText("Hola, " + nombre + "!");
        }
    }

    // Teklatu ekintzak kudeatzeko metodoa
    private void handleKeyPress(KeyEvent event) throws Exception {
        // Sakatutako tekla lortu
        KeyCode keyCode = event.getCode();
        // Mugimenduaren neurria zehaztu
        double moveDelta = 10;

        // Kontrolatzailea ez bada null, bere metodoak erabili ahal izateko

        // Sakatutako tekla pantailan bistaratu
        System.out.println("Sakatutako tekla: " + keyCode);
        // Sakatutako tekla arabera ekintzak egin
        switch (keyCode) {
            case W:
                movePikachu(0, -moveDelta); // Goranzko mugimendua
                break;
            case A:
                movePikachu(-moveDelta, 0); // Ezkerreko mugimendua
                break;
            case S:
                movePikachu(0, moveDelta); // Beheranzko mugimendua
                break;
            case D:
                movePikachu(moveDelta, 0); // Eskumako mugimendua
                break;
            default:
                // Beste tekla batzuetarako ezer egin
                break;

        }
    }


}
