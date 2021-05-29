package screen;

import animation.*;
import cls.Cube;
import cls.Cylinder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;

import static screen.Main.monitor;

public class Controller {

    private double prevItemX;
    private double prevItemY;
    private double prevMouseX;
    private double prevMouseY;

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
    public ImageView imageCylinder;

    // Image of the object on the
    @FXML
    private ImageView imageOnRoad;

    @FXML
    private ImageView supportImage;

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

        //animate actor
        ActorAnimation actorAnimation = new ActorAnimation(standActor,leftActor,rightActor,Main.monitor,
                new int[]{2, 118, 70, 2, 15},
                new int[]{2, 118, 70, 3, 15});

        //add listener to force slider
        forceSlider.valueProperty().addListener((observableValue, number, t1) -> {
            Main.monitor.getActorForce().setValue(t1.floatValue());
            actorAnimation.update();
        });

        // Initially, there's no object in the road
        lbDropOnRoad.setVisible(false);
        imageOnRoad.setVisible(false);
        // Set default size is half as large as MAXIMUM_THRES. Change size via a slider.
        sliderSize.setMin(0);
        sliderSize.setMax(1);
        sliderSize.setValue(0.5);
        sliderSize.setBlockIncrement(0.1);
        sliderSize.setOnMouseDragged(mouseEvent -> {
            float scale = (float) sliderSize.getValue();
            System.out.println("Scale" + scale);
            imageOnRoad.setScaleX(scale/0.5);
            imageOnRoad.setScaleY(scale/0.5);
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
        SurfaceAnimation surfaceAnimation = new SurfaceAnimation(road, displayPane, Main.monitor, background,0.1F);
        surfaceAnimation.start();
        //add arrows listeners
        //forceSlider.valueProperty().addListener((observableValue, number, t1) -> {
          //  monitor.getActorForce().setValue(t1.floatValue());});
        //display arrows
        ArrowAnimation arrowAnimation = new ArrowAnimation(monitor, actorLeftArrow, actorRightArrow, fricLeftArrow, fricRightArrow, totalForceLeftArrow, totalForceRightArrow);
        arrowAnimation.start();
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
    @FXML
    public void onMousePressed(MouseEvent mouseEvent){
        System.out.println("Pressed");
        ImageView obj = (ImageView) mouseEvent.getSource();
//        prevItemX = obj.getLayoutX();prevItemY = obj.getLayoutY();prevMouseX = mouseEvent.getX();prevMouseY = mouseEvent.getY();
        mouseEvent.consume();
    }

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

//        double diffX = mouseEvent.getX() - prevMouseX;double diffY = mouseEvent.getY() - prevMouseY;obj.setTranslateX(obj.getTranslateX() + diffX);obj.setTranslateY(obj.getTranslateY() + diffY);

        mouseEvent.consume();
    }

    // Handle event: a dragging mouse entered above a pane.
    @FXML
    public void onMouseDragEntered(MouseDragEvent mouseDragEvent) {
        System.out.println("MouseDragEntered");
        mouseDragEvent.consume();
    }

    // Handle event: a image is released from being dragged by the mouse
    @FXML
    public void onMouseReleased(MouseEvent mouseEvent) {
        System.out.println("MouseRelease");
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
    @FXML
    public void onMouseDragOver(MouseDragEvent mouseDragEvent) {
        if (mouseDragEvent.getTarget() == displayPane || mouseDragEvent.getTarget() == paneCube || mouseDragEvent.getTarget() == paneCylinder) {
            System.out.println("MouseDragOver");
        }
        mouseDragEvent.consume();
    }

    // Handle event: the mouse dropped an image on to a pane
    @FXML
    public void onMouseDragReleased(MouseDragEvent mouseDragEvent) throws Exception {
        System.out.println("MouseDragReleased");
        System.out.println(mouseDragEvent.getGestureSource());
        lbDropOnRoad.setVisible(false);
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
    @FXML
    public void onDragDone(DragEvent dragEvent){
        System.out.println("DragDone");
        dragEvent.consume();
    }

    @FXML
    public void setForceOnDrop() {

    }
}
