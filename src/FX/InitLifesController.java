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
    @FXML private Spinner<Integer> lifeSpinner;
    @FXML private Button nextButton;
    @FXML private Button backButton;

    public InitLifesController(Stage stage,
                               PlayerContainer players,
                               QuestionContainer questions)
    {
        this.stage = stage;
        this.players = players;
        this.questions = questions;

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
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        lifeSpinner.setValueFactory(valueFactory);

        backButton.setOnAction(event -> backToPreviousLayout());
        nextButton.setOnAction(event -> openLayout());
    }

    private void openLayout() {
        Set<Map.Entry<String, Player>> entrySet = players.players_list.entrySet();

        for (Map.Entry<String, Player> entry: entrySet)
            entry.getValue().lifes = lifeSpinner.getValue();

        InitQuestionsController controller = new InitQuestionsController(stage, players, questions);
    }

    private void backToPreviousLayout() {
        InitPlayersController controller = new InitPlayersController(stage, players, questions);
    }
}