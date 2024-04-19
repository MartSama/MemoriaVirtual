package martsama.memoriavirtual;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private VBox proc;

    @FXML
    private VBox ram;

    @FXML
    protected void onHelloButtonClick() {
        //Logica que anima
        Node label = proc.getChildren().get(0);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), label);
        label.setStyle("-fx-background-color: white");
        label.setScaleX(2);
        label.setScaleY(2);
        transition.play();
        transition.setOnFinished(event -> {
            ram.getChildren().add(label);
            label.setStyle("-fx-background-color: black");
            label.setScaleX(1);
            label.setScaleY(1);
        });
    }



    //Arrays para contener variables

    List<Proceso> procesador = new ArrayList<>();
    List<Proceso> memoriaRam = new ArrayList<>();
    List<Proceso> memoriaCache = new ArrayList<>();
    List<Proceso> memoriaVirtual = new ArrayList<>();
    List<Proceso> intermedio = new ArrayList<>();


    //Variables
    int inicio = 0;
    int end = 5;


    //

}