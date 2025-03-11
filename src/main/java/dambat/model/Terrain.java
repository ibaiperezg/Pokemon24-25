package dambat.model;

import dambat.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
/**
 * Terrain klasea, jokoaren lurraldea adierazten du.
 */
public class Terrain extends ImageView {
    
   
            
    public Terrain() {
         setImage(new Image(this.getClass().getResource("/dambat/img/suelo.png").toExternalForm()));
        // setOnMouseClicked(event -> {
        //     System.out.println("[" + ((Terrain) event.getSource()).getX() + "," + ((Terrain) event.getSource()).getY()
        //             + "]" + " puntuan egin duzu klik.");
        //     ilundu();
        // });

    }
   
    
        
    
}
