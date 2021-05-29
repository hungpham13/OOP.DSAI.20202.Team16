package screen;

import animation.*;
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

    @FXML
    private ImageView imageOnRoad;

    // Drag and drop Cylinder pane
    @FXML
    private Pane paneCylinder;

    // Drag and drop Cube pane
    @FXML
    private Pane paneCube;

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


        lbDropOnRoad.setVisible(false);
        imageOnRoad.setVisible(false);
        imageOnRoad.setOnRotate(new EventHandler<RotateEvent>() {
            @Override
            public void handle(RotateEvent rotateEvent) {
                if (imageOnRoad.getImage().getUrl().endsWith("cylinder.png")) {
                    imageOnRoad.setRotate(((Cylinder) monitor.getObj()).getAngularVelocity());
                }
            }
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

        CylinderTransition cylinderTransition = new CylinderTransition(monitor, imageCylinder);
        cylinderTransition.play();
        sliderSize.setMin(0);
        sliderSize.setMax(1);
        sliderSize.setBlockIncrement(0.01);
        sliderSize.setOnMouseDragged(mouseEvent -> {
            float size = (float) sliderSize.getValue();
            imageOnRoad.setScaleX(size);
            imageOnRoad.setScaleY(size);
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

    @FXML
    public void onMousePressed(MouseEvent mouseEvent){
        System.out.println("Pressed");
        ImageView obj = (ImageView) mouseEvent.getSource();
        prevItemX = obj.getLayoutX();
        prevItemY = obj.getLayoutY();
        prevMouseX = mouseEvent.getX();
        prevMouseY = mouseEvent.getY();
        mouseEvent.consume();
    }
    @FXML
    public void onMouseReleased(MouseEvent mouseEvent) {
        System.out.println("MouseReleased");
        lbDropOnRoad.setVisible(false);
//        if (mouseEvent.getSource() == imageCylinder) {
//            imageCube.setVisible(true);
//        }
//        else {
//            imageCylinder.setVisible(true);
//        }
        // What if we drop the imageView in the road? We must hide cube and cylinder, not visible
        mouseEvent.consume();
    }

    @FXML
    public void onDragDetected(MouseEvent mouseEvent){
        // If the ImageView is caught dragged, we copy its image to clipboard for later transfer.
        System.out.println("DragDetected");
        ImageView obj = (ImageView) mouseEvent.getSource();
        obj.startFullDrag();
        mouseEvent.consume();
    }
    @FXML
    public synchronized void onMouseDragged(MouseEvent mouseEvent){
//        System.out.println("MouseDragged");
        lbDropOnRoad.setVisible(true);
        ImageView obj = (ImageView) mouseEvent.getSource();
        if (obj == imageCylinder) {
            // if we are dragging cylinder, we hide cube
            imageCube.setVisible(false);
            imageOnRoad.setVisible(false);
        }
        else if (obj == imageCube) {
            // if we are dragging cube, we hide cylinder
            imageCylinder.setVisible(false);
            imageOnRoad.setVisible(false);
        } else if (obj == imageOnRoad) {
            // If we are dragging on-road object, we hide both cube and cylinder. Actually we don' need to code anything
            imageCube.setVisible(false);
            imageCylinder.setVisible(false);
        }
        double diffX = mouseEvent.getX() - prevMouseX;
        double diffY = mouseEvent.getY() - prevMouseY;
        obj.setTranslateX(obj.getTranslateX() + diffX);
        obj.setTranslateY(obj.getTranslateY() + diffY);
        mouseEvent.consume();
    }

    @FXML
    public void onMouseDragEntered(MouseDragEvent mouseDragEvent) {
        System.out.println("MouseDragEntered");
        mouseDragEvent.consume();
    }
    @FXML
    public void onMouseDragExited(MouseDragEvent mouseDragEvent) {
        System.out.println("MouseDragExited");
        mouseDragEvent.consume();
    }

    @FXML
    public void onMouseDragOver(MouseDragEvent mouseDragEvent) {
        // TODO
        //System.out.println("MouseDragOver");
        System.out.println(mouseDragEvent.getTarget() == imageCylinder);
        if (mouseDragEvent.getTarget() == displayPane || mouseDragEvent.getTarget() == paneCube || mouseDragEvent.getTarget() == imageCylinder) {
            System.out.println("MouseDragOver");
        }
        mouseDragEvent.consume();
    }
    @FXML
    public void onMouseDragReleased(MouseDragEvent mouseDragEvent) {
        System.out.println("MouseDragReleased");
        System.out.println(mouseDragEvent.getGestureSource());
        lbDropOnRoad.setVisible(false);
        if (mouseDragEvent.getTarget() == displayPane
//                && (mouseDragEvent.getGestureSource() == imageCylinder || mouseDragEvent.getGestureSource() == imageCube)
        ) {
            // if drop to road succeed
            imageOnRoad.setImage(((ImageView) mouseDragEvent.getGestureSource()).getImage());
            System.out.println(imageOnRoad.getImage().getUrl());
            imageCube.setVisible(false);
            imageCylinder.setVisible(false);
            imageOnRoad.setVisible(true);
        }
        else if ((mouseDragEvent.getTarget() == paneCylinder || mouseDragEvent.getTarget() == paneCube)
//                && (mouseDragEvent.getGestureSource() == imageOnRoad)
        ) {
            // if drop to box succeed
            System.out.println("Helloooooo");
            imageCylinder.setVisible(true);
            imageCube.setVisible(true);
        }
        else {
            // if drop not succeed
            // TODO recover to initial position
        }
        mouseDragEvent.consume();
    }



    @FXML
    public void onDragDone(DragEvent dragEvent){
        System.out.println("DragDone");
        dragEvent.consume();
    }

    @FXML
    public void setForceOnDrop() {

    }
}
