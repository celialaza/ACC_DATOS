package org.example.gestorjuegosfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.gestorjuegosfx.AuthServices;
import org.example.gestorjuegosfx.JavaFXUtil;
import org.example.gestorjuegosfx.data.DataProvider;
import org.example.gestorjuegosfx.user.User;
import org.example.gestorjuegosfx.user.UserDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private PasswordField txtContraseña;
    @FXML
    private Button btnContinuar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Label lblInfo;

    private Stage stage;

    private UserDAO userDAO;
    private AuthServices authServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSource ds = DataProvider.getDataSource();
        userDAO = new UserDAO(ds);
        authServices = new AuthServices(userDAO);

    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void cancelar(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void continuar(ActionEvent actionEvent) {

        if(authServices.login(txtCorreo.getText(),txtContraseña.getText()).isPresent()){
            lblInfo.setText("Acceso correcto");
           JavaFXUtil.showModal(Alert.AlertType.INFORMATION,"Bienvenido",null,"Bienvenido");
           MainController mainController = JavaFXUtil.setScene("/org/example/gestorjuegosfx/main-view.fxml");
            mainController.setStage(stage);

        } else{
            lblInfo.setText("Error en el acceso");
            txtCorreo.clear();
            txtContraseña.clear();
        }

    }


}
