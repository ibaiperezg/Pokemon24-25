package dambat.model;

import dambat.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
/**
 * Arbusto klasea, jokoaren inguruko oztopo bezala funtzionatzen duen elementua.
 */
public class Arbusto extends ImageView{
    public Arbusto() {
         setImage(new Image(this.getClass().getResource("/dambat/img/arbusto1.png").toExternalForm()));
        // setOnMouseClicked(event -> {
        //     System.out.println("[" + ((Terrain) event.getSource()).getX() + "," + ((Terrain) event.getSource()).getY()
        //             + "]" + " puntuan egin duzu klik.");
        //     ilundu();
        // });

    }
}
