package FX;

import api.PlayerContainer;
import api.QuestionContainer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitPlayersController implements Initializable {
    public final Stage stage;
    public PlayerContainer players;
    public QuestionContainer questions;
    private final ObservableList<PlayersList> player_data;
    @FXML private TextField nick;
    @FXML private Button addButton;
    @FXML private Button resetButton;
    @FXML private Button nextButton;
    @FXML private TableView<PlayersList> table;
    @FXML private TableColumn<PlayersList, String> player_column;

    public InitPlayersController(Stage stage,
                                 PlayerContainer players,
                                 ObservableList<PlayersList> player_data,
                                 QuestionContainer questions)
    {
        this.stage = stage;
        this.players = players;
        this.player_data = player_data;
        this.questions = questions;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/players.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player_column.setCellValueFactory(new PropertyValueFactory<PlayersList, String>("player"));
        table.setEditable(true);
        player_column.setCellFactory(TextFieldTableCell.forTableColumn());
        table.setItems(player_data);

        addButton.setOnAction(event -> addData());
        resetButton.setOnAction(event -> reset());
        nextButton.setOnAction(event -> openLayout());
    }

    private void addData() {
        String player_nickname = nick.getText();

        if (!player_nickname.equals("")) {
            nick.setText(null);
            players.addPlayer(player_nickname);
            PlayersList item = new PlayersList(player_nickname);
            player_data.add(item);
            table.setItems(player_data);
        }

        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("QUIZ Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Najpierw podaj nick gracza!");
            alert.showAndWait();
        }
    }

    private void reset() {
        players.players_list.clear();
        player_data.clear();
        table.setItems(player_data);
    }

    private void openLayout() {
        InitLifesController controller;

        if (players.players_list.size() < 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("QUIZ Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Musisz dodać przynajmniej jednego gracza, aby kontynuować!");
            alert.showAndWait();
        }

        else
            controller = new InitLifesController(stage, players, player_data, questions);
    }
}
