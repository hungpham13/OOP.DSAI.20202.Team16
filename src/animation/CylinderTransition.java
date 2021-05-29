package animation;

import cls.Cylinder;
import cls.Monitor;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;

public class CylinderTransition extends Transition {

    private final Monitor monitor;
    private ImageView imageCylinder;

    public CylinderTransition(Monitor monitor, ImageView imageCylinder) {
        this.monitor = monitor;
        this.imageCylinder = imageCylinder;
    }


    @Override
    protected void interpolate(double v) {
        if (monitor.getObj() instanceof Cylinder) {
            System.out.println("Transition on Cylinder");
            imageCylinder.setRotate(((Cylinder) monitor.getObj()).getAngle());
        }
    }
}
