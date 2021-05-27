package animation;

import cls.Force;
import cls.Monitor;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SurfaceAnimation extends AnimationTimer {
    private long startNanoTime = System.nanoTime();
    private final Group road;
    private final Pane displayPane;
    private final Monitor monitor;
    private final float velocityRate;

    public SurfaceAnimation(Group road, Pane displayPane, Monitor monitor, float velocityRate) {
        this.road = road;
        this.displayPane = displayPane;
        this.monitor = monitor;
        this.velocityRate = velocityRate;
    }

    @Override
    public void handle(long currentNanoTime) {
        //create infinity road
        ImageView child2 = (ImageView) road.getChildren().get(1);
        if (road.getLayoutX() >= -30) {
            road.setLayoutX(road.getLayoutX() - child2.getLayoutX());
        } else if (road.getLayoutX() <= displayPane.getWidth() + 30 - (child2.getLayoutX() + child2.getFitWidth())) {
            road.setLayoutX(road.getLayoutX() + child2.getLayoutX());
        }

        //move the road
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        if (monitor.isPlaying()) {
            Force totalForce = monitor.getActorForce().plus(monitor.getFrictionForce());
            monitor.getObj().applyForce(totalForce, (float) t);
            road.setLayoutX(road.getLayoutX() - t * velocityRate* monitor.getObj().getVelocity());
        }
        startNanoTime = currentNanoTime;
    }
}
