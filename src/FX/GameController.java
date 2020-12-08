package FX;

import api.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class GameController implements Initializable {
    public final Stage stage;
    public PlayerContainer player;
    public QuestionContainer question;
    public int loopCounter;
    private final ObservableList<GameTableView> player_data;
    private final ToggleGroup group = new ToggleGroup();
    private final Set<Map.Entry<String, Player>> entrySet;
    @FXML private TableView<GameTableView> table;
    @FXML private TableColumn<GameTableView, Integer> lp_column;
    @FXML private TableColumn<GameTableView, String> player_column;
    @FXML private TableColumn<GameTableView, Integer> score_column;
    @FXML private TableColumn<GameTableView, Integer> life_column;
    @FXML private TextArea questionArea;
    @FXML private RadioButton checkA;
    @FXML private RadioButton checkB;
    @FXML private RadioButton checkC;
    @FXML private RadioButton checkD;
    @FXML private Label labelA;
    @FXML private Label labelB;
    @FXML private Label labelC;
    @FXML private Label labelD;
    @FXML private Button enterButton;

    public GameController(Stage stage,
                          PlayerContainer player,
                          QuestionContainer question,
                          int loopCounter)
    {
        this.stage = stage;
        this.player = player;
        this.question = question;
        this.loopCounter = loopCounter;
        player_data = FXCollections.observableArrayList();
        entrySet = player.players_list.entrySet();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/game.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkA.setToggleGroup(group);
        checkB.setToggleGroup(group);
        checkC.setToggleGroup(group);
        checkD.setToggleGroup(group);

        initPlayersTable();
        initQuestion();

        enterButton.setOnAction(event -> submit());
    }

    private void initPlayersTable() {
        lp_column.setCellValueFactory(cellData -> cellData.getValue().lpProperty().asObject());
        player_column.setCellValueFactory(cellData -> cellData.getValue().nicknameProperty());
        score_column.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
        life_column.setCellValueFactory(cellData -> cellData.getValue().lifebuoyProperty().asObject());

        lp_column.setCellFactory(TextFieldTableCell.<GameTableView, Integer>forTableColumn(new IntegerStringConverter()));
        player_column.setCellFactory(TextFieldTableCell.forTableColumn());
        score_column.setCellFactory(TextFieldTableCell.<GameTableView, Integer>forTableColumn(new IntegerStringConverter()));
        life_column.setCellFactory(TextFieldTableCell.<GameTableView, Integer>forTableColumn(new IntegerStringConverter()));

        int i = 1;

        for (Map.Entry<String, Player> entry: entrySet) {
            GameTableView item = new GameTableView(i,
                    entry.getValue().nickname,
                    entry.getValue().score,
                    entry.getValue().lifes);

            player_data.add(item);
            i++;
        }

        table.setItems(player_data);
    }

    private void initQuestion() {
        questionArea.setText("Pytanie " + (loopCounter + 1) + ":\n" + question.questions_list.get(loopCounter).text);
        questionArea.setEditable(false);

        labelA.setText(question.questions_list.get(loopCounter).answer.A);
        labelB.setText(question.questions_list.get(loopCounter).answer.B);
        labelC.setText(question.questions_list.get(loopCounter).answer.C);
        labelD.setText(question.questions_list.get(loopCounter).answer.D);
    }

    private void submit() {
        GameTableView data = table.getSelectionModel().getSelectedItem();
        GameController refresh;

        if (data != null) {
            if (checkA.isSelected()) {
                if (question.questions_list.get(loopCounter).answer.a) {
                    player.players_list.get(data.getNickname()).addScore(question.questions_list.get(loopCounter).points);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("QUIZ Information Dialog");
                    alert.setHeaderText("Świetnie");
                    alert.setContentText("Odpowiedź poprawna!");
                    alert.showAndWait();
                }

                else {
                    player.players_list.get(data.getNickname()).lifes--;

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("QUIZ Information Dialog");
                    alert.setHeaderText("Źle");
                    alert.setContentText("Odpowiedź niepoprawna!");
                    alert.showAndWait();
                }

                loopCounter++;
                if (loopCounter < question.questions_list.size())
                    refresh = new GameController(stage, player, question, loopCounter);

                else
                    whoWon();
            }

            else if (checkB.isSelected()) {
                if (question.questions_list.get(loopCounter).answer.b) {
                    player.players_list.get(data.getNickname()).addScore(question.questions_list.get(loopCounter).points);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("QUIZ Information Dialog");
                    alert.setHeaderText("Świetnie");
                    alert.setContentText("Odpowiedź poprawna!");
                    alert.showAndWait();
                }

                else {
                    player.players_list.get(data.getNickname()).lifes--;

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("QUIZ Information Dialog");
                    alert.setHeaderText("Źle");
                    alert.setContentText("Odpowiedź niepoprawna!");
                    alert.showAndWait();
                }

                loopCounter++;
                if (loopCounter < question.questions_list.size())
                    refresh = new GameController(stage, player, question, loopCounter);

                else
                    whoWon();
            }

            else if (checkC.isSelected()) {
                if (question.questions_list.get(loopCounter).answer.c) {
                    player.players_list.get(data.getNickname()).addScore(question.questions_list.get(loopCounter).points);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("QUIZ Information Dialog");
                    alert.setHeaderText("Świetnie");
                    alert.setContentText("Odpowiedź poprawna!");
                    alert.showAndWait();
                }

                else {
                    player.players_list.get(data.getNickname()).lifes--;

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("QUIZ Information Dialog");
                    alert.setHeaderText("Źle");
                    alert.setContentText("Odpowiedź niepoprawna!");
                    alert.showAndWait();
                }

                loopCounter++;
                if (loopCounter < question.questions_list.size())
                    refresh = new GameController(stage, player, question, loopCounter);

                else
                    whoWon();
            }

            else if (checkD.isSelected()) {
                if (question.questions_list.get(loopCounter).answer.d) {
                    player.players_list.get(data.getNickname()).addScore(question.questions_list.get(loopCounter).points);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("QUIZ Information Dialog");
                    alert.setHeaderText("Świetnie");
                    alert.setContentText("Odpowiedź poprawna!");
                    alert.showAndWait();
                }

                else {
                    player.players_list.get(data.getNickname()).lifes--;

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("QUIZ Information Dialog");
                    alert.setHeaderText("Źle");
                    alert.setContentText("Odpowiedź niepoprawna!");
                    alert.showAndWait();
                }

                loopCounter++;
                if (loopCounter < question.questions_list.size())
                    refresh = new GameController(stage, player, question, loopCounter);

                else
                    whoWon();
            }

            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("QUIZ Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Nie wybrano żadnej odpowiedzi!");
                alert.showAndWait();
            }
        }

        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("QUIZ Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Nie wybrano gracza!");
            alert.showAndWait();
        }
    }

    private void whoWon() {
        Player winner = null;
        int bestScore = 0;
        
        for (Map.Entry<String, Player> entry: entrySet) {
            if (entry.getValue().score > bestScore) {
                bestScore = entry.getValue().score;
                winner = entry.getValue();
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("QUIZ Information Dialog");
        alert.setHeaderText("Koniec");
        alert.setContentText("Zwycięzca: " + winner.nickname + "\nWynik: " + winner.score);
        alert.showAndWait();
    }
}