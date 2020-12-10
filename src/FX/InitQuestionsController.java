package FX;

import api.Answer;
import api.PlayerContainer;
import api.QuestionContainer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitQuestionsController implements Initializable {
    public final Stage stage;
    public PlayerContainer players;
    public QuestionContainer questions;
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
    @FXML private Button chooseImageButton;
    @FXML private Spinner<Integer> pointsSpinner;
    @FXML private Label number;
    @FXML private Label pathLabel;
    @FXML private Label errorLabel;
    @FXML private CheckBox imageCheck;

    public InitQuestionsController(Stage stage,
                                   PlayerContainer players,
                                   QuestionContainer questions)
    {
        this.stage = stage;
        this.players = players;
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
        checkA.setSelected(true);

        number.setText(String.valueOf(questions.questions_list.size()));

        addButton.setOnAction(event -> addData());
        backButton.setOnAction(event -> backToPreviousLayout());
        resetButton.setOnAction(event -> reset());
        nextButton.setOnAction(event -> openLayout());
        chooseImageButton.setOnAction(event -> chooseImage());

        addButton.setTooltip(new Tooltip("Dodaj nowe pytanie"));
        resetButton.setTooltip(new Tooltip("Usuń wszystkie pytania"));
        chooseImageButton.setVisible(false);

        imageCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (imageCheck.isSelected()) {
                    chooseImageButton.setVisible(true);
                    pathLabel.setVisible(true);
                }

                else {
                    chooseImageButton.setVisible(false);
                    pathLabel.setVisible(false);
                }
            }
        });
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

        else if (checkB.isSelected())
            answer.setB();

        else if (checkC.isSelected())
            answer.setC();

        else if (checkD.isSelected())
            answer.setD();

        if (text.equals("") || A.equals("") || B.equals("") || C.equals("") || D.equals(""))
            errorLabel.setText("Żadne pole nie może być puste!");

        else {
            if (imageCheck.isSelected()) {
                if (!pathLabel.getText().equals("")) {
                    questions.addQuestion(text, points, answer, pathLabel.getText());
                    InitQuestionsController refresh = new InitQuestionsController(stage, players, questions);
                }

                else
                    errorLabel.setText("Nie wybrałeś obrazka!");
            }

            else {
                questions.addQuestion(text, points, answer, null);
                InitQuestionsController refresh = new InitQuestionsController(stage, players, questions);
            }
        }
    }

    private void chooseImage() {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fc.getExtensionFilters().add(imageFilter);
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null)
            pathLabel.setText(selectedFile.getAbsolutePath());
    }

    private void reset() {
        questions.questions_list.clear();
        number.setText("0");
    }

    private void openLayout() {
        GameController controller;

        if (questions.questions_list.size() < 1)
            errorLabel.setText("Musisz dodać przynajmniej jedno pytanie, aby móc kontynuować!");

        else
            controller = new GameController(stage, players, questions, 0);
    }

    private void backToPreviousLayout() {
        InitLifesController controller = new InitLifesController(stage, players, questions);
    }
}