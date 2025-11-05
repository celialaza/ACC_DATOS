package org.example.gestorjuegosfx.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.gestorjuegosfx.AuthServices;
import org.example.gestorjuegosfx.JavaFXUtil;
import org.example.gestorjuegosfx.data.DataProvider;
import org.example.gestorjuegosfx.user.User;
import org.example.gestorjuegosfx.user.UserDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class MainController implements Initializable {

    @javafx.fxml.FXML
    private Button btnSalir;

    private Stage stage;
    @javafx.fxml.FXML
    private ChoiceBox<User> choice;

    @javafx.fxml.FXML
    private ListView<User> lista;
    @javafx.fxml.FXML
    private TableColumn<User,String> cEmail;
    @javafx.fxml.FXML
    private TableColumn<User,String> cContraseña;
    @javafx.fxml.FXML
    private TableView<User> tabla;
    private AuthServices authServices;
    private UserDAO userDAO;
    @javafx.fxml.FXML
    private Button btnItJuegos;

    public void setStage(Stage stage){
        this.stage=stage;
    }

    private ObservableList<User> datos = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSource ds = DataProvider.getDataSource();
        userDAO = new UserDAO(ds);
        authServices = new AuthServices(userDAO);

        authServices.getCurrentUser().ifPresent(System.out::println);


        choice.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getEmail().toUpperCase();
            }

            @Override
            public User fromString(String s) {
                return null;
            }
        });

        datos.add( new User(1,"fr@cesur.com","1234",true) );
        datos.add( new User(2,"ana@cesur.com", "abcd", true) );
        datos.add( new User(3,"luis@cesur.com", "pass123", false) );
        datos.add( new User(4,"maria@cesur.com", "qwerty", true) );
        datos.add( new User(5,"jose@cesur.com", "admin", false) );
        datos.add( new User(6,"carmen@cesur.com", "cesur2025", true) );
        choice.setItems(datos);

        lista.setItems(datos);
        lista.setCellFactory( (_)-> new ListCell<>(){
            @Override
            public void updateItem(User item, boolean empty){
                super.updateItem(item, empty);
                if(!empty) setText(item.getEmail());
            }
        });

        cEmail.setCellValueFactory( (cell)->{
            return new SimpleStringProperty("("+cell.getValue().getEmail()+")");
        });
        cContraseña.setCellValueFactory( (cell)->{
            return new SimpleStringProperty("Password: "+cell.getValue().getPassword());
        });

        tabla.setItems(datos);

        //choice.getItems().addAll("A","B","C","D","E","F","G","H");
        choice.setValue( choice.getItems().getFirst() );
    }
    @javafx.fxml.FXML
    public void irJuegos(ActionEvent actionEvent) {
        // Usamos JavaFXUtil para cargar la nueva escena
        GamesController controller = JavaFXUtil.setScene("/org/example/gestorjuegosfx/games-view.fxml");
        // Le pasamos el 'stage' al nuevo controlador
        controller.setStage(stage);
    }



    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {

    try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();



    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    }


}
