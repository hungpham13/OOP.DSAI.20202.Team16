package screen;

import animation.*;
import cls.Cylinder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
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

    @FXML
    private ImageView imageOnRoad;

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

    @FXML
    public void onMousePressed(MouseEvent mouseEvent){
        System.out.println("Pressed");
        ImageView obj = (ImageView) mouseEvent.getSource();
        prevItemX = obj.getLayoutX();
        prevItemY = obj.getLayoutY();
        prevMouseX = mouseEvent.getX();
        prevMouseY = mouseEvent.getY();
    }
    @FXML
    public void onMouseExited(MouseEvent mouseEvent) {
        System.out.println("MouseExited");
        lbDropOnRoad.setVisible(false);
        if (mouseEvent.getSource() == imageCylinder) {
            imageCube.setVisible(true);
        }
        else {
            imageCylinder.setVisible(true);
        }
        // What if we drop the imageView in the road? We must hide cube and cylinder, not visible
    }
    @FXML
    public void onDragDetected(MouseEvent mouseEvent){
        // If the ImageView is caught dragged, we copy its image to clipboard for later transfer.
        System.out.println("DragDetected");
        ImageView obj = (ImageView) mouseEvent.getSource();
        Dragboard db = obj.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putImage(obj.getImage());
        db.setDragView(obj.getImage());
        db.setContent(content);
        System.out.println("OKKKKKKKKK");
        System.out.println("DBBBBBBBBBBB: " + db.hasImage());
        mouseEvent.consume();
    }
    @FXML
    public void onMouseDragged(MouseEvent mouseEvent){
//        System.out.println("MouseDragged");
        lbDropOnRoad.setVisible(true);
        ImageView obj = (ImageView) mouseEvent.getSource();
        if (obj == imageCylinder) {
            // if we are dragging cylinder, we hide cube
            imageCube.setVisible(false);
        }
        else if (obj == imageCube) {
            // if we are dragging cube, we hide cylinder
            imageCylinder.setVisible(false);
        } else if (obj == imageOnRoad) {
            // If we are dragging on-road object, we hide both cube and cylinder. Actually we don' need to code anything
        }
        double diffX = mouseEvent.getX() - prevMouseX;
        double diffY = mouseEvent.getY() - prevMouseY;
        obj.setTranslateX(obj.getTranslateX() + diffX);
        obj.setTranslateY(obj.getTranslateY() + diffY);
    }

    @FXML
    public void onDragEntered(DragEvent dragEvent) {
        System.out.println("DragEntered");
        dragEvent.consume();
    }
    @FXML
    public void onDragExited(DragEvent dragEvent) {
        System.out.println("DragExited");
        dragEvent.consume();
    }

    @FXML
    public void onDragOver(DragEvent dragEvent) {
        System.out.println("DragOver");
        if (dragEvent.getSource() == imageCylinder || dragEvent.getSource() == imageCube) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
        dragEvent.consume();
    }

    @FXML
    public void onDragDropped(DragEvent dragEvent) {
        System.out.println("DragDropped");
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (dragEvent.getTarget() == imageOnRoad) {
            // If we're dropping on to road, set the dragged obj's image as on-road image
            imageOnRoad.setImage(db.getImage());
            success = true;
        }
        else {
            imageCylinder.setImage(new Image("/resources/cylinder.png"));
            imageCube.setImage(new Image("/resources/cube.jpg"));
            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    @FXML
    public void onDragDone(DragEvent dragEvent){
        System.out.println("DragDone");
        // TODO Clear image from source
    }

    @FXML
    public void setForceOnDrop() {

    }
}
