package screen;

import animation.ArrowAnimation;
import animation.SpriteTransition;
import animation.SurfaceAnimation;
import cls.Force;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.util.Observable;

import static screen.Main.monitor;

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

    @FXML
    private ImageView subroad1;
    @FXML
    private ImageView subroad2;

    @FXML
    private ImageView actorRightArrow;

    @FXML
    private ImageView actorLeftArrow;

    @FXML
    private ImageView fricRightArrow;

    @FXML
    private ImageView fricLeftArrow;

    @FXML
    private ImageView totalForceLeftArrow;

    @FXML
    private ImageView totalForceRightArrow;

    @FXML
    private void initialize() {
        //setup resources
        actor.setImage(new Image(getClass().getResourceAsStream("/resources/actor.png")));
        standActor.setImage(new Image(getClass().getResourceAsStream("/resources/standActor.png")));
        subroad1.setImage(new Image(getClass().getResourceAsStream("/resources/surface.png")));
        subroad2.setImage(new Image(getClass().getResourceAsStream("/resources/surface.png")));
        actorRightArrow.setImage((new Image(getClass().getResourceAsStream("/resources/actorArrow.png"))));
        actorLeftArrow.setImage((new Image(getClass().getResourceAsStream("/resources/actorArrow.png"))));
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
        SpriteTransition actorTransition = new SpriteTransition(actor,250,2,118,70,2,15, monitor);
        actorTransition.play();

        //add listener to force slider
        forceSlider.valueProperty().addListener((observableValue, number, t1) -> {
            monitor.getActorForce().setValue(t1.floatValue());
            if (t1.intValue() == 0){
                standActor.setVisible(true);
                actor.setVisible(false);
            } else if (t1.intValue() != 0){
                actor.setVisible(true);
                standActor.setVisible(false);
            }
        });

        //animate surface
        SurfaceAnimation surfaceAnimation = new SurfaceAnimation(road, displayPane, monitor);
        surfaceAnimation.start();
        //add arrows listeners
        //forceSlider.valueProperty().addListener((observableValue, number, t1) -> {
          //  monitor.getActorForce().setValue(t1.floatValue());});
        //display arrows
        ArrowAnimation arrowAnimation = new ArrowAnimation(monitor, actorLeftArrow, actorRightArrow);
        arrowAnimation.start();
        }
    @FXML
    private JFXButton playBtn;
    @FXML
    private JFXButton pauseBtn;

    @FXML
    public void playPressedBtn(ActionEvent e) {
        monitor.cont();
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }

    @FXML
    public void pausePressedBtn(ActionEvent e) {
        monitor.pause();
        pauseBtn.setDisable(true);
        playBtn.setDisable(false);
    }

    @FXML
    public void resetPressedBtn(ActionEvent e) {
        monitor.reset();
        //forceSlider.setValue(0);
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }

}