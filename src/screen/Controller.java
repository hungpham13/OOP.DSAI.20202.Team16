package screen;

import animation.ActorAnimation;
import animation.ArrowAnimation;
import animation.SurfaceAnimation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;


import static screen.Main.monitor;

public class Controller {
    @FXML
    private Pane displayPane;

    @FXML
    private JFXSlider forceSlider;
    @FXML
    private JFXSlider kineticSlider;
    @FXML
    private JFXSlider staticSlider;


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
    private Label forceValueLabel;
    @FXML
    private Label accelerationLabel;
    @FXML
    private Label velocityLabel;


    @FXML
    private void initialize() {

        //reset all to init
        //resetPressedBtn(new ActionEvent());

        //clipping pane
        Rectangle outputClip = new Rectangle();
        displayPane.setClip(outputClip);
        displayPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });

        //bind force value with label
        ObservableStringValue formattedForceValue = Bindings.createStringBinding(()->
                "Force: " + monitor.getActorForce().getValue() + " N",
                monitor.getActorForce().getValueProperty());
        forceValueLabel.textProperty().bind(formattedForceValue);

        //bind acceleration with label
        ObservableStringValue formattedAcceleration = Bindings.createStringBinding(()->
                        "Acceleration: " + monitor.getObjAcceleration() + " m/s2",
                monitor.getObj().getMassProperty(),monitor.getActorForce().getValueProperty());
        accelerationLabel.textProperty().bind(formattedAcceleration);

        //bind velocity with label
        ObservableStringValue formattedVelocity = Bindings.createStringBinding(()->
                        "Velocity: " + monitor.getObj().getVelocity() + " m/s",
                monitor.getObj().getVelocityProperty());
        velocityLabel.textProperty().bind(formattedVelocity);

        //animate actor
        ActorAnimation actorAnimation = new ActorAnimation(standActor,leftActor,rightActor,Main.monitor,
                new int[]{2, 118, 70, 2, 15},
                new int[]{2, 118, 70, 3, 15});

        //animate arrows
        ArrowAnimation arrowAnimation = new ArrowAnimation(monitor, actorLeftArrow, actorRightArrow);

        //add listener to force slider
        forceSlider.valueProperty().addListener((observableValue, number, t1) -> {
            Main.monitor.getActorForce().setValue(t1.floatValue());
        });
        //add listener to surface coefficient slider
        kineticSlider.valueProperty().addListener((observableValue, number, t1) -> {
            try {
                Main.monitor.getSurface().setKineticFrictionCoef(t1.floatValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        staticSlider.valueProperty().addListener((observableValue, number, t1) -> {
            try {
                Main.monitor.getSurface().setStaticFrictionCoef(t1.floatValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //animate surface
        SurfaceAnimation surfaceAnimation = new SurfaceAnimation(road, displayPane, Main.monitor, background,0.1F);
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
    void mouseDragExited(DragEvent e) {
        System.out.println("DragExited");
    }

    @FXML
    void mouseDragOver(DragEvent e) {
        System.out.println("DragOver");
    }

    @FXML
    void mouseDragDropped(DragEvent e) {
        System.out.println("DragDropped");
    }

    @FXML
    void mouseDragDetected(DragEvent e) {
        System.out.println("DragDetected");
    }
}
