package dambat.model;

import dambat.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
/**
 * Gengar klasea, jokoaren etsai bat.
 */
public class Gengar extends Etsaia {

    public Gengar() {
       
        this.setImage(new Image(getClass().getResource("/dambat/img/gengarp.gif").toExternalForm()));
       
    }
    
}