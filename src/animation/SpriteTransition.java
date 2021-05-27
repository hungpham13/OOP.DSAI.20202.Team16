package animation;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteTransition extends Transition {
    private final ImageView actor;
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;
    private final int count;

    public SpriteTransition(ImageView actor, float duration, int count, int width, int height, int offsetX,
                             int offsetY){
        this.actor = actor;
        this.count = count;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        setCycleDuration(Duration.millis(duration));
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
}

    @Override
    protected void interpolate(double k) {
        int index = Math.min((int) Math.floor(k * count), count-1);
        int x = (index % 2) * width + offsetX;
        int y = (index / 2) * height + offsetY;
        actor.setViewport(new Rectangle2D(x, y, width, height));
    }
}
