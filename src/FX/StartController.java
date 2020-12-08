package FX;

import api.PlayerContainer;
import api.QuestionContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    public final Stage stage;
    private final ObservableList<PlayersList> player_data;
    public PlayerContainer players;
    public QuestionContainer questions;
    @FXML private Button startButton;

    public StartController() {
        players = new PlayerContainer();
        questions = new QuestionContainer();
        stage = new Stage();
        stage.setTitle("QUIZ");
        player_data = FXCollections.observableArrayList();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/start.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.setOnAction(event -> openLayout());
    }

    private void openLayout() {
        stage.close();
        InitPlayersController init = new InitPlayersController(stage, players, player_data, questions);
        stage.show();
    }
}
