package screen;

import cls.Cube;
import cls.Force;
import cls.Monitor;
import cls.Surface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public static Monitor monitor;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        primaryStage.setTitle("Hello Dinosaur");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        monitor = new Monitor(new Cube(12,0.5f), new Surface(0.2f, 0.3f), new Force(0));
        launch(args);
    }
}
