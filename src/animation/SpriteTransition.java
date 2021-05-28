package animation;

import cls.Monitor;
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
    private final Monitor monitor;

    public SpriteTransition(ImageView actor, int duration, int[] para, Monitor monitor){
        //parameters for actor image - para: [count, width, height, offsetX, offsetY]
        this.actor = actor;
        this.count = para[0];
        this.width = para[1];
        this.height = para[2];
        this.offsetX = para[3];
        this.offsetY = para[4];
        this.monitor = monitor;

        setDelay(Duration.millis(0));
        setCycleDuration(Duration.millis(duration));
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        actor.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    @Override
    protected void interpolate(double k) {
        if (monitor.isPlaying()) {
            int index = Math.min((int) Math.floor(k * count), count - 1);
            int x = (index % 2) * width + offsetX;
            int y = (index / 2) * height + offsetY;
            actor.setViewport(new Rectangle2D(x, y, width, height));
        }
    }
}
