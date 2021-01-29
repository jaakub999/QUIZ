package FX;

import api.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
    private final SpinnerValueFactory<Level> valueFactory;
    private final ToggleGroup group;

    @FXML private AnchorPane mainPane;
    @FXML private TextArea textQ;
    @FXML private TextField textFieldA;
    @FXML private TextField textFieldB;
    @FXML private TextField textFieldC;
    @FXML private TextField textFieldD;
    @FXML private TextField categoryField;
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
    @FXML private Spinner<Level> levelSpinner;
    @FXML private Slider pointsSlider;
    @FXML private ComboBox<String> questionComboBox;
    @FXML private Label number;
    @FXML private Label pathLabel;
    @FXML private Label errorLabel;
    @FXML private Label pointsValue;
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
        ObservableList<Level> levels = FXCollections.observableArrayList(Level.EASY, Level.MEDIUM, Level.HARD, Level.VERY_HARD);
        valueFactory =  new SpinnerValueFactory.ListSpinnerValueFactory<Level>(levels);
        valueFactory.setValue(Level.EASY);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/questions.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText("Błąd!");
                alert.setContentText("Wybrany plik nie jest obsługiwany przez program lub jest niekompatybilny z obecną wersją programu!");
                alert.showAndWait();
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        levelSpinner.setValueFactory(valueFactory);

        pointsSlider.setMax(10);
        pointsSlider.setMin(1);
        pointsSlider.setBlockIncrement(1);

        pointsSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                pointsValue.setText(String.valueOf(Math.round((Double) newValue)));
            }
        });

        checkA.setToggleGroup(group);
        checkB.setToggleGroup(group);
        checkC.setToggleGroup(group);
        checkD.setToggleGroup(group);

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
        saveButton.setTooltip(new Tooltip("Zapisz utworzony zestaw pytań"));
        loadButton.setTooltip(new Tooltip("Wczytaj zestaw pytań"));

        for (int i = 0; i < questions.questions_list.size(); i++) {
            if (questions.questions_list.get(i).getText().length() < 12)
                questionID.add(i + 1 + ". " + questions.questions_list.get(i).getText());

            else
                questionID.add((i + 1 + ". " + questions.questions_list.get(i).getText()).substring(0, 12) + "...");
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
        int points = (int) pointsSlider.getValue();
        String text = textQ.getText().replaceAll("\n", System.getProperty("line.separator"));
        String A = textFieldA.getText();
        String B = textFieldB.getText();
        String C = textFieldC.getText();
        String D = textFieldD.getText();
        String category;
        Answer answer = new Answer(A, B, C, D);
        Level level = levelSpinner.getValue();

        if (!categoryField.getText().equals(""))
            category = categoryField.getText();

        else
            category = "brak";

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
                    questions.addQuestion(text, category, level, points, answer, pathLabel.getText());
                    InitQuestionsController refresh = new InitQuestionsController(stage, players, questions);
                }

                else
                    errorLabel.setText("Nie wybrałeś obrazka!");
            }

            else {
                questions.addQuestion(text, category, level, points, answer, null);
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
        int points = (int) pointsSlider.getValue();
        String text = textQ.getText().replaceAll("\n", System.getProperty("line.separator"));
        String A = textFieldA.getText();
        String B = textFieldB.getText();
        String C = textFieldC.getText();
        String D = textFieldD.getText();
        String category;
        Answer answer = new Answer(A, B, C, D);
        Level level = levelSpinner.getValue();

        if (!categoryField.getText().equals(""))
            category = categoryField.getText();

        else
            category = "brak";

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
                    questions.editQuestion(text, category, level, points, answer, pathLabel.getText(), index);
                    InitQuestionsController refresh = new InitQuestionsController(stage, players, questions);
                }

                else
                    errorLabel.setText("Nie wybrałeś obrazka!");
            }

            else {
                questions.editQuestion(text, category, level, points, answer, null, index);
                InitQuestionsController refresh = new InitQuestionsController(stage, players, questions);
            }
        }
    }

    private void chooseQuestion() {
        int index = questionComboBox.getSelectionModel().getSelectedIndex();
        valueFactory.setValue(questions.questions_list.get(index).getLevel());
        levelSpinner.setValueFactory(valueFactory);
        pointsSlider.setValue(questions.questions_list.get(index).getPoints());
        textQ.setText(questions.questions_list.get(index).getText());
        textFieldA.setText(questions.questions_list.get(index).getAnswer().A);
        textFieldB.setText(questions.questions_list.get(index).getAnswer().B);
        textFieldC.setText(questions.questions_list.get(index).getAnswer().C);
        textFieldD.setText(questions.questions_list.get(index).getAnswer().D);
        editButton.setVisible(true);

        if (questions.questions_list.get(index).getAnswer().a)
            checkA.setSelected(true);

        else if (questions.questions_list.get(index).getAnswer().b)
            checkB.setSelected(true);

        else if (questions.questions_list.get(index).getAnswer().c)
            checkC.setSelected(true);

        else if (questions.questions_list.get(index).getAnswer().d)
            checkD.setSelected(true);

        if (questions.questions_list.get(index).getImagePath() != null) {
            imageCheck.setSelected(true);
            pathLabel.setText(questions.questions_list.get(index).getImagePath());
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
