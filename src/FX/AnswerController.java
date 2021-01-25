package FX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AnswerController implements Initializable {
    public final Stage stage;
    public String text;

    @FXML private Text dialogText1;
    @FXML private Text dialogText2;
    @FXML private Button dialogButton;
    @FXML private Label correctAnswer;

    public AnswerController(String text) {
        this.text = text;
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/answerDialog.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (text != null) {
            correctAnswer.setText("Poprawna odpowiedź to:  " + text);
            dialogText1.setVisible(true);
        }

        else
            dialogText2.setVisible(true);

        dialogButton.setOnAction(event -> {
            stage.close();
        });
    }
}