package FX;

import api.Player;
import api.PlayerContainer;
import api.QuestionContainer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class InitLifesController implements Initializable {
    public final Stage stage;
    public PlayerContainer players;
    public QuestionContainer questions;
    public Set<Map.Entry<String, Player>> entrySet;

    @FXML private Spinner<Integer> chanceSpinner;
    @FXML private Button nextButton;
    @FXML private Button backButton;

    public InitLifesController(Stage stage,
                               PlayerContainer players,
                               QuestionContainer questions)
    {
        this.stage = stage;
        this.players = players;
        this.questions = questions;
        entrySet = players.players_list.entrySet();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/playerLifes.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map.Entry<String, Player> entry = players.players_list.entrySet().iterator().next();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, entry.getValue().getChances());
        chanceSpinner.setValueFactory(valueFactory);
        chanceSpinner.setTooltip(new Tooltip("> 100 = 100\n< 1 = 1"));
        chanceSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                chanceSpinner.increment(0);
                });

        backButton.setOnAction(event -> backToPreviousLayout());
        nextButton.setOnAction(event -> openLayout());
    }


    private void openLayout() {
        for (Map.Entry<String, Player> entry: entrySet)
            entry.getValue().setChances(chanceSpinner.getValue());

        OptionsController controller = new OptionsController(stage, players, questions);
    }

    private void backToPreviousLayout() {
        InitPlayersController controller = new InitPlayersController(stage, players, questions);
    }
}