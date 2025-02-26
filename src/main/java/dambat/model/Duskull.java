package dambat.model;

import dambat.Config;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Duskull extends Etsaia {
    private GridPane gdEremua;
    public Timeline duskullTimeline; 
    public Duskull(GridPane eremua) {
        gdEremua=eremua;
       
        this.setImage(new Image(getClass().getResource("/dambat/img/duskull.gif").toExternalForm()));
        gdEremua.add(this,2,2);
        defineDuskullAnimation();
        duskullTimeline.setCycleCount(Timeline.INDEFINITE);
        duskullTimeline.play();
    }
    public void defineDuskullAnimation() {
    duskullTimeline = new Timeline(
        new KeyFrame(Duration.seconds(0.35), e -> moveDuskull(1))    );
        duskullTimeline.setCycleCount(Timeline.INDEFINITE);
        duskullTimeline.play();}

    public void moveDuskull(double deltaY) {
        Integer currentX = GridPane.getColumnIndex(this);
        Integer currentY = GridPane.getRowIndex(this);
    
        if (currentX == null || currentY == null) {
            return;
        }
    
        int newCellY = currentY + (int) Math.signum(deltaY);
    
        if (newCellY >= 1 && newCellY <= 4) {
            GridPane.setRowIndex(this, newCellY);
        } else {
            
            GridPane.setRowIndex(this, 1);
        }
        
    }
    
}
