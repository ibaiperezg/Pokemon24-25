package dambat.model;


import dambat.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Escalera extends ImageView{
    public Escalera(){
         setImage(new Image(this.getClass().getResource("/dambat/img/escalera.png").toExternalForm()));
    }
    
}
