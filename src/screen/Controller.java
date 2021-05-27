package screen;

import cls.Force;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.control.*;

import javax.swing.*;
import java.util.Objects;

public class Controller {
    @FXML
    private Pane displayPane;

    @FXML
    private Group road;

    @FXML
    private ImageView standActor;
    @FXML
    private ImageView actor;

    private final Transition actorTransition = new Transition(){
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
            int index = Math.min((int) Math.floor(k*2),1);
            int x = (index%2)*width + offsetX;
            int y = (index/2)*height + offsetY;
            actor.setViewport(new Rectangle2D(x,y,width,height));
        }
    };
    @FXML
    private ImageView subroad1;
    @FXML
    private ImageView subroad2;

    // Slider to change edge (radius) size of object
    @FXML
    private JFXSlider sliderSize;

    // Text Input to change mass of object
    @FXML
    private TextField tfMass;

    // Image of Cube
    @FXML
    private ImageView imageCube;

    // Image of Cylinder
    @FXML
    private ImageView imageCylinder;

    // Drag and drop On-road pane
    @FXML
    private StackPane stackPaneOnRoad;

    // Drag and drop Cylinder pane
    @FXML
    private StackPane stackPaneCylinder;

    // Drag and drop Cube pane
    @FXML
    private StackPane stackPaneCube;

    @FXML
    private void initialize(){
        //setup resources
        actor.setImage(new Image(getClass().getResourceAsStream("/resources/actor.png")));
        standActor.setImage(new Image(getClass().getResourceAsStream("/resources/standActor.png")));
        subroad1.setImage(new Image(getClass().getResourceAsStream("/resources/surface.png")));
        subroad2.setImage(new Image(getClass().getResourceAsStream("/resources/surface.png")));
        //play
        playPressedBtn(new ActionEvent());
        //clipping pane
        Rectangle outputClip = new Rectangle();
        displayPane.setClip(outputClip);
        displayPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });


        // Lap's work
        // Add event handler for drag and drop
        stackPaneOnRoad.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                mouseDragOver(dragEvent);
            }
        });
        stackPaneCylinder.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                mouseDragOver(dragEvent);
            }
        });
        stackPaneCube.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                mouseDragOver(dragEvent);
            }
        });
        stackPaneOnRoad.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                mouseDragDropped(dragEvent);
            }
        });
        stackPaneCylinder.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                mouseDragDropped(dragEvent);
            }
        });
        stackPaneCube.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                mouseDragDropped(dragEvent);
            }
        });
        stackPaneOnRoad.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                mouseDragExited(dragEvent);
            }
        });
        stackPaneCylinder.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                mouseDragExited(dragEvent);
            }
        });
        stackPaneCube.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                mouseDragExited(dragEvent);
            }
        });





        //animate actor
        actorTransition.play();

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
    public void playPressedBtn(ActionEvent e){
        Main.monitor.cont();
        actorTransition.play();
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }
    @FXML
    public void pausePressedBtn(ActionEvent e){
        Main.monitor.pause();
        actorTransition.stop();
        pauseBtn.setDisable(true);
        playBtn.setDisable(false);
    }
    @FXML
    public void resetPressedBtn(ActionEvent e){
        Main.monitor.reset();
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }

    void mouseDragExited(DragEvent e) {
        System.out.println("DragExited");
    }

    void mouseDragOver(DragEvent e) {
        System.out.println("DragOver");
    }

    void mouseDragDropped(DragEvent e) {
        System.out.println("DragDropped");
    }
}
