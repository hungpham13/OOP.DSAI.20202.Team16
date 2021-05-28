package animation;

import cls.Monitor;
import javafx.scene.image.ImageView;

public class ArrowAnimation {
    private final Monitor monitor;
    private ImageView actorRightArrow;
    private ImageView actorLeftArrow;
    private ImageView fricRightArrow;
    private ImageView fricLeftArrow;
    private ImageView totalForceLeftArrow;
    private ImageView totalForceRightArrow;
    private float oldActorLength;
    public ArrowAnimation(Monitor monitor, ImageView actorLeftArrow, ImageView actorRightArrow) {
        this.monitor = monitor;
        this.actorLeftArrow = actorLeftArrow;
        this.actorRightArrow = actorRightArrow;
        this.oldActorLength = (float) actorLeftArrow.getFitWidth();
        monitor.getActorForce().getValueProperty().addListener((observableValue) -> update());
    }
    public void update(){
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
        oldActorLength = (float) actorLeftArrow.getFitWidth();
    }
}
