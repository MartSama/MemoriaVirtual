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

    //Codigo que duplica los procesos, ya sea ram o cache, lo que hace es crear un nodo que se agrega al procesador
    protected void duplicateElements( Pane origin, boolean type){
        //Operadores ternarios para duplicar segun el tamano del contendor de origen, 6 para la ram y 3 para el cache
        int inicio = type ? 6 : 3;
        int end = type  ? 9 : 6;
        //Ciclo for que agrega animaciones para cada nodo(proceso) que movemos
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
            //Agregamos los nodos despues de las animaciones
            transition2.setOnFinished(event -> {
                proc.getChildren().add(label0);
                label.setStyle("-fx-background-color: rgba(255,0,200,.60)");
                label0.setStyle("-fx-background-color: rgba(255,0,200, .60)");
                label0.setScaleX(1);
                label0.setScaleY(1);
            });
        }
    }

    //Codigo de animacino que lo que hace es escalar el tamano de cada label
    protected void bigAnimation(Node label){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), label);
        label.setStyle("-fx-background-color: #6e78ff; -fx-border-radius: 10px; -fx-border-color: black; -fx-border-width: 2px");
        label.setScaleX(1.4);
        label.setScaleY(1.4);
        transition.play();
        //Regresamos el label a la normalidad
        transition.setOnFinished(event -> {
            label.setStyle("-fx-background-color: #ff70a6");
            label.setScaleX(1);
            label.setScaleY(1);
        });
    }

    //Hacemos una sobrecarga de metodo al agregar un panel de destino, este metodo aparte
    // de hacer que el nodo (proceso) se haga mas grande, tambien mueve el nodo hacia el panel de desetino
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
    //Creamos un metodo para separar la logica, lo que hace es procesar cada nodo y agregarle una animacion
    @FXML
    protected void moveAnimation(int index, Pane destination,Pane original) {
        Node label = original.getChildren().get(index);
        bigAnimation(label, destination);
    }

    //Codigo que nos permite moven los procesos de un panel a otro
    protected void moveElements( Pane destination, Pane original){
        for (int i = 0; i < 3; i++){
            moveAnimation(i, destination, original);
        }
    }
    //Con esto animamos el proceso de entrada salida, agregamos ciertos colores para que vaya cambiando
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
    //En este metodo lo que hacemos es inciar la logica, se activa con un boton y al activarle empieza todo el programa
    protected void start(){
        //Primero creamos un nuevo hilo para poder actualizar la ui mientras tenemos el hilo principal de JavaFx
        new Thread( () -> {
            //Creamos un ciclo for que nos permite ejecutar una porcion de codigo especifica una cantidad n de veces
            for (int i = 0; i<3; i++) {
                try {
                   //Empieza la logica y movemos los elementos
                    moveElements(ram, virtual);
                    //Hacemos que el codigo se "freezee" por unos segundos para que las animacinoes no ocurran a la vez
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
                        duplicateElements(cache, false);
                        Thread.sleep(2000);
                        entradaSalida();
                        Thread.sleep(46000);
                        removeNode(cache, virtual, true);
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

    public void removeNode( Pane origin, Pane destination, boolean imaginary ){
        int inicio = 3;
        int end = 6;
        List<Node> nodesToRemove = new ArrayList<>();
        for (int i = inicio; i< end; i++ ){
            moveAnimation(i, destination, origin);

            if(imaginary){
                nodesToRemove.add(proc.getChildren().get(i+3));
            }
        }
        if(!nodesToRemove.isEmpty()){
            Platform.runLater(() -> proc.getChildren().removeAll(nodesToRemove));
        }
    }
}