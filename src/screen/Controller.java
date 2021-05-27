package screen;

import animation.SpriteTransition;
import animation.SurfaceAnimation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

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
    private final SpriteTransition actorTransition = new SpriteTransition(actor,250,2,118,70,2,15);

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

        //add listener to force slider
        forceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Main.monitor.getActorForce().setValue(t1.floatValue());
            }
        });

        //animate surface
        new SurfaceAnimation(road, displayPane, Main.monitor);
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