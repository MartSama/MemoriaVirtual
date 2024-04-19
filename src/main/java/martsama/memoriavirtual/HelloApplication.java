package martsama.memoriavirtual;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class HelloApplication extends Application {
    @FXML
    private VBox proc;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 350);
        stage.setTitle("Memoria Virtaul");
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);
        /*Label label = new Label("Mi Label");
        label.setStyle("-fx-text-fill: white;");

        // Creamos una Timeline que se ejecutará durante 5 segundos
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> label.setStyle("-fx-background-color: rgba(0, 51, 0, 1);")), // deep green
                new KeyFrame(Duration.seconds(1), event -> label.setStyle("-fx-background-color: rgba(0, 51, 0, 0.75);")), // medium green
                new KeyFrame(Duration.seconds(2), event -> label.setStyle("-fx-background-color: rgba(0, 51, 0, 0.5);")), // light green
                new KeyFrame(Duration.seconds(3), event -> label.setStyle("-fx-background-color: rgba(0, 51, 0, 0.25);")), // lighter green
                new KeyFrame(Duration.seconds(4), event -> label.setStyle("-fx-background-color: rgba(0, 51, 0, 0);")) // fully transparent
        );
        timeline.setCycleCount(1); // Ejecutar la Timeline solo una vez

        // Mostramos la ventana
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 200, 100);
        stage.setScene(scene);
        stage.setTitle("Ejemplo VBox con cambio de altura");
        stage.show();

        // Iniciamos la Timeline
        timeline.play();*/
    }

    public static void main(String[] args) {
        launch();
    }
}