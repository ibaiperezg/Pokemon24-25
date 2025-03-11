package dambat.controller;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;

/**
 * VideoController klasea, bideo baten erreprodukzioa kudeatzen duena.
 */
public class VideoController {
    @FXML
    private MediaView mediaView;

    /**
     * Inicializazio metodoa.
     * Bideoaren fitxategia kargatzen du eta erreproduzitzen hasten da.
     */
    public void initialize() {
        String videoPath = new File("src/main/resources/dambat/video/sample.mp4").toURI().toString();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
    }
}
