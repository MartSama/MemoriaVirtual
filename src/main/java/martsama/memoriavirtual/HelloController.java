package martsama.memoriavirtual;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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

    protected void duplicateElements(int inicio, int end, Pane origin){

        for ( int i = inicio; i< end; i ++ ){
            Node label = origin.getChildren().get(i);
            bigAnimation(label);
            Label label0 = new Label();
            label0.setText(((Label) label).getText());
            label0.getStyleClass().add("custom-label");
            label0.setFont(((Label) label).getFont());
            TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), label0);

            label0.setStyle("-fx-background-color: #adf7b6; -fx-border-radius: 10px; -fx-border-color: black; -fx-border-width: 2px");
            label0.setScaleX(2);
            label0.setScaleY(2);
            transition2.play();

            transition2.setOnFinished(event -> {
                proc.getChildren().add(label0);
                //Quitar este en caso necesario
                label.setStyle("-fx-background-color: rgba(0,0,0,.60)");
                label0.setStyle("-fx-background-color: rgba(0,0,0, .60)");
                label0.setScaleX(1);
                label0.setScaleY(1);
            });
        }
    }

    protected void bigAnimation(Node label){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), label);
        label.setStyle("-fx-background-color: #6e78ff; -fx-border-radius: 10px; -fx-border-color: black; -fx-border-width: 2px");
        label.setScaleX(2);
        label.setScaleY(2);
        transition.play();
        transition.setOnFinished(event -> {
            label.setStyle("-fx-background-color: #ff70a6");
            label.setScaleX(1);
            label.setScaleY(1);
        });
    }
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

            label.setStyle("-fx-background-color: #ff9770; -fx-border-radius: 10px; -fx-border-color: black; -fx-border-width: 2px");
            label.setScaleX(1.4);
            label.setScaleY(1.4);
            transition.play();
            label0.setStyle("-fx-background-color: #ff9770; -fx-border-radius: 10px; -fx-border-color: black; -fx-border-width: 2px");
            label0.setScaleX(1.4);
            label0.setScaleY(1.4);
            transition2.play();
            transition.setOnFinished(event -> {
                destination.getChildren().add(label);

                label.setStyle("-fx-background-color: #70d6ff");
                label.setScaleX(1);
                label.setScaleY(1);
            });
            transition2.setOnFinished(event -> {
                proc.getChildren().add(label0);

                label0.setStyle("-fx-background-color: #70d6ff");
                label0.setScaleX(1);
                label0.setScaleY(1);
            });
        }else{
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), label);
            label.setStyle("-fx-background-color: #70d6ff; -fx-border-radius: 10px; -fx-border-color: black; -fx-border-width: 2px");
            label.setScaleX(1.4);
            label.setScaleY(1.4);
            transition.play();
            transition.setOnFinished(event -> {
                destination.getChildren().add(label);
                label.setStyle("-fx-background-color: #70d6ff");
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
        new KeyFrame(Duration.seconds(0), event -> label.setStyle("-fx-background-color: rgba(68,97, 71, 1);")), // deep purple
                new KeyFrame(Duration.seconds(1), event -> label.setStyle("-fx-background-color: rgba(68,97, 71, 0.80);")), // medium purple
                new KeyFrame(Duration.seconds(2), event -> label.setStyle("-fx-background-color: rgba(68,97, 71, 0.60);")), // light purple
                new KeyFrame(Duration.seconds(3), event -> label.setStyle("-fx-background-color: rgba(68,97, 71, 0.40);")), // semi-transparent
                new KeyFrame(Duration.seconds(4), event -> label.setStyle("-fx-background-color: rgba(68,97, 71, 0.20);")), // semi-transparent
                new KeyFrame(Duration.seconds(5), event -> label.setStyle("-fx-background-color: rgba(68,97, 71, 0);")) // fully transparent
        );
        timeline.setCycleCount(1);
        timeline.play();
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
                    Thread.sleep(2000);
                    //Crear logica para animaciones
                    new Thread(()->{
                        try{
                            entradaSalida();
                            Thread.sleep(45000);
                            removeNode(6,9, ram, virtual, true);
                            Thread.sleep(3000);
                            moveElements(0,3,ram,virtual, false);
                            Thread.sleep(3000);
                            duplicateElements(6,9, cache);
                            Thread.sleep(3000);
                            entradaSalida();
                            Thread.sleep(45000);
                            removeNode(6,9, cache, virtual, true);
                            Thread.sleep(3000);
                            duplicateElements(6,9, ram);
                            entradaSalida();
                            Thread.sleep(3000);
                            moveElements(0,3,cache,virtual,false);
                            Thread.sleep(45000);
                            removeNode(6,9, ram,virtual, true);
                           Thread.sleep(3000);
                           duplicateElements(6,9,cache);
                           Thread.sleep(3000);
                           entradaSalida();
                           Thread.sleep(3000);
                           moveElements(0,3,ram,virtual, false);
                            Thread.sleep(45000);
                            removeNode(6,9,cache, virtual,true);
                            Thread.sleep(3000);
                            duplicateElements(6,9, ram);
                            Thread.sleep(3000);
                            entradaSalida();
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }).start();

                    //Hacer animacion para sacar nodos
//            }
            }
            catch(Exception e){
                    e.printStackTrace();
            }
//
        }).start();
    }

    protected void entradaSalida(){
        new Thread(()->{
            try{
                for(int i=0; i<9; i++){
                    entradaSalidaAnimation(proc.getChildren().get(i));
//                                Thread.sleep(10000);
                    Thread.sleep(6000);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }).start();
    }
    public void removeNode(int inicio, int end, Pane origin, Pane destination, boolean imaginary){
        List<Node> nodesToRemove = new ArrayList<>();
       for (int i = inicio; i< end; i++ ){
           moveAnimation(i, destination, origin, false);

           if(imaginary){
               nodesToRemove.add(proc.getChildren().get(i));
           }
       }
       if(!nodesToRemove.isEmpty()){
           Platform.runLater(() -> proc.getChildren().removeAll(nodesToRemove));
       }
    }
}