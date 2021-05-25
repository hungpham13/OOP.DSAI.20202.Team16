package screen;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Controller {
    @FXML
    private Pane displayPane;

    @FXML
    private ImageView road;

    @FXML
    private void initialize(){
        //clipping pane
        Rectangle outputClip = new Rectangle();
        displayPane.setClip(outputClip);
        displayPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });

        //animate surface
        long startNanoTime = System.nanoTime();
        double startX = road.getX();

        new AnimationTimer(){
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime)/ 1000000000.0;
                road.setX(road.getX() - Main.monitor.getObj().getVelocity());

            }
        };

    }
}
