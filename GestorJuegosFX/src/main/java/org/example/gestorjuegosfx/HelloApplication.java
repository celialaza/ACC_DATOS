package org.example.gestorjuegosfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gestorjuegosfx.controllers.LoginController;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
     JavaFXUtil.initStage(stage);
     JavaFXUtil.setScene("/org/example/gestorjuegosfx/login-view.fxml");
    }
}
