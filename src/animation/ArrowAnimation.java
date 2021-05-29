package animation;

import cls.Force;
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
    private float oldFricLength;
    private float oldTotalLength;
    private long startNanoTime = System.nanoTime();
    public ArrowAnimation(Monitor monitor, ImageView actorLeftArrow, ImageView actorRightArrow,
                                           ImageView fricLeftArrow, ImageView fricRightArrow,
                                           ImageView totalForceLeftArrow, ImageView totalForceRightArrow) {
        this.monitor = monitor;
        this.actorLeftArrow = actorLeftArrow;
        this.actorRightArrow = actorRightArrow;
        this.fricLeftArrow = fricLeftArrow;
        this.fricRightArrow = fricRightArrow;
        this.totalForceLeftArrow = totalForceLeftArrow;
        this.totalForceRightArrow = totalForceRightArrow;
        this.oldActorLength = (float) actorLeftArrow.getFitWidth();
        this.oldFricLength = (float) fricLeftArrow.getFitWidth();
        this.oldTotalLength = (float) totalForceLeftArrow.getFitWidth();
    }
    @Override
    public void handle(long currentNanoTime) {
        if (monitor.getActorForce().getValue() > 0) {
            actorLeftArrow.setVisible(false);
            actorRightArrow.fitWidthProperty().setValue(monitor.getActorForce().getValue()*2);
            actorRightArrow.fitHeightProperty().setValue(50);
            actorRightArrow.setVisible(true);
        } else if (monitor.getActorForce().getValue() < 0) {
            actorRightArrow.setVisible(false);
            actorLeftArrow.fitWidthProperty().setValue(- monitor.getActorForce().getValue()*2 );
            float chenhlech1 = oldActorLength - (float) actorLeftArrow.getFitWidth();
            actorLeftArrow.setLayoutX(actorLeftArrow.getLayoutX() + chenhlech1);
            actorLeftArrow.fitHeightProperty().setValue(50);
            actorLeftArrow.setVisible(true);
        }
        if (monitor.getFrictionForce(monitor.getActorForce()).getValue() > 0) {
            fricLeftArrow.setVisible(false);
            fricRightArrow.fitWidthProperty().setValue(monitor.getFrictionForce(monitor.getActorForce()).getValue()*2);
            fricRightArrow.fitHeightProperty().setValue(50);
            fricRightArrow.setVisible(true);
        } else if (monitor.getFrictionForce(monitor.getActorForce()).getValue() < 0) {
            fricRightArrow.setVisible(false);
            fricLeftArrow.fitWidthProperty().setValue(- monitor.getFrictionForce(monitor.getActorForce()).getValue()*2 );
            float chenhlech2 = oldFricLength - (float) fricLeftArrow.getFitWidth();
            fricLeftArrow.setLayoutX(fricLeftArrow.getLayoutX() + chenhlech2);
            fricLeftArrow.fitHeightProperty().setValue(50);
            fricLeftArrow.setVisible(true);
        }
        Force totalForce = monitor.getActorForce().plus(monitor.getFrictionForce(monitor.getActorForce()));
        System.out.println("total force: " + totalForce.getValue());
        if (totalForce.getValue() > 0) {
            totalForceLeftArrow.setVisible(false);
            totalForceRightArrow.fitWidthProperty().setValue(totalForce.getValue()*2);
            totalForceRightArrow.fitHeightProperty().setValue(50);
            totalForceRightArrow.setVisible(true);
        } else if (totalForce.getValue() < 0) {
            totalForceRightArrow.setVisible(false);
            totalForceLeftArrow.fitWidthProperty().setValue(- totalForce.getValue()*2 );
            float chenhlech3 = oldTotalLength - (float) totalForceLeftArrow.getFitWidth();
            totalForceLeftArrow.setLayoutX(totalForceLeftArrow.getLayoutX() + chenhlech3);
            totalForceLeftArrow.fitHeightProperty().setValue(50);
            totalForceLeftArrow.setVisible(true);
        } else if (Math.round(totalForce.getValue()) == 0) {
            totalForceLeftArrow.setVisible(false);
            totalForceRightArrow.setVisible(false);
        }
        startNanoTime = currentNanoTime;
        oldActorLength = (float) actorLeftArrow.getFitWidth();
        oldFricLength = (float) fricLeftArrow.getFitWidth();
        oldTotalLength = (float) totalForceLeftArrow.getFitWidth();
    }
}
