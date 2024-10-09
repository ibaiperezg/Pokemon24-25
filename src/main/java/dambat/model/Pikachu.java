package dambat.model;

import dambat.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Pikachu extends ImageView {

    int bizitzak = 3;
    public Pikachu() {
       
        this.setImage(new Image(getClass().getResource("/dambat/img/corriendo.gif").toExternalForm()));
       
    }


    

    // public void mugituEskuinera() {
    // try {
    // Thread.sleep(500);
    // GridPane.setColumnIndex(this,GridPane.getColumnIndex(this)+1);
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
}
