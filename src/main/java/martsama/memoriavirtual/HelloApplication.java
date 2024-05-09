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
        //Se manda a llamar el archivo XML que hace el render de la ventana
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        //Se pone un titulo a la ventana
        stage.setTitle("Memoria Virtual");
        stage.setScene(scene);
        //Se muestra la ventana
        stage.show();
        //Esta parte hace que nuestra ventana este siempre hast arriba, asi la podemos ver mas facil
        stage.setAlwaysOnTop(true);

    }

    public static void main(String[] args) {
        launch();
    }
}