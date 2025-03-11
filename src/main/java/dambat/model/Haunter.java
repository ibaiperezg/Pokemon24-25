package dambat.model;
import dambat.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
/**
 * Haunter klasea, jokoaren etsai bat.
 */
public class Haunter extends Etsaia{
    public Haunter() {
       
        this.setImage(new Image(getClass().getResource("/dambat/img/haunter.gif").toExternalForm()));
       
    }
    
}
