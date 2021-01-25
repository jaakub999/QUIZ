package FX;

import api.PlayerContainer;
import api.Question;
import api.QuestionContainer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class OptionsController implements Initializable {
    public final Stage stage;
    public PlayerContainer players;
    public QuestionContainer questions;
    private final ToggleGroup group1;
    private final ToggleGroup group2;
    private final ToggleGroup group3;
    private final ToggleGroup group4;

    @FXML private Pane tablePane;
    @FXML private HBox checkPointsBox;
    @FXML private HBox checkLevelBox;
    @FXML private HBox checkCategoryBox;
    @FXML private RadioButton option1;
    @FXML private RadioButton option2;
    @FXML private RadioButton option3;
    @FXML private RadioButton option4;
    @FXML private RadioButton checkPoints1;
    @FXML private RadioButton checkPoints2;
    @FXML private RadioButton checkPoints3;
    @FXML private RadioButton checkLevel1;
    @FXML private RadioButton checkLevel2;
    @FXML private RadioButton checkLevel3;
    @FXML private RadioButton checkCategory1;
    @FXML private RadioButton checkCategory2;
    @FXML private RadioButton checkCategory3;
    @FXML private CheckBox checkPoints;
    @FXML private CheckBox checkLevel;
    @FXML private CheckBox checkCategory;
    @FXML private Button nextButton;
    @FXML private Button backButton;

    public OptionsController(Stage stage,
                             PlayerContainer players,
                             QuestionContainer questions)
    {
        this.stage = stage;
        this.players = players;
        this.questions = questions;
        group1 = new ToggleGroup();
        group2 = new ToggleGroup();
        group3 = new ToggleGroup();
        group4 = new ToggleGroup();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/gameOptions.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        option1.setToggleGroup(group1);
        option2.setToggleGroup(group1);
        option3.setToggleGroup(group1);
        option4.setToggleGroup(group1);

        checkPoints1.setToggleGroup(group2);
        checkPoints2.setToggleGroup(group2);
        checkPoints3.setToggleGroup(group2);

        checkLevel1.setToggleGroup(group3);
        checkLevel2.setToggleGroup(group3);
        checkLevel3.setToggleGroup(group3);

        checkCategory1.setToggleGroup(group4);
        checkCategory2.setToggleGroup(group4);
        checkCategory3.setToggleGroup(group4);

        option4.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                tablePane.setVisible(option4.isSelected());
            }
        });

        checkPoints.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkPointsBox.setDisable(!checkPoints.isSelected());
            }
        });

        checkLevel.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkLevelBox.setDisable(!checkLevel.isSelected());
            }
        });

        checkCategory.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkCategoryBox.setDisable(!checkCategory.isSelected());
            }
        });

        nextButton.setOnAction(event -> openLayout());
        backButton.setOnAction(event -> backToPreviousLayout());
    }

    private void openLayout() {
        if (option2.isSelected())
            questions.questions_list.sort(new QuestionContainer());

        else if (option3.isSelected()) {
            questions.randomize();
        }

        else if (option4.isSelected()) {
            int pointsPriority = 0;
            int levelPriority = 0;
            int categoryPriority = 0;

            if (checkPoints.isSelected()) {
                if (checkPoints1.isSelected())
                    pointsPriority = 1;

                else if (checkPoints2.isSelected())
                    pointsPriority = 2;

                else if (checkPoints3.isSelected())
                    pointsPriority = 3;
            }

            if (checkLevel.isSelected()) {
                if (checkLevel1.isSelected())
                    levelPriority = 1;

                else if (checkLevel2.isSelected())
                    levelPriority = 2;

                else if (checkLevel3.isSelected())
                    levelPriority = 3;
            }

            if (checkCategory.isSelected()) {
                if (checkCategory1.isSelected())
                    categoryPriority = 1;

                else if (checkCategory2.isSelected())
                    categoryPriority = 2;

                else if (checkCategory3.isSelected())
                    categoryPriority = 3;
            }

            if (checkPoints.isSelected() && checkLevel.isSelected() && checkCategory.isSelected()) {
                if (pointsPriority < levelPriority) {
                    if (levelPriority < categoryPriority)
                        questions.questions_list.sort(Comparator.comparing(Question::getPoints)
                                                                .thenComparing(Question::getLevel)
                                                                .thenComparing(Question::getCategory));

                    else
                        questions.questions_list.sort(Comparator.comparing(Question::getPoints)
                                                                .thenComparing(Question::getCategory)
                                                                .thenComparing(Question::getLevel));
                }

                else if (levelPriority < pointsPriority) {
                    if (pointsPriority < categoryPriority)
                        questions.questions_list.sort(Comparator.comparing(Question::getLevel)
                                                                .thenComparing(Question::getPoints)
                                                                .thenComparing(Question::getCategory));

                    else
                        questions.questions_list.sort(Comparator.comparing(Question::getLevel)
                                                                .thenComparing(Question::getCategory)
                                                                .thenComparing(Question::getPoints));
                }

                else if (categoryPriority < pointsPriority) {
                    if (pointsPriority < levelPriority)
                        questions.questions_list.sort(Comparator.comparing(Question::getCategory)
                                .thenComparing(Question::getPoints)
                                .thenComparing(Question::getLevel));

                    else
                        questions.questions_list.sort(Comparator.comparing(Question::getCategory)
                                .thenComparing(Question::getLevel)
                                .thenComparing(Question::getPoints));
                }
            }

            else if (checkPoints.isSelected() && checkLevel.isSelected()) {
                if (pointsPriority < levelPriority)
                    questions.questions_list.sort(Comparator.comparing(Question::getPoints)
                                                            .thenComparing(Question::getLevel));

                else
                    questions.questions_list.sort(Comparator.comparing(Question::getLevel)
                                                            .thenComparing(Question::getPoints));
            }

            else if (checkPoints.isSelected() && checkCategory.isSelected()) {
                if (pointsPriority < categoryPriority)
                    questions.questions_list.sort(Comparator.comparing(Question::getPoints)
                                                            .thenComparing(Question::getCategory));

                else
                    questions.questions_list.sort(Comparator.comparing(Question::getCategory)
                                                            .thenComparing(Question::getPoints));
            }

            else if (checkCategory.isSelected() && checkLevel.isSelected()) {
                if (levelPriority < categoryPriority)
                    questions.questions_list.sort(Comparator.comparing(Question::getLevel)
                                                            .thenComparing(Question::getCategory));

                else
                    questions.questions_list.sort(Comparator.comparing(Question::getCategory)
                                                            .thenComparing(Question::getLevel));
            }

            else if (checkPoints.isSelected())
                questions.questions_list.sort(Comparator.comparing(Question::getPoints));

            else if (checkLevel.isSelected())
                questions.questions_list.sort(Comparator.comparing(Question::getLevel));

            else if (checkCategory.isSelected())
                questions.questions_list.sort(Comparator.comparing(Question::getCategory));
        }

        GameController.ITERATION = 0;
        GameController controller = new GameController(stage, players, questions);
    }

    private void backToPreviousLayout() {
        InitLifesController controller = new InitLifesController(stage, players, questions);
    }
}