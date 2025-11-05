module org.example.gestorjuegosfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires mysql.connector.j;
    requires java.naming;
    requires javafx.graphics;
    requires java.logging;



    opens org.example.gestorjuegosfx to javafx.fxml;
    exports org.example.gestorjuegosfx;
    exports org.example.gestorjuegosfx.user;
    opens org.example.gestorjuegosfx.user to javafx.fxml;
    exports org.example.gestorjuegosfx.controllers;
    opens org.example.gestorjuegosfx.controllers to javafx.fxml;
    exports org.example.gestorjuegosfx.data;
    opens org.example.gestorjuegosfx.data to javafx.fxml;
    exports org.example.gestorjuegosfx.game;
    opens org.example.gestorjuegosfx.game to javafx.fxml;
}