package animation;

import cls.Monitor;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ArrowAnimation extends AnimationTimer {
    private final Monitor monitor;
    private ImageView actorRightArrow;
    private ImageView actorLeftArrow;
    private ImageView fricRightArrow;
    private ImageView fricLeftArrow;
    private ImageView totalForceLeftArrow;
    private ImageView totalForceRightArrow;
    private float oldActorLength;
    private long startNanoTime = System.nanoTime();
    public ArrowAnimation(Monitor monitor, ImageView actorLeftArrow, ImageView actorRightArrow) {
        this.monitor = monitor;
        this.actorLeftArrow = actorLeftArrow;
        this.actorRightArrow = actorRightArrow;
        this.oldActorLength = (float) actorLeftArrow.getFitWidth();
    }
    @Override
    public void handle(long currentNanoTime) {
        if (monitor.getActorForce().getValue() > 0) {
            actorLeftArrow.setVisible(false);
            actorRightArrow.fitWidthProperty().setValue(monitor.getActorForce().getValue());
            actorRightArrow.fitHeightProperty().setValue(50);
            actorRightArrow.setVisible(true);
        } else if (monitor.getActorForce().getValue() < 0) {
            actorRightArrow.setVisible(false);
            actorLeftArrow.fitWidthProperty().setValue(- monitor.getActorForce().getValue() );
            float chenhlech = oldActorLength - (float) actorLeftArrow.getFitWidth();
            actorLeftArrow.setLayoutX(actorLeftArrow.getLayoutX() + chenhlech);
            actorLeftArrow.fitHeightProperty().setValue(50);
            actorLeftArrow.setVisible(true);
        }
        startNanoTime = currentNanoTime;
        oldActorLength = (float) actorLeftArrow.getFitWidth();
    }
}
