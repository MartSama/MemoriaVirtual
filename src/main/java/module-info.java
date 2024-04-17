module martsama.memoriavirtual {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    opens martsama.memoriavirtual to javafx.fxml;
    exports martsama.memoriavirtual;
}