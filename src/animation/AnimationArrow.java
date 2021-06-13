package animation;

import base.Force;
import javafx.beans.property.BooleanProperty;
import javafx.scene.image.ImageView;

public class AnimationArrow {
    private final ImageView rightArrow;
    private final ImageView leftArrow;
    private float oldLength;
    private final Force force;
    private final ImageView imageOnRoad;
    private final BooleanProperty visiblity;

    public AnimationArrow(Force force, ImageView leftArrow, ImageView rightArrow, ImageView imageOnRoad,
                          BooleanProperty visiblity) {
        this.force = force;
        this.leftArrow = leftArrow;
        this.rightArrow = rightArrow;
        this.oldLength = (float) leftArrow.getFitWidth();
        this.imageOnRoad = imageOnRoad;
        this.visiblity = visiblity;
        leftArrow.setVisible(false);
        rightArrow.setVisible(false);
        force.getValueProperty().addListener((observableValue, number, t1) -> update());
        imageOnRoad.layoutYProperty().addListener((observableValue, number, t1) -> updateY(t1.intValue() - number.intValue()));
        visiblity.addListener((observable -> update()));
    }


    private void updateY(float Y){
        rightArrow.setLayoutY(rightArrow.getLayoutY() + Y);
        rightArrow.setScaleY(imageOnRoad.getScaleY());
        rightArrow.setScaleX(imageOnRoad.getScaleX());
        leftArrow.setLayoutY(leftArrow.getLayoutY() + Y);
        leftArrow.setScaleY(imageOnRoad.getScaleY());
    }
    private void update() {
        if (visiblity.getValue()) {
            leftArrow.setVisible(true);
            rightArrow.setVisible(true);
            if (force.getValue() > 0) {
                leftArrow.setVisible(false);
                rightArrow.fitWidthProperty().setValue(force.getValue() * 2);
                rightArrow.fitHeightProperty().setValue(50);
                rightArrow.setVisible(true);
            } else if (force.getValue() < 0) {
                rightArrow.setVisible(false);
                leftArrow.fitWidthProperty().setValue(-force.getValue() * 2);
                float chenhlech = oldLength - (float) leftArrow.getFitWidth();
                leftArrow.setLayoutX(leftArrow.getLayoutX() + chenhlech);
                leftArrow.fitHeightProperty().setValue(50);
                leftArrow.setVisible(true);
            } else if (force.getValue() == 0) {
                leftArrow.setVisible(false);
                rightArrow.setVisible(false);
            }
            oldLength = (float) leftArrow.getFitWidth();
        } else {
            leftArrow.setVisible(false);
            rightArrow.setVisible(false);
        }
    }
}

