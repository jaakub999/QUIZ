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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {
    static int ITERATION;

    public final Stage stage;
    public PlayerContainer players;
    public QuestionContainer question;
    private final ObservableList<GameTableView> player_data;
    private final ToggleGroup group;
    private final Set<Map.Entry<String, Player>> entrySet;
    private Player[] players_tab;

    @FXML private AnchorPane mainPane;
    @FXML private HBox answerBox;
    @FXML private TextArea questionArea;
    @FXML private TableView<GameTableView> table;
    @FXML private TableColumn<GameTableView, Integer> lp_column;
    @FXML private TableColumn<GameTableView, String> player_column;
    @FXML private TableColumn<GameTableView, Integer> score_column;
    @FXML private TableColumn<GameTableView, Integer> life_column;
    @FXML private RadioButton checkA;
    @FXML private RadioButton checkB;
    @FXML private RadioButton checkC;
    @FXML private RadioButton checkD;
    @FXML private Label labelA;
    @FXML private Label labelB;
    @FXML private Label labelC;
    @FXML private Label labelD;
    @FXML private Label errorLabel;
    @FXML private Button enterButton;
    @FXML private Button showButton;
    @FXML private ImageView imageView;

    public GameController(Stage stage,
                          PlayerContainer players,
                          QuestionContainer question)
    {
        this.stage = stage;
        this.players = players;
        this.question = question;
        group = new ToggleGroup();
        player_data = FXCollections.observableArrayList();
        players_tab = new Player[players.players_list.size()];
        entrySet = players.players_list.entrySet();

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

        initPlayers();
        initQuestion();

        enterButton.setOnAction(event -> submit());
        showButton.setOnAction(event -> {
            answerBox.setVisible(true);
            showButton.setVisible(false);
        });

        enterButton.setTooltip(new Tooltip("Zatwierdź i sprawdź odpowiedź"));
    }

    private void initPlayers() {
        players_tab = generatePlayersTab();

        lp_column.setCellValueFactory(cellData -> cellData.getValue().lpProperty().asObject());
        player_column.setCellValueFactory(cellData -> cellData.getValue().nicknameProperty());
        score_column.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
        life_column.setCellValueFactory(cellData -> cellData.getValue().lifesProperty().asObject());

        lp_column.setCellFactory(TextFieldTableCell.<GameTableView, Integer>forTableColumn(new IntegerStringConverter()));
        player_column.setCellFactory(TextFieldTableCell.forTableColumn());
        score_column.setCellFactory(TextFieldTableCell.<GameTableView, Integer>forTableColumn(new IntegerStringConverter()));
        life_column.setCellFactory(TextFieldTableCell.<GameTableView, Integer>forTableColumn(new IntegerStringConverter()));

        int outOfChance = 0;
        for (int i = 0; i < players_tab.length; i++) {
            player_data.add(new GameTableView(i + 1,
                    players_tab[i].getNickname(),
                    players_tab[i].getScore(),
                    players_tab[i].getChances()));

            if (players_tab[i].getChances() == 0)
                outOfChance++;
        }

        table.setItems(player_data);

        if (outOfChance == players_tab.length)
            result();
    }

    private void initQuestion() {
        questionArea.setText("Pytanie " + (ITERATION + 1) + " z " + question.questions_list.size() +
                                "\t\t(" + question.questions_list.get(ITERATION).getPoints() + "pkt)" +
                                "\nKategoria:\t\t" + question.questions_list.get(ITERATION).getCategory() +
                                "\nPoziom:\t\t\t" + question.questions_list.get(ITERATION).getLevel() +
                                "\n\n" + question.questions_list.get(ITERATION).getText());

        labelA.setText(question.questions_list.get(ITERATION).getAnswer().A);
        labelB.setText(question.questions_list.get(ITERATION).getAnswer().B);
        labelC.setText(question.questions_list.get(ITERATION).getAnswer().C);
        labelD.setText(question.questions_list.get(ITERATION).getAnswer().D);

        if (question.questions_list.get(ITERATION).getImagePath() != null) {
            File file = new File(question.questions_list.get(ITERATION).getImagePath());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    private void submit() {
        GameTableView data = table.getSelectionModel().getSelectedItem();
        GameController refresh;

        if (data != null) {
            if (data.getLifes() == 0)
                errorLabel.setText("Ten gracz stracił już wszystkie szanse - nie może odpowiadać!");

            else {
                if (checkA.isSelected()) {
                    if (question.questions_list.get(ITERATION).getAnswer().a) {
                        players.players_list.get(data.getNickname()).addScore(question.questions_list.get(ITERATION).getPoints());
                        correctAnswer();
                    } else {
                        players.players_list.get(data.getNickname()).chanceDecrement();
                        wrongAnswer();
                    }

                    ITERATION++;
                    if (ITERATION < question.questions_list.size())
                        refresh = new GameController(stage, players, question);

                    else
                        result();
                } else if (checkB.isSelected()) {
                    if (question.questions_list.get(ITERATION).getAnswer().b) {
                        players.players_list.get(data.getNickname()).addScore(question.questions_list.get(ITERATION).getPoints());
                        correctAnswer();
                    } else {
                        players.players_list.get(data.getNickname()).chanceDecrement();
                        wrongAnswer();
                    }

                    ITERATION++;
                    if (ITERATION < question.questions_list.size())
                        refresh = new GameController(stage, players, question);

                    else
                        result();
                } else if (checkC.isSelected()) {
                    if (question.questions_list.get(ITERATION).getAnswer().c) {
                        players.players_list.get(data.getNickname()).addScore(question.questions_list.get(ITERATION).getPoints());
                        correctAnswer();
                    } else {
                        players.players_list.get(data.getNickname()).chanceDecrement();
                        wrongAnswer();
                    }

                    ITERATION++;
                    if (ITERATION < question.questions_list.size())
                        refresh = new GameController(stage, players, question);

                    else
                        result();
                } else if (checkD.isSelected()) {
                    if (question.questions_list.get(ITERATION).getAnswer().d) {
                        players.players_list.get(data.getNickname()).addScore(question.questions_list.get(ITERATION).getPoints());
                        correctAnswer();
                    } else {
                        players.players_list.get(data.getNickname()).chanceDecrement();
                        wrongAnswer();
                    }

                    ITERATION++;
                    if (ITERATION < question.questions_list.size())
                        refresh = new GameController(stage, players, question);

                    else
                        result();
                } else
                    errorLabel.setText("Nie wybrano żadnej odpowiedzi!");
            }
        }

        else
            errorLabel.setText("Nie wybrano gracza!");
    }

    private void result() {
        players_tab = generatePlayersTab();
        player_data.clear();

        for (int i = 0; i < players_tab.length; i++)
            player_data.add(new GameTableView(i + 1,
                    players_tab[i].getNickname(),
                    players_tab[i].getScore(),
                    players_tab[i].getChances()));

        table.setItems(player_data);
        mainPane.setDisable(true);
        GameOverController gameOver = new GameOverController(player_data);
        gameOver.stage.showAndWait();
        stage.close();
        StartController restart = new StartController();
        restart.stage.showAndWait();
    }

    private void correctAnswer() {
        mainPane.setDisable(true);
        AnswerController controller = new AnswerController(null);
    }

    private void wrongAnswer() {
        String correct = null;

        if (question.questions_list.get(ITERATION).getAnswer().a)
            correct = labelA.getText();

        else if (question.questions_list.get(ITERATION).getAnswer().b)
            correct = labelB.getText();

        else if (question.questions_list.get(ITERATION).getAnswer().c)
            correct = labelC.getText();

        else if (question.questions_list.get(ITERATION).getAnswer().d)
            correct = labelD.getText();

        mainPane.setDisable(true);
        AnswerController controller = new AnswerController(correct);
    }

    private Player[] generatePlayersTab() {
        Player[] tab = new Player[players.players_list.size()];
        Player temp;
        boolean change = true;
        int i = 0;

        for (Map.Entry<String, Player> entry: entrySet) {
            tab[i] = entry.getValue();
            i++;
        }

        while (change) {
            change = false;

            for (i = 0; i < tab.length - 1; i++) {
                if (tab[i].getScore() < tab[i + 1].getScore()) {
                    temp = tab[i];
                    tab[i] = tab[i + 1];
                    tab[i + 1] = temp;
                    change = true;
                }
            }
        }

        return tab;
    }
}