package martsama.memoriavirtual;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private VBox proc;

    @FXML
    private VBox ram;

    @FXML
    private VBox cache;

    @FXML
    private FlowPane virtual;

    @FXML
    protected void moveAnimation(int index, Pane destination,Pane original, boolean duplicate) {
        //Logica que anima
        Node label = original.getChildren().get(index);
        if(duplicate){
            Label label0 = new Label();
            label0.setText(((Label) label).getText());
            label0.getStyleClass().add("custom-label");
            label0.setFont(((Label) label).getFont());
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), label);
            TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), label0);
            label.setStyle("-fx-background-color: white");
            label.setScaleX(2);
            label.setScaleY(2);
            transition.play();
            label0.setStyle("-fx-background-color: white");
            label0.setScaleX(2);
            label0.setScaleY(2);
            transition2.play();
            transition.setOnFinished(event -> {
                destination.getChildren().add(label);

                label.setStyle("-fx-background-color: black");
                label.setScaleX(1);
                label.setScaleY(1);
            });
            transition2.setOnFinished(event -> {
                proc.getChildren().add(label0);

                label0.setStyle("-fx-background-color: black");
                label0.setScaleX(1);
                label0.setScaleY(1);
            });
        }else{
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), label);
            label.setStyle("-fx-background-color: white");
            label.setScaleX(2);
            label.setScaleY(2);
            transition.play();
            transition.setOnFinished(event -> {
                destination.getChildren().add(label);
                label.setStyle("-fx-background-color: black");
                label.setScaleX(1);
                label.setScaleY(1);
            });
        }

    }

    protected void moveElements(int inicio, int end, Pane destination, Pane original, boolean duplicate){
        for (int i = inicio; i < end; i++){
            moveAnimation(i, destination, original, duplicate);
        }
    }
    protected void entradaSalidaAnimation(Node label){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> label.setStyle("-fx-background-color: rgba(0, 51, 0, 1);")), // deep green
                new KeyFrame(Duration.seconds(1), event -> label.setStyle("-fx-background-color: rgba(0, 51, 0, 0.75);")), // medium green
                new KeyFrame(Duration.seconds(2), event -> label.setStyle("-fx-background-color: rgba(0, 51, 0, 0.5);")), // light green
                new KeyFrame(Duration.seconds(3), event -> label.setStyle("-fx-background-color: rgba(0, 51, 0, 0.25);")), // lighter green
                new KeyFrame(Duration.seconds(4), event -> label.setStyle("-fx-background-color: rgba(0, 51, 0, 0);")) // fully transparent
        );
        timeline.setCycleCount(1);
    }

    //Arrays para contener variables

    List<Integer> procesador = new ArrayList<>();
    List<Integer> memoriaRam = new ArrayList<>();
    List<Integer> memoriaCache = new ArrayList<>();
    List<Integer> memoriaVirtual = new ArrayList<>();
    List<Integer> intermedio = new ArrayList<>();

    //Variables
    int inicio;
    int end;
    boolean flag;
    //Crear ciclo for que va moviendose de proceso en proceso

    protected void createList(List<Integer> list, Pane screen, int index){
        for (int i = 0; i<index; i++){
            list.add(i);
        }
    }
    protected void duplicateLabales(){

    }
    @FXML
    protected void start(){
        createList(memoriaVirtual, virtual, 15);
        createList(memoriaRam, ram, 6);
        createList(memoriaCache, cache, 6);
           //Hacer logica para mover elementos de memoria virtual hacia ram, procesador y cahche
            //Primero mover nodos de virtual a ram los primeros 3
        new Thread(() ->{

            inicio = 0;
            end = 3;
            try{
//                while(flag || !memoriaVirtual.isEmpty()){
                    moveElements(inicio, end, ram, virtual, true);
                    Thread.sleep(2000);
                    moveElements(inicio, end, cache, virtual, false);
                    flag=false;
                    //Crear otros tres labels e insertarlos en el prcesador
//            }
            }
            catch(Exception e){
                    e.printStackTrace();
            }
//
        }).start();
    }

}