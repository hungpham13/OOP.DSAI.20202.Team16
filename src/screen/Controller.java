package screen;

import animation.SpriteTransition;
import animation.SurfaceAnimation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
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
    private ImageView rightArrow;

    @FXML
    private ImageView leftArrow;

    @FXML
    private ImageView totalForceArrow;

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
        SpriteTransition actorTransition = new SpriteTransition(actor,250,2,118,70,2,15,Main.monitor);
        actorTransition.play();

        //add listener to force slider
        forceSlider.valueProperty().addListener((observableValue, number, t1) -> {
            Main.monitor.getActorForce().setValue(t1.floatValue());
            if (t1.intValue() == 0){
                standActor.setVisible(true);
                actor.setVisible(false);
            } else if (t1.intValue() != 0){
                actor.setVisible(true);
                standActor.setVisible(false);
            }
        });

        //animate surface
        SurfaceAnimation surfaceAnimation = new SurfaceAnimation(road, displayPane, Main.monitor);
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
        //forceSlider.setValue(0);
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