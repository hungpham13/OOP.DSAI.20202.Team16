package screen;

import cls.Force;
import com.jfoenix.controls.JFXButton;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Controller {
    @FXML
    private Pane displayPane;

    @FXML
    private Group road;

    @FXML
    private void initialize(){
        //clipping pane
        Rectangle outputClip = new Rectangle();
        displayPane.setClip(outputClip);
        displayPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });

        //animate surface
        final long[] startNanoTime = {System.nanoTime()};

        new AnimationTimer(){
            @Override
            public void handle(long currentNanoTime) {
                //create infinity road
                if (road.getLayoutX() >= -30) {
                    road.setLayoutX(road.getLayoutX()-1690);
                } else if (road.getLayoutX() <= displayPane.getWidth()+30-(1690+1722)) {
                    road.setLayoutX(road.getLayoutX()+1690);
                }

                //move the road
                double t = (currentNanoTime - startNanoTime[0])/ 1000000000.0;
                if (Main.monitor.isPlaying()) {
                    //calculate new total applied force to object
                    Force totalForce = Main.monitor.getActorForce().plus(Main.monitor.getFrictionForce());
                    //apply total force to object
                    Main.monitor.getObj().applyForce(totalForce, (float) t);
                    //move road
                    road.setLayoutX(road.getLayoutX() - t * Main.monitor.getObj().getVelocity());
                }

                startNanoTime[0] = currentNanoTime;
            }
        }.start();
    }

    @FXML
    private JFXButton playBtn;
    @FXML
    private JFXButton pauseBtn;
    @FXML
    public void playPressedBtn(ActionEvent e){
        Main.monitor.cont();
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }
    @FXML
    public void pausePressedBtn(ActionEvent e){
        Main.monitor.pause();
        pauseBtn.setDisable(true);
        playBtn.setDisable(false);
    }
    @FXML
    public void resetPressedBtn(ActionEvent e){
        Main.monitor.reset();
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }
}
