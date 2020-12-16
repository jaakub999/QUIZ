package FX;

import api.Player;
import api.PlayerContainer;
import api.QuestionContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class InitPlayersController implements Initializable {
    public final Stage stage;
    public PlayerContainer players;
    public QuestionContainer questions;
    private final ObservableList<PlayersList> player_data;
    @FXML private TextField nick;
    @FXML private Button addButton;
    @FXML private Button resetButton;
    @FXML private Button nextButton;
    @FXML private Button backButton;
    @FXML private Button removeButton;
    @FXML private TableView<PlayersList> table;
    @FXML private TableColumn<PlayersList, String> player_column;
    @FXML private Label errorLabel;

    public InitPlayersController(Stage stage,
                                 PlayerContainer players,
                                 QuestionContainer questions)
    {
        this.stage = stage;
        this.players = players;
        this.questions = questions;
        player_data = FXCollections.observableArrayList();

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
        Set<Map.Entry<String, Player>> entrySet = players.players_list.entrySet();
        for (Map.Entry<String, Player> entry: entrySet)
            player_data.add(new PlayersList(entry.getKey()));

        player_column.setCellValueFactory(cellData -> cellData.getValue().playerProperty());
        player_column.setCellFactory(TextFieldTableCell.forTableColumn());
        table.setItems(player_data);

        resetButton.setOnAction(event -> reset());
        removeButton.setOnAction(event -> removeData());
        addButton.setOnAction(event -> addData());
        nextButton.setOnAction(event -> openLayout());
        backButton.setOnAction(event -> backToPreviousLayout());

        addButton.setTooltip(new Tooltip("Dodaj nowego gracza"));
        resetButton.setTooltip(new Tooltip("Usuń wszystkich graczy"));
        removeButton.setTooltip(new Tooltip("Usuń wybranego gracza"));
    }

    private void addData() {
        String player_nickname = nick.getText();

        if (!player_nickname.equals("")) {
            nick.setText(null);
            players.addPlayer(player_nickname);
            PlayersList item = new PlayersList(player_nickname);
            player_data.add(item);
            table.setItems(player_data);

            InitPlayersController refresh = new InitPlayersController(stage, players, questions);
        }

        else
            errorLabel.setText("Nie podałeś nicku gracza!");
    }

    private void removeData() {
        PlayersList data = table.getSelectionModel().getSelectedItem();

        if (data != null) {
            players.removePlayer(data.getPlayer());
            InitPlayersController refresh = new InitPlayersController(stage, players, questions);
        }
    }

    private void reset() {
        players.players_list.clear();
        player_data.clear();
        table.setItems(player_data);
        errorLabel.setText(null);
    }

    private void openLayout() {
        InitLifesController controller;

        if (players.players_list.size() < 1)
            errorLabel.setText("Musisz dodać przynajmniej jednego gracza, aby móc kontynuować!");

        else
            controller = new InitLifesController(stage, players, questions);
    }

    private void backToPreviousLayout() {
        InitQuestionsController controller = new InitQuestionsController(stage, players, questions);
    }
}
