package FX;

import api.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class InitQuestionsController implements Initializable {
    public final Stage stage;
    public PlayerContainer players;
    public QuestionContainer questions;
    private final ObservableList<String> questionID;
    private final ToggleGroup group;
    @FXML private AnchorPane mainPane;
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
    @FXML private Button resetButton;
    @FXML private Button removeButton;
    @FXML private Button nextButton;
    @FXML private Button chooseImageButton;
    @FXML private Button editButton;
    @FXML private Button saveButton;
    @FXML private Button loadButton;
    @FXML private Spinner<Integer> pointsSpinner;
    @FXML private ComboBox<String> questionComboBox;
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
        group = new ToggleGroup();
        questionID = FXCollections.observableArrayList();

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
        pointsSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                pointsSpinner.increment(0);
        });

        checkA.setToggleGroup(group);
        checkB.setToggleGroup(group);
        checkC.setToggleGroup(group);
        checkD.setToggleGroup(group);
        checkA.setSelected(true);

        number.setText("Ilość pytań: " + questions.questions_list.size());

        addButton.setOnAction(event -> addData());
        resetButton.setOnAction(event -> reset());
        nextButton.setOnAction(event -> openLayout());
        removeButton.setOnAction(event -> removeData());
        chooseImageButton.setOnAction(event -> chooseImage());
        editButton.setOnAction(event -> editData());
        saveButton.setOnAction(event -> saveData());
        loadButton.setOnAction(event -> loadData());

        addButton.setTooltip(new Tooltip("Dodaj nowe pytanie"));
        resetButton.setTooltip(new Tooltip("Usuń wszystkie pytania"));
        removeButton.setTooltip(new Tooltip("Usuń wybrane pytanie"));
        editButton.setTooltip(new Tooltip("Nadpisz wybrane pytanie"));
        chooseImageButton.setVisible(false);
        editButton.setVisible(false);

        for (int i = 0; i < questions.questions_list.size(); i++) {
            if (questions.questions_list.get(i).text.length() < 12)
                questionID.add(i + 1 + ". " + questions.questions_list.get(i).text);

            else
                questionID.add((i + 1 + ". " + questions.questions_list.get(i).text).substring(0, 12) + "...");
        }

        questionComboBox.setItems(questionID);
        questionComboBox.setOnAction(event -> chooseQuestion());
        questionComboBox.setTooltip(new Tooltip("Wybierz jedno z utworzonych pytań"));

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

    private void removeData() {
        if (!questionComboBox.getSelectionModel().isEmpty()) {
            int index = questionComboBox.getSelectionModel().getSelectedIndex();
            questions.removeQuestion(index);
        }

        InitQuestionsController refresh = new InitQuestionsController(stage, players, questions);
    }

    private void editData() {
        int index = questionComboBox.getSelectionModel().getSelectedIndex();
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
                    questions.editQuestion(text, points, answer, pathLabel.getText(), index);
                    InitQuestionsController refresh = new InitQuestionsController(stage, players, questions);
                }

                else
                    errorLabel.setText("Nie wybrałeś obrazka!");
            }

            else {
                questions.editQuestion(text, points, answer, null, index);
                InitQuestionsController refresh = new InitQuestionsController(stage, players, questions);
            }
        }
    }

    private void chooseQuestion() {
        int index = questionComboBox.getSelectionModel().getSelectedIndex();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, questions.questions_list.get(index).points);
        pointsSpinner.setValueFactory(valueFactory);
        textQ.setText(questions.questions_list.get(index).text);
        textFieldA.setText(questions.questions_list.get(index).answer.A);
        textFieldB.setText(questions.questions_list.get(index).answer.B);
        textFieldC.setText(questions.questions_list.get(index).answer.C);
        textFieldD.setText(questions.questions_list.get(index).answer.D);
        editButton.setVisible(true);

        if (questions.questions_list.get(index).answer.a)
            checkA.setSelected(true);

        else if (questions.questions_list.get(index).answer.b)
            checkB.setSelected(true);

        else if (questions.questions_list.get(index).answer.c)
            checkC.setSelected(true);

        else if (questions.questions_list.get(index).answer.d)
            checkD.setSelected(true);

        if (questions.questions_list.get(index).imagePath != null) {
            imageCheck.setSelected(true);
            pathLabel.setText(questions.questions_list.get(index).imagePath);
        }

        else {
            imageCheck.setSelected(false);
            pathLabel.setText(null);
        }

        errorLabel.setText(null);
    }

    private void saveData() {
        mainPane.setDisable(true);
        SaveController output = new SaveController(questions);
        output.stage.showAndWait();
        mainPane.setDisable(false);
    }

    private void loadData() {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Binary Files", "*.bin");
        fc.getExtensionFilters().add(filter);
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            String filepath = selectedFile.getAbsolutePath();
            QuestionContainer setOfQuestions = (QuestionContainer) readObjectFromFile(filepath);
            InitQuestionsController refresh = new InitQuestionsController(stage, players, setOfQuestions);
        }
    }

    private Object readObjectFromFile(String filepath) {
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object o = objectIn.readObject();
            objectIn.close();
            return o;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
        InitQuestionsController refresh = new InitQuestionsController(stage, players, questions);
    }

    private void openLayout() {
        InitPlayersController controller;

        if (questions.questions_list.size() < 1)
            errorLabel.setText("Musisz dodać przynajmniej jedno pytanie, aby móc kontynuować!");

        else
            controller = new InitPlayersController(stage, players, questions);
    }
}
