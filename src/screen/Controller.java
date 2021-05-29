package screen;

import animation.*;
import cls.Cylinder;
import animation.*;
import cls.Cube;
import cls.Cylinder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
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
//Upload an image to customize your repository’s social media preview.


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
    public ImageView imageCylinder;

    // Image of the object on the
    @FXML
    private ImageView imageOnRoad;

    // Pane that contains Cylinder
    @FXML
    private Pane paneCylinder;

    // Pane that contains Cube
    @FXML
    private Pane paneCube;

    // Label that show hint when putting image on the road
    @FXML
    private Label lbDropOnRoad;

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
    private Label frictionForceLabel;
    @FXML
    private Label totalForceLabel;


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
                "Actor Force: " + monitor.getActorForce().getValue() + " N",
                monitor.getActorForce().getValueProperty());
        forceValueLabel.textProperty().bind(formattedForceValue);
        //bind force value with label
        ObservableStringValue formattedTotal = Bindings.createStringBinding(()->
                        "Total Force: " + monitor.getTotalForce().getValue() + " N",
                monitor.getTotalForce().getValueProperty());
        totalForceLabel.textProperty().bind(formattedTotal);
        //bind force value with label
        ObservableStringValue formattedFriction = Bindings.createStringBinding(()->
                        "Friction Force: " + monitor.getFrictionForce().getValue() + " N",
                monitor.getFrictionForce().getValueProperty());
        frictionForceLabel.textProperty().bind(formattedFriction);


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

        //lbDropOnRoad.setVisible(false);
        if (monitor.getObj() instanceof Cylinder) {
            Cylinder c = (Cylinder) monitor.getObj();
            c.getAngleProperty().addListener(observable -> imageCylinder.setRotate(c.getAngle()));
        }

        //animate actor
        ActorAnimation actorAnimation = new ActorAnimation(standActor,leftActor,rightActor,Main.monitor,
                new int[]{2, 118, 70, 2, 15},
                new int[]{2, 118, 70, 3, 15});

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

        // Initially, there's no object in the road
        lbDropOnRoad.setVisible(false);
        imageOnRoad.setVisible(false);
        // Set default size is half as large as MAXIMUM_THRES. Change size via a slider.
        sliderSize.setMin(5);
        sliderSize.setMax(7);
        sliderSize.setValue(5);
        sliderSize.setBlockIncrement(1);
        sliderSize.setOnMouseDragged(mouseEvent -> {
            float scale = (float) sliderSize.getValue();
            System.out.println("Scale" + scale);
            imageOnRoad.setScaleX(scale/5);
            imageOnRoad.setScaleY(scale/5);
            imageOnRoad.setLayoutY(90 + 145 - imageOnRoad.getFitHeight());
            imageOnRoad.setLayoutX(510 + 145/2 - imageOnRoad.getFitWidth()/2);
        });
        // User input the mass of the object via a text field.
        tfMass.setOnAction(actionEvent -> {
            try {
                monitor.getObj().setMass(Float.parseFloat(tfMass.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //animate surface
        SurfaceAnimation surfaceAnimation = new SurfaceAnimation(road, displayPane, Main.monitor, background);
        surfaceAnimation.start();
        //add arrows listeners
        //forceSlider.valueProperty().addListener((observableValue, number, t1) -> {
          //  monitor.getActorForce().setValue(t1.floatValue());});
        //display arrows
        AnimationArrow actorArrowAnimation = new AnimationArrow(monitor.getActorForce(), actorLeftArrow, actorRightArrow);
        AnimationArrow fricArrowAnimation = new AnimationArrow(monitor.getFrictionForce(), fricLeftArrow, fricRightArrow);
        AnimationArrow totalArrowAnimation = new AnimationArrow(monitor.getTotalForce(), totalForceLeftArrow, totalForceRightArrow);

        //arrowAnimation.start();
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

    // Variable control if in object is successfully dropped on to the road
    boolean dropToRoadSucceed;

    // Handle event: an image is pressed by mouse
//    @FXML
//    public void onMousePressed(MouseEvent mouseEvent){
//        System.out.println("Pressed");
//        ImageView obj = (ImageView) mouseEvent.getSource();
//        mouseEvent.consume();
//    }

    // Handle event: an image is dragged
    @FXML
    public void onDragDetected(MouseEvent mouseEvent){
        System.out.println("DragDetected");
        dropToRoadSucceed = false;
        ImageView obj = (ImageView) mouseEvent.getSource();
        obj.startFullDrag();

        mouseEvent.consume();
    }

    // Handle event: an image is BEING dragged
    @FXML
    public synchronized void onMouseDragged(MouseEvent mouseEvent){
        System.out.println("MouseDragged");
        lbDropOnRoad.setVisible(true);
        ImageView obj = (ImageView) mouseEvent.getSource();

        imageCylinder.setVisible(false);
        imageCube.setVisible(false);
        imageOnRoad.setVisible(false);

        mouseEvent.consume();
    }

    // Handle event: a dragging mouse entered above a pane.
//    @FXML
//    public void onMouseDragEntered(MouseDragEvent mouseDragEvent) {
//        System.out.println("MouseDragEntered");
//        mouseDragEvent.consume();
//    }

    // Handle event: a image is released from being dragged by the mouse
    @FXML
    public void onMouseReleased(MouseEvent mouseEvent) {
        System.out.println("MouseReleased");
        lbDropOnRoad.setVisible(false);
        if (dropToRoadSucceed) {
            imageOnRoad.setVisible(true);
            imageCube.setVisible(false);
            imageCylinder.setVisible(false);
        }
        else {
            imageCube.setVisible(true);
            imageCylinder.setVisible(true);
            imageOnRoad.setVisible(false);
        }
        mouseEvent.consume();
    }

    // Handle event: a dragging mouse is hovering above a pane
//    @FXML
//    public void onMouseDragOver(MouseDragEvent mouseDragEvent) {
//        if (mouseDragEvent.getTarget() == displayPane || mouseDragEvent.getTarget() == paneCube || mouseDragEvent.getTarget() == paneCylinder) {
//            System.out.println("MouseDragOver");
//        }
//        mouseDragEvent.consume();
//    }

    // Handle event: the mouse dropped an image on to a pane
    @FXML
    public void onMouseDragReleased(MouseDragEvent mouseDragEvent) throws Exception {
        System.out.println("MouseDragReleased");
        if (mouseDragEvent.getTarget() == displayPane) {
            dropToRoadSucceed = true;
            imageOnRoad.setImage(((ImageView) mouseDragEvent.getGestureSource()).getImage());
            if (mouseDragEvent.getGestureSource() == imageCylinder) {
                Cylinder c = new Cylinder(20, 0.5f);
                monitor.setObj(c);
                c.getAngleProperty().addListener(observable -> imageOnRoad.setRotate(c.getAngle())); // set image on the road as rotatable
            }
            else if (mouseDragEvent.getGestureSource() == imageCube) {
                monitor.setObj(new Cube(20, 0.5f));
            }
        }
        mouseDragEvent.consume();
    }

    // Handle event: Dragging process is done on an image
//    @FXML
//    public void onDragDone(DragEvent dragEvent){
//        System.out.println("DragDone");
//        dragEvent.consume();
//    }
}
