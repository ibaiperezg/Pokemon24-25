package dambat.controller;

import java.io.File;
import java.io.IOException;

import dambat.App;
import dambat.Config;
import dambat.database.DatabaseManager;
import dambat.model.Arbusto;
import dambat.model.Duskull;
import dambat.model.Escalera;
import dambat.model.Gengar;
import dambat.model.Haunter;
import dambat.model.Pikachu;
import dambat.model.Terrain;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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
    private long tiempoInicio;
    private String nombreUsuario;
    private double tiempoTranscurrido;

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
        
    }

    @FXML
    public void jolastenHasi() {
        tiempoInicio = System.currentTimeMillis(); // kronometroa hasten du
        System.out.println("⏳Kronoa martxan...");
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
            pikapikaPane.getChildren().clear(); 
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
        Integer escaleraX = GridPane.getColumnIndex(escalera);
        Integer escaleraY = GridPane.getRowIndex(escalera);
    
        if (escaleraX == null || escaleraY == null) {
            escaleraX = 7;
            escaleraY = 7;
        }
    
        if (pikachuX == escaleraX && pikachuY == escaleraY) {
            System.out.println("🎉 Pikachu eskailerara iritsi da!. Denbora gordetzen eta eszenaz aldatzen...");
    
            // Denbora gorde
            long tiempoFinal = System.currentTimeMillis();
            tiempoTranscurrido = (tiempoFinal - tiempoInicio) / 1000.0; // Segundotan jarri
    
            // Denbora datubasera
            DatabaseManager.guardarTiempo(nombreUsuario, tiempoTranscurrido);
            System.out.println("✅ Tiempo guardado en la base de datos: " + tiempoTranscurrido + "s");
    
            // Eszena aldatu
            showVideo();
        }
    }
    
    

    private void cambiarAEscenaFinal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dambat/fxml/escenaFinal.fxml"));
            Parent root = loader.load();
    
            FinalController finalController = loader.getController();
            if (finalController != null) {
                finalController.mostrarRanking(nombreUsuario, tiempoTranscurrido);
            } else {
                System.out.println("❌ ERROREA: FinalController ez da inizializatu.");
            }
    
            Stage stage = (Stage) borrokaEremua.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Puntuazioa");
            stage.setFullScreen(true);
            stage.show();
    
            System.out.println("✅ Azken eszenara aldatu da.");
        } catch (IOException e) {
            System.out.println("❌ ERROREA: Ezin izan da azken eszena kargatu.");
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
        this.nombreUsuario = nombre; // AIzena gordetzen dela ziurtatu
        if (nombreLabel != null) {
            nombreLabel.setText("Kaixo, " + nombre + "!");
        }
        System.out.println("🟢 Erabiltzaile izena jarrits: " + nombreUsuario);
    }
    
    private void showVideo() {
        try {
            // Bideoa kargatu (bide erlatiboa erabili)
            String videoPath = getClass().getResource("/dambat/video/sample.mp4").toExternalForm();
            System.out.println("Bideoaren bidea: " + videoPath); // Debug: Bidearen bidea kontsolan erakutsi
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);
    
            // Bideoa pantaila osoan erakutsi
            mediaView.setPreserveRatio(false); // Bideoa pantaila osoan egokitzeko
    
            // Bideoa erakusteko Stage berria sortu
            Stage videoStage = new Stage();
            videoStage.initModality(Modality.APPLICATION_MODAL);
            videoStage.setFullScreen(true);
    
            // Bideoaren tamaina Stage-aren tamainara egokitu
            mediaView.fitWidthProperty().bind(videoStage.widthProperty()); // Zabalera egokitu
            mediaView.fitHeightProperty().bind(videoStage.heightProperty()); // Altuera egokitu
    
            // Saltatzeko botoia sortu (hasieratik ikusgai)
            Button skipButton = new Button("SALTATU");
            skipButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white; -fx-font-size: 20px;");
            skipButton.setVisible(true); // Hasieratik ikusgai
    
            // Botoia klikatzean eszena aldatu (bideoa ez da geldituko)
            skipButton.setOnAction(e -> {
                mediaPlayer.stop(); // Bideoa gelditu
                videoStage.close(); // Leihoa itxi
                cambiarAEscenaFinal(); // Eszena aldatu
            });
    
            // Bideoa amaitu ondoren eszena aldatu
            mediaPlayer.setOnEndOfMedia(() -> {
                videoStage.close(); // Leihoa itxi
                cambiarAEscenaFinal(); // Eszena aldatu
            });
            mediaPlayer.setOnReady(() -> {
                System.out.println("✅ Bideoa kargatuta eta prest.");
                mediaPlayer.play(); // Reproducir el video cuando esté listo
            });
            videoStage.setOnCloseRequest(event -> {
                mediaPlayer.stop(); // Detener el reproductor al cerrar la ventana
            });
            // Bideoa eta botoia erakusteko layouta sortu (AnchorPane erabiliz)
            AnchorPane root = new AnchorPane();
            root.getChildren().addAll(mediaView, skipButton);
    
            // Botoia eskubian eta behean kokatu
            AnchorPane.setBottomAnchor(skipButton, 20.0); // Behean
            AnchorPane.setRightAnchor(skipButton, 20.0); // Eskubian
    
            // Scene eta Stage konfiguratu
            Scene scene = new Scene(root, Color.BLACK);
            videoStage.setScene(scene);
            videoStage.show();
    
            // Bideoa erreproduzitu
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("❌ ERROREA: Bideoa kargatzerakoan.");
            e.printStackTrace();
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
