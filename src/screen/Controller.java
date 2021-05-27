package screen;

import cls.Force;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Objects;

public class Controller {
    @FXML
    private Pane displayPane;

    @FXML
    private JFXSlider forceSlider;

    @FXML
    private Group road;

    @FXML
    private ImageView standActor;
    @FXML
    private ImageView actor;
    private final Transition actorTransition = new Transition() {
        final int width = 118;
        final int height = 70;
        final int offsetX = 2;
        final int offsetY = 15;

        {
            setCycleDuration(Duration.millis(250));
            setCycleCount(Animation.INDEFINITE);
            setInterpolator(Interpolator.LINEAR);
        }

        @Override
        protected void interpolate(double k) {
            int index = Math.min((int) Math.floor(k * 2), 1);
            int x = (index % 2) * width + offsetX;
            int y = (index / 2) * height + offsetY;
            actor.setViewport(new Rectangle2D(x, y, width, height));
        }
    };
    @FXML
    private ImageView subroad1;
    @FXML
    private ImageView subroad2;

    @FXML
    private void initialize() {
        //setup resources
        actor.setImage(new Image(getClass().getResourceAsStream("/resources/actor.png")));
        standActor.setImage(new Image(getClass().getResourceAsStream("/resources/standActor.png")));
        subroad1.setImage(new Image(getClass().getResourceAsStream("/resources/surface.png")));
        subroad2.setImage(new Image(getClass().getResourceAsStream("/resources/surface.png")));
        forceSlider.setShowTickMarks(true);
        forceSlider.setShowTickLabels(true);
        forceSlider.minProperty().setValue(-100.0);
        forceSlider.maxProperty().setValue(100.0);

        //play
        playPressedBtn(new ActionEvent());
        //clipping pane
        Rectangle outputClip = new Rectangle();
        displayPane.setClip(outputClip);
        displayPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });


        //animate actor
        actorTransition.play();

        //animate surface
        final long[] startNanoTime = {System.nanoTime()};
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                //create infinity road
                if (road.getLayoutX() >= -30) {
                    road.setLayoutX(road.getLayoutX() - 1690);
                } else if (road.getLayoutX() <= displayPane.getWidth() + 30 - (1690 + 1722)) {
                    road.setLayoutX(road.getLayoutX() + 1690);
                }

                //move the road
                double t = (currentNanoTime - startNanoTime[0]) / 1000000000.0;
                if (Main.monitor.isPlaying()) {
                    //calculate new total applied force to object
                    forceSlider.valueProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                            Force actorForce = Main.monitor.getActorForce();
                            actorForce.setValue((forceSlider.valueProperty().floatValue()*(float)0.5));
                            Force totalForce = actorForce.plus(Main.monitor.getFrictionForce());
                            Main.monitor.getObj().applyForce(totalForce, (float) t);
                            //road.setLayoutX(road.getLayoutX() - t * Main.monitor.getObj().getVelocity());
                        }
                    });
                    //apply total force to object
                    //Main.monitor.getObj().applyForce(totalForce, (float) t);
                    //move road based on new velocity of object
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
    public void playPressedBtn(ActionEvent e) {
        Main.monitor.cont();
        actorTransition.play();
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }

    @FXML
    public void pausePressedBtn(ActionEvent e) {
        Main.monitor.pause();
        actorTransition.stop();
        pauseBtn.setDisable(true);
        playBtn.setDisable(false);
    }

    @FXML
    public void resetPressedBtn(ActionEvent e) {
        Main.monitor.reset();
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }
    @FXML
    public void setForceOnDrop(DragEvent event) {
    //forceSlider.valueProperty().addListener(new ChangeListener<Number>() {
    //@Override
    //public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
    //    Main.monitor.getActorForce().setValue(forceSlider.valueProperty().floatValue());
    //      Main.monitor.getObj().applyForce(Main.monitor.getActorForce(),(float) 0.03);
    //    }
    //  });
    }
}