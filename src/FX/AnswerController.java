package FX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.URL;
import java.util.ResourceBundle;

public class AnswerController implements Initializable {
    public final Stage stage;
    public String text;
    @FXML private Button dialogButton;
    @FXML private Label correctAnswer;

    public AnswerController(String text) {
        this.text = text;
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        correctAnswer.setText(text);

        dialogButton.setOnAction(event -> {
            stage.close();
        });
    }
}