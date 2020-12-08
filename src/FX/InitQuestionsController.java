package FX;

import api.Answer;
import api.PlayerContainer;
import api.QuestionContainer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitQuestionsController implements Initializable {
    public final Stage stage;
    public PlayerContainer players;
    public QuestionContainer questions;
    private final ObservableList<PlayersList> player_data;
    private final ToggleGroup group = new ToggleGroup();
    @FXML private TextArea textQ;
    @FXML private TextField textFieldA;
    @FXML private TextField textFieldB;
    @FXML private TextField textFieldC;
    @FXML private TextField textFieldD;
    @FXML private RadioButton checkA;
    @FXML private RadioButton checkB;
    @FXML private RadioButton checkC;
    @FXML private RadioButton checkD;
    @FXML private Button addButton;
    @FXML private Button backButton;
    @FXML private Button resetButton;
    @FXML private Button nextButton;
    @FXML private Spinner<Integer> pointsSpinner;
    @FXML private Label number;

    public InitQuestionsController(Stage stage,
                                   PlayerContainer players,
                                   ObservableList<PlayersList> player_data,
                                   QuestionContainer questions)
    {
        this.stage = stage;
        this.players = players;
        this.player_data = player_data;
        this.questions = questions;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/questions.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        pointsSpinner.setValueFactory(valueFactory);

        checkA.setToggleGroup(group);
        checkB.setToggleGroup(group);
        checkC.setToggleGroup(group);
        checkD.setToggleGroup(group);

        addButton.setOnAction(event -> addData());
        backButton.setOnAction(event -> backToPreviousLayout());
        resetButton.setOnAction(event -> reset());
        nextButton.setOnAction(event -> openLayout());
    }

    private void addData() {
        int points = pointsSpinner.getValue();
        String text = textQ.getText().replaceAll("\n", System.getProperty("line.separator"));
        String A = textFieldA.getText();
        String B = textFieldB.getText();
        String C = textFieldC.getText();
        String D = textFieldD.getText();
        Answer answer = new Answer(A, B, C, D);

        if (checkA.isSelected())
            answer.setA();

        if (checkB.isSelected())
            answer.setB();

        if (checkC.isSelected())
            answer.setC();

        if (checkD.isSelected())
            answer.setD();

        if (text.equals("") || A.equals("") || B.equals("") || C.equals("") || D.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("QUIZ Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Żadne pole nie może być puste!");
            alert.showAndWait();
        }

        else {
            questions.addQuestion(text, points, answer);
            number.setText(String.valueOf(questions.questions_list.size()));
            textQ.setText(null);
            textFieldA.setText(null);
            textFieldB.setText(null);
            textFieldC.setText(null);
            textFieldD.setText(null);
        }
    }

    private void reset() {
        questions.questions_list.clear();
        number.setText(String.valueOf(questions.questions_list.size()));
    }

    private void openLayout() {
        GameController controller;

        if (questions.questions_list.size() < 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("QUIZ Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Musisz dodać przynajmniej jedno pytanie, aby kontynuować!");
            alert.showAndWait();
        }

        else
            controller = new GameController(stage, players, questions, 0);
    }

    private void backToPreviousLayout() {
        InitLifesController controller = new InitLifesController(stage, players, player_data, questions);
    }
}