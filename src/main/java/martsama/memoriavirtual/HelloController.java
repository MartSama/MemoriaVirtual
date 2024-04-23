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
    private VBox proc;

    @FXML
    private VBox ram;

    @FXML
    private VBox cache;

    @FXML
    private FlowPane virtual;

    protected void duplicateElements( Pane origin, boolean type){
        int inicio = type ? 6 : 3;
        int end = type  ? 9 : 6;
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
        label.setScaleX(1.4);
        label.setScaleY(1.4);
        transition.play();
        transition.setOnFinished(event -> {
            label.setStyle("-fx-background-color: #ff70a6");
            label.setScaleX(1);
            label.setScaleY(1);
        });
    }

    protected void bigAnimation(Node label, Pane destination){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), label);
        label.setStyle("-fx-background-color: #69d6ff; -fx-border-radius: 10px; -fx-border-color: black; -fx-border-width: 2px");
        label.setScaleX(1.4);
        label.setScaleY(1.4);
        transition.play();
        transition.setOnFinished(event -> {
            destination.getChildren().add(label);
            label.setStyle("-fx-background-color: #69d6ff");
            label.setScaleX(1);
            label.setScaleY(1);
        });
    }
    @FXML
    protected void moveAnimation(int index, Pane destination,Pane original) {
        Node label = original.getChildren().get(index);
        bigAnimation(label, destination);
    }

    protected void moveElements( Pane destination, Pane original){
        for (int i = 0; i < 3; i++){
            moveAnimation(i, destination, original);
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
    @FXML
    protected void start(){
        new Thread( () -> {
            for (int i = 0; i<3; i++) {
                try {
                    moveElements(ram, virtual);
                    Thread.sleep(2000);
                    if(i < 2){
                        moveElements(cache, virtual);
                    }
                    Thread.sleep(2000);
                    duplicateElements(ram, true);
                    Thread.sleep(2000);
                    entradaSalida();
                    Thread.sleep(46000);
                    removeNode(ram, virtual, true, true);
                    if(i < 4){
                        Thread.sleep(2000);
                        duplicateElements(cache, true);
                        Thread.sleep(2000);
                        entradaSalida();
                        Thread.sleep(46000);
                        removeNode(cache, virtual, true, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        }).start();
    }

    protected void entradaSalida(){
        new Thread(()->{
            try{
                for(int i=0; i<9; i++){
                    entradaSalidaAnimation(proc.getChildren().get(i));
                    Thread.sleep(6000);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }).start();
    }
    public void removeNode( Pane origin, Pane destination, boolean imaginary, boolean type){
        int inicio = type ? 6 : 3;
        int end = type ? 9: 6;
        List<Node> nodesToRemove = new ArrayList<>();
       for (int i = inicio; i< end; i++ ){
           moveAnimation(i, destination, origin);

           if(imaginary){
               nodesToRemove.add(proc.getChildren().get(i));
           }
       }
       if(!nodesToRemove.isEmpty()){
           Platform.runLater(() -> proc.getChildren().removeAll(nodesToRemove));
       }
    }
}