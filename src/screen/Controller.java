package screen;

import cls.Force;
import cls.Monitor;
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
        final long[] startNanoTime = {System.nanoTime()};

        new AnimationTimer(){
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime[0])/ 1000000000.0;
                //calculate new total applied force to object
                Force totalForce = Main.monitor.getActorForce().plus(Main.monitor.getFrictionForce());
                //apply total force to object
                Main.monitor.getObj().applyForce(totalForce, (float) t);
                //move road
                road.setX(road.getX() - t*Main.monitor.getObj().getVelocity());

                startNanoTime[0] = currentNanoTime;

            }
        }.start();

    }
}
