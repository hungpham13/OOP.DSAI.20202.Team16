package animation;

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
    private final double backgroundVelocityRate = 0.1;
    private final int UNIT = 260; //define pixel length of 1 meter on screen

    public SurfaceAnimation(Group road, Pane displayPane, Monitor monitor, Group background) {
        this.road = road;
        this.displayPane = displayPane;
        this.monitor = monitor;
        this.background = background;
    }
    private void createInf(Group road){
        ImageView child2 = (ImageView) road.getChildren().get(1);
        if (road.getLayoutX() >= -30) {
            road.setLayoutX(road.getLayoutX() - child2.getLayoutX());
        } else if (road.getLayoutX() <= displayPane.getWidth() + 30 - (child2.getLayoutX() + child2.getFitWidth())) {
            road.setLayoutX(road.getLayoutX() + child2.getLayoutX());
        }
    }

    @Override
    public void handle(long currentNanoTime) {
        //create infinity road and background
        createInf(road);
        createInf(background);

        //move the road and background
        float t = (float) ((currentNanoTime - startNanoTime) / 1000000000.0);
        if (monitor.isPlaying()){
            monitor.appliedForceToObjInTime(t);
            //*260
            road.setLayoutX(road.getLayoutX() - UNIT * t * 1 * monitor.getObj().getVelocity());
            background.setLayoutX(background.getLayoutX() - UNIT * t * backgroundVelocityRate * monitor.getObj().getVelocity());
        }
        startNanoTime = currentNanoTime;
    }
}
