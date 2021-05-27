package screen;

import animation.SpriteTransition;
import animation.SurfaceAnimation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
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
    private JFXSlider forceSlider;

    @FXML
    private Group road;

    @FXML
    private Group background;

    @FXML
    private ImageView standActor;
    @FXML
    private ImageView leftActor;
    @FXML
    private ImageView rightActor;

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
    private void initialize() {

        //reset all to init
        resetPressedBtn(new ActionEvent());

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
        SpriteTransition leftActorTransition = new SpriteTransition(leftActor,250,2,118,70,2,15,Main.monitor);
        SpriteTransition rightActorTransition = new SpriteTransition(rightActor,250,2,118,70,3,15,Main.monitor);
        rightActorTransition.play();
        leftActorTransition.play();

        //add listener to force slider
        forceSlider.valueProperty().addListener((observableValue, number, t1) -> {
            Main.monitor.getActorForce().setValue(t1.floatValue());
            if (t1.intValue() == 0){
                standActor.setVisible(true);
                leftActor.setVisible(false);
                rightActor.setVisible(false);
            } else if (t1.intValue() > 0){
                leftActor.setVisible(true);
                standActor.setVisible(false);
                rightActor.setVisible(false);
            } else if (t1.intValue() < 0){
                rightActor.setVisible(true);
                leftActor.setVisible(false);
                standActor.setVisible(false);
            }
        });

        //animate surface
        SurfaceAnimation surfaceAnimation = new SurfaceAnimation(road, displayPane, Main.monitor, 1F);
        SurfaceAnimation backgroundAnimation = new SurfaceAnimation(background, displayPane, Main.monitor, 0.1F);
        backgroundAnimation.start();
        surfaceAnimation.start();
    }

    @FXML
    private JFXButton playBtn;
    @FXML
    private JFXButton pauseBtn;

    @FXML
    public void playPressedBtn(ActionEvent e) {
        Main.monitor.cont();
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }

    @FXML
    public void pausePressedBtn(ActionEvent e) {
        Main.monitor.pause();
        pauseBtn.setDisable(true);
        playBtn.setDisable(false);
    }

    @FXML
    public void resetPressedBtn(ActionEvent e) {
        Main.monitor.reset();
        forceSlider.setValue(0);
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
