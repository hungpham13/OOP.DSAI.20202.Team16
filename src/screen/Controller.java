package screen;

import animation.SpriteTransition;
import animation.SurfaceAnimation;
import cls.Object;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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


        lbDropOnRoad.setVisible(false);


        //animate actor
        SpriteTransition leftActorTransition = new SpriteTransition(leftActor,200,2,118,70,2,15,Main.monitor);
        SpriteTransition rightActorTransition = new SpriteTransition(rightActor,200,2,118,70,3,15,Main.monitor);
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
    public void setForceOnDrop(DragEvent event) {
    //forceSlider.valueProperty().addListener(new ChangeListener<Number>() {
    //@Override
    //public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
    //    Main.monitor.getActorForce().setValue(forceSlider.valueProperty().floatValue());
    //      Main.monitor.getObj().applyForce(Main.monitor.getActorForce(),(float) 0.03);
    //    }
    //  });
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
}
