package screen;

import animation.*;
import base.Cylinder;
import base.Cube;
import base.Object;
import com.jfoenix.controls.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;

import javax.swing.*;

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
    private JFXTextArea tfMass;

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
    private Label massLabel;

    @FXML
    private JFXCheckBox massCbox;
    @FXML
    private JFXCheckBox veloCbox;
    @FXML
    private JFXCheckBox acceCbox;
    @FXML
    private JFXCheckBox arrowForceCbox;
    @FXML
    private JFXCheckBox valueForceCbox;
    @FXML
    private JFXCheckBox sumForceCbox;

    @FXML
    private void initialize() throws Exception {

        //reset all to init
        resetPressedBtn(new ActionEvent());

        //clipping pane
        Rectangle outputClip = new Rectangle();
        displayPane.setClip(outputClip);
        displayPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });

        //bind force value with label
        ObservableStringValue formattedForceValue = Bindings.createStringBinding(() ->
                        "Actor Force: " + monitor.getActorForce().getValue() + " N",
                monitor.getActorForce().getValueProperty());
        forceValueLabel.textProperty().bind(formattedForceValue);
        forceValueLabel.visibleProperty().bindBidirectional(valueForceCbox.selectedProperty());
        //bind force value with label
        ObservableStringValue formattedTotal = Bindings.createStringBinding(() ->
                        "Total Force: " + monitor.getTotalForce().getValue() + " N",
                monitor.getTotalForce().getValueProperty());
        totalForceLabel.textProperty().bind(formattedTotal);

        ObservableBooleanValue isTotal = Bindings.createBooleanBinding(() ->
                        valueForceCbox.isSelected() && sumForceCbox.isSelected(),
        valueForceCbox.selectedProperty(),sumForceCbox.selectedProperty());
        totalForceLabel.visibleProperty().bind(isTotal);

        //bind force value with label
        ObservableStringValue formattedFriction = Bindings.createStringBinding(() ->
                        "Friction Force: " + monitor.getFrictionForce().getValue() + " N",
                monitor.getFrictionForce().getValueProperty());
        frictionForceLabel.textProperty().bind(formattedFriction);
        frictionForceLabel.visibleProperty().bindBidirectional(valueForceCbox.selectedProperty());

        monitor.getObjList().addListener((ListChangeListener<Object>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    forceSlider.setDisable(false);
                    acceCbox.setDisable(false);
                    veloCbox.setDisable(false);
                    sliderSize.setValue(5);
                    imageOnRoad.setLayoutY(90);
                    massCbox.setDisable(false);
                    try {
                        //bind mass with tfMass and masslabel
                        tfMass.setText(String.valueOf(monitor.getObj().getMass()));
                        ObservableStringValue formattedMass = Bindings.createStringBinding(() ->
                                        monitor.getObj().getMass() + " kg",
                                monitor.getObj().getMassProperty());
                        massLabel.textProperty().bind(formattedMass);
                        massLabel.visibleProperty().bindBidirectional(massCbox.selectedProperty());

                        //bind acceleration with label
                        ObservableStringValue formattedAcceleration = Bindings.createStringBinding(() ->
                                        "Acceleration: " + String.format("%.2g",monitor.getObjAcceleration()) + " m/s2",
                                monitor.getObj().getMassProperty(), monitor.getActorForce().getValueProperty());
                        accelerationLabel.textProperty().bind(formattedAcceleration);
                        accelerationLabel.visibleProperty().bindBidirectional(acceCbox.selectedProperty());

                        //bind velocity with label
                        ObservableStringValue formattedVelocity = Bindings.createStringBinding(() ->
                                        "Velocity: " + String.format("%.2g",monitor.getObj().getVelocity()) + " m/s",
                                monitor.getObj().getVelocityProperty());
                        velocityLabel.textProperty().bind(formattedVelocity);
                        velocityLabel.visibleProperty().bindBidirectional(veloCbox.selectedProperty());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    velocityLabel.setVisible(false);
                    accelerationLabel.setVisible(false);
                    massLabel.setVisible(false);
                    veloCbox.setDisable(true);
                    massCbox.setDisable(true);
                    acceCbox.setDisable(true);
                    forceSlider.setDisable(true);
                }
            }
        });


        //animate actor
        ActorAnimation actorAnimation = new ActorAnimation(standActor, leftActor, rightActor, monitor,imageOnRoad,
                new int[]{2, 118, 70, 2, 15},
                new int[]{2, 118, 70, 3, 15});

        //add listener to force slider
        forceSlider.valueProperty().bindBidirectional(monitor.getActorForce().getValueProperty());
        //add listener to surface coefficient slider
        kineticSlider.valueProperty().bindBidirectional(monitor.getSurface().getKineticCoefProperty());
        staticSlider.valueProperty().bindBidirectional(monitor.getSurface().getStaticCoefProperty());
        kineticSlider.maxProperty().bind(staticSlider.valueProperty());
        kineticSlider.majorTickUnitProperty().bind(Bindings.createDoubleBinding(()->kineticSlider.getMax()/2,
                kineticSlider.maxProperty()));

        // Set default size is half as large as MAXIMUM_THRES. Change size via a slider.
        sliderSize.setMin(5);
        sliderSize.setMax(7);
        sliderSize.setValue(5);
        sliderSize.setBlockIncrement(1);
        sliderSize.valueProperty().addListener((observableValue, t, t1) -> {
            float oldScale = t.floatValue();
            float scale = t1.floatValue();
            System.out.println("Scale" + scale);
            imageOnRoad.setLayoutY(imageOnRoad.getLayoutY() + imageOnRoad.getFitHeight()*(oldScale-scale)/10);
            imageOnRoad.setScaleX(scale / 5);
            imageOnRoad.setScaleY(scale / 5);
        });
        // User input the mass of the object via a text field.
        tfMass.textProperty().addListener((observableValue, s, t1) -> {
            try {
                monitor.getObj().setMass(Float.parseFloat(t1));
            } catch (Exception e) {
                pausePressedBtn(new ActionEvent());
                JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        });

        //animate surface
        SurfaceAnimation surfaceAnimation = new SurfaceAnimation(road, displayPane, Main.monitor, background);
        surfaceAnimation.start();
        //add arrows listeners
        //forceSlider.valueProperty().addListener((observableValue, number, t1) -> {
        //  monitor.getActorForce().setValue(t1.floatValue());});
        //display arrows
        AnimationArrow actorArrowAnimation = new AnimationArrow(monitor.getActorForce(), actorLeftArrow,
                actorRightArrow,imageOnRoad, arrowForceCbox.selectedProperty());
        AnimationArrow fricArrowAnimation = new AnimationArrow(monitor.getFrictionForce(), fricLeftArrow,
                fricRightArrow, imageOnRoad, arrowForceCbox.selectedProperty());
        AnimationArrow totalArrowAnimation = new AnimationArrow(monitor.getTotalForce(), totalForceLeftArrow,
                totalForceRightArrow, imageOnRoad, sumForceCbox.selectedProperty());
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
    public void resetPressedBtn(ActionEvent e) throws Exception {
        Main.monitor.reset();
        forceSlider.setValue(0);
        imageOnRoad.setRotate(0);
        imageOnRoad.setScaleX(1);
        imageOnRoad.setScaleY(1);
        imageOnRoad.setVisible(false);
        imageCube.setVisible(true);
        imageCylinder.setVisible(true);

        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }

    // Variable control if in object is successfully dropped on to the road
    boolean dropToRoadSucceed;
    @FXML
    private Pane dragRecognizePane;

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
        dragRecognizePane.setVisible(true);

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
    public void onMouseReleased(MouseEvent mouseEvent) throws Exception {
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
            resetPressedBtn(new ActionEvent());
        }
        dragRecognizePane.setVisible(false);
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
        if (mouseDragEvent.getTarget() == dragRecognizePane) {
            dropToRoadSucceed = true;
            imageOnRoad.setImage(((ImageView) mouseDragEvent.getGestureSource()).getImage());
            if (mouseDragEvent.getGestureSource() == imageCylinder) {
                Cylinder c = new Cylinder(20, 0.5f);
                monitor.setObj(c);
                c.getAngleProperty().addListener(observable -> imageOnRoad.setRotate(2 * c.getAngle())); // set image on
                // the road as rotatable
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
