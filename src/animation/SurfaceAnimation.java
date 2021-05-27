package animation;

import cls.Force;
import cls.Monitor;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class SurfaceAnimation extends AnimationTimer {
    private long startNanoTime = System.nanoTime();
    private final Group road;
    private final Pane displayPane;
    private final Monitor monitor;

    public SurfaceAnimation(Group road, Pane displayPane, Monitor monitor) {
        this.road = road;
        this.displayPane = displayPane;
        this.monitor = monitor;
    }

    @Override
    public void handle(long currentNanoTime) {
        //create infinity road
        if (road.getLayoutX() >= -30) {
            road.setLayoutX(road.getLayoutX() - 1690);
        } else if (road.getLayoutX() <= displayPane.getWidth() + 30 - (1690 + 1722)) {
            road.setLayoutX(road.getLayoutX() + 1690);
        }

        //move the road
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        if (monitor.isPlaying()) {
            Force totalForce = monitor.getActorForce().plus(monitor.getFrictionForce());
            monitor.getObj().applyForce(totalForce, (float) t);
            road.setLayoutX(road.getLayoutX() - t * monitor.getObj().getVelocity());
        }
        startNanoTime = currentNanoTime;
    }
}
