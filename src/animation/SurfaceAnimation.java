package animation;

import cls.Force;
import cls.Monitor;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SurfaceAnimation extends AnimationTimer {
    private long startNanoTime = System.nanoTime();
    private final Group road;
    private final Group background;
    private final Pane displayPane;
    private final Monitor monitor;
    private final float backgroundVelocityRate;

    public SurfaceAnimation(Group road, Pane displayPane, Monitor monitor, Group background, float backgroundVelocityRate) {
        this.road = road;
        this.displayPane = displayPane;
        this.monitor = monitor;
        this.background = background;
        this.backgroundVelocityRate = backgroundVelocityRate;
    }
    private void createInf(Group road){
        ImageView child2 = (ImageView) road.getChildren().get(1);
        if (road.getLayoutX() >= -30) {
            road.setLayoutX(road.getLayoutX() - child2.getLayoutX());
        } else if (road.getLayoutX() <= displayPane.getWidth() + 30 - (child2.getLayoutX() + child2.getFitWidth())) {
            road.setLayoutX(road.getLayoutX() + child2.getLayoutX());
        }
    }
    private void moveSurface(Group road, float t, float velocityRate){
        if (monitor.isPlaying()) {
            Force totalForce = monitor.getActorForce().plus(monitor.getFrictionForce());
            monitor.getObj().applyForce(totalForce, t);
            road.setLayoutX(road.getLayoutX() - t * velocityRate* monitor.getObj().getVelocity());
        }

    }

    @Override
    public void handle(long currentNanoTime) {
        //create infinity road
        createInf(road);
        createInf(background);

        //move the road
        float t = (float) ((currentNanoTime - startNanoTime) / 1000000000.0);
        moveSurface(road,t,1);
        moveSurface(background,t,backgroundVelocityRate);
        startNanoTime = currentNanoTime;
    }
}