package org.example.gestorjuegosfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.gestorjuegosfx.AuthServices;
import org.example.gestorjuegosfx.JavaFXUtil;
import org.example.gestorjuegosfx.data.DataProvider;
import org.example.gestorjuegosfx.game.Game;
import org.example.gestorjuegosfx.game.GameDAO;
import org.example.gestorjuegosfx.user.User;
import org.example.gestorjuegosfx.user.UserDAO;

import javax.sql.DataSource;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



public class GamesController implements Initializable {
    @javafx.fxml.FXML
    private TableColumn cDescripcion;
    @javafx.fxml.FXML
    private TableColumn cPlataforma;
    @javafx.fxml.FXML
    private TableColumn cTitulo;
    @javafx.fxml.FXML
    private TableColumn cAnio;
    @javafx.fxml.FXML
    private Button btnGuardar;
    @javafx.fxml.FXML
    private Button btnVolver;
    @javafx.fxml.FXML
    private TextField txtAnio;
    @javafx.fxml.FXML
    private TextArea txtDescripcion;
    @javafx.fxml.FXML
    private TextField txtTitulo;
    @javafx.fxml.FXML
    private TextField txtPlataforma;
    @javafx.fxml.FXML
    private TableView tablaJuegos;

    private Stage stage;
    private GameDAO gameDAO;
    private AuthServices authServices;
    private User currentUser;
    private Game juegoSeleccionado;

    private final ObservableList<Game> juegosData = FXCollections.observableArrayList();


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //inicializar DAOs y servicios
        DataSource ds = DataProvider.getDataSource();
        gameDAO = new GameDAO(ds);
        authServices = new AuthServices(new UserDAO(ds));

        //Obtener el usuario logeado
        authServices.getCurrentUser().ifPresent(user -> {
            currentUser = user;
        });

        //Configurar la tabla de juegos
        cTitulo.setCellValueFactory(new PropertyValueFactory<>("title"));
        cPlataforma.setCellValueFactory(new PropertyValueFactory<>("platform"));
        cAnio.setCellValueFactory(new PropertyValueFactory<>("year"));
        cDescripcion.setCellValueFactory(new PropertyValueFactory<>("description"));

        //Cargar los juegos del usuario actual
        if (currentUser != null) {
            juegosData.addAll(gameDAO.findAllByUserId(currentUser.getId()));
            tablaJuegos.setItems(juegosData);
        } else {
            JavaFXUtil.showModal(Alert.AlertType.ERROR, "Error de Usuario", "No se encontró usuario", "No se ha podido identificar al usuario logueado.");
        }
        // 5. Configurar el listener de la tabla (HU3)
      /*  tablaJuegos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        mostrarDetallesJuego(newSelection);
                    }
                });

        // 6. Limpiar el formulario al inicio
        limpiarFormulario();
    }
    private void mostrarDetallesJuego(Game game) {
        juegoSeleccionado = game;
        txtTitulo.setText(game.getTitle());
        txtPlataforma.setText(game.getPlatform());
        txtAnio.setText(String.valueOf(game.getYear()));
        txtDescripcion.setText(game.getDescription());
    }

    private void limpiarFormulario() {
        juegoSeleccionado = null;
        txtTitulo.clear();
        txtPlataforma.clear();
        txtAnio.clear();
        txtDescripcion.clear();
        tablaJuegos.getSelectionModel().clearSelection();
    }*/

    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        // Usamos JavaFXUtil para cargar la vista principal
        MainController controller = JavaFXUtil.setScene("/org/example/gestorjuegosfx/main-view.fxml");
        // Le pasamos el 'stage' al controlador principal (igual que hacíamos en LoginController)
        controller.setStage(stage);
    }

    @javafx.fxml.FXML
    public void guardarJuego(ActionEvent actionEvent) {
        if (currentUser == null) {
            JavaFXUtil.showModal(Alert.AlertType.ERROR, "Error", "No hay usuario", "No se puede guardar un juego sin un usuario logueado.");
            return;
        }

        if (txtTitulo.getText().isEmpty() || txtPlataforma.getText().isEmpty()) {
            JavaFXUtil.showModal(Alert.AlertType.WARNING, "Campos vacíos", "Faltan datos", "El título y la plataforma son obligatorios.");
            return;
        }

        int year;
        try {
            year = Integer.parseInt(txtAnio.getText());
        } catch (NumberFormatException e) {
            JavaFXUtil.showModal(Alert.AlertType.WARNING, "Dato incorrecto", "Año no válido", "El año debe ser un número.");
            return;
        }

        if (juegoSeleccionado == null) {
            // --- Es un juego NUEVO (HU2) ---
            Game newGame = new Game(null, txtTitulo.getText(), txtPlataforma.getText(), year, txtDescripcion.getText(), currentUser.getId(), "default.jpg");
            Optional<Game> savedGame = gameDAO.save(newGame);

            savedGame.ifPresent(game -> {
                juegosData.add(game); // <-- Añade el juego a la lista de la tabla

                // --- ¡¡ESTA LÍNEA TE FALTABA!! ---
                limpiarFormulario(); // <-- Limpia el formulario y la selección
            });

        } else {
            // --- Es una ACTUALIZACIÓN (HU3) ---
            juegoSeleccionado.setTitle(txtTitulo.getText());
            juegoSeleccionado.setPlatform(txtPlataforma.getText());
            juegoSeleccionado.setYear(year);
            juegoSeleccionado.setDescription(txtDescripcion.getText());
            gameDAO.update(juegoSeleccionado);
            tablaJuegos.refresh();


            limpiarFormulario();
        }
    }
    /**
     * Limpia el formulario y la selección de la tabla
     */
    private void limpiarFormulario() {
        juegoSeleccionado = null;
        txtTitulo.clear();
        txtPlataforma.clear();
        txtAnio.clear();
        txtDescripcion.clear();


        tablaJuegos.getSelectionModel().clearSelection();
    }


}
