package animation;

import cls.Force;
import cls.Monitor;
import javafx.scene.image.ImageView;

public class AnimationArrow {
    private ImageView rightArrow;
    private ImageView leftArrow;
    private float oldLength;
    private Force force;

    public AnimationArrow(Force force, ImageView leftArrow, ImageView rightArrow) {

        this.force = force;
        this.leftArrow = leftArrow;
        this.rightArrow = rightArrow;
        this.oldLength = (float) leftArrow.getFitWidth();
        leftArrow.setVisible(false);
        rightArrow.setVisible(false);
        force.getValueProperty().addListener((observableValue, number, t1) -> update(t1.floatValue()));
    }

    private void update(float forceValue) {
        if (force.getValue() > 0) {
            leftArrow.setVisible(false);
            rightArrow.fitWidthProperty().setValue(force.getValue()*2);
            rightArrow.fitHeightProperty().setValue(50);
            rightArrow.setVisible(true);
        } else if (force.getValue() < 0) {
            rightArrow.setVisible(false);
            leftArrow.fitWidthProperty().setValue(- force.getValue()*2 );
            float chenhlech = oldLength - (float) leftArrow.getFitWidth();
            leftArrow.setLayoutX(leftArrow.getLayoutX() + chenhlech);
            leftArrow.fitHeightProperty().setValue(50);
            leftArrow.setVisible(true);
        } else if (force.getValue() == 0) {
            leftArrow.setVisible(false);
            rightArrow.setVisible(false);
        }
            oldLength = (float) leftArrow.getFitWidth();
    }
}

