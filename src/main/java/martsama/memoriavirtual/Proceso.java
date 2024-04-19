package martsama.memoriavirtual;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Proceso {
    private Color color;
    private String nombre;
    private Label label;
    private int tiempo;
    private int posicion;
    private boolean tipo;

    public Proceso(Color color, String nombre, Label label, int tiempo, int posicion, boolean tipo) {
        this.label = label;
        this.color = color;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.posicion = posicion;
        this.tipo = tipo;
    }

    public Color getColor() {
        return color;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getPosicion() {
        return posicion;
    }

    public boolean isTipo() {
        return tipo;
    }

    public Label getLabel(){
        return label;
    }
}
