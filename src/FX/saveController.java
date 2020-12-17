package FX;

import api.QuestionContainer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class saveController implements Initializable {
    public final Stage stage;
    public QuestionContainer questions;
    @FXML private Button saveButton;
    @FXML private Button pathButton;
    @FXML private Button cancelButton;
    @FXML private Button confirmButton;
    @FXML private TextField pathTextField;
    @FXML private TextField saveTextField;
    @FXML private Text dialogText2;

    public saveController(QuestionContainer questions) {
        this.questions = questions;
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/save.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialogText2.setVisible(false);
        confirmButton.setVisible(false);
        pathButton.setOnAction(event -> chooseDirectory());
        saveButton.setOnAction(event -> writeObjectToFile());
        cancelButton.setOnAction(event -> back());
        confirmButton.setOnAction(event -> back());
    }

    private void chooseDirectory() {
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(stage);

        if (selectedDirectory != null)
            pathTextField.setText(selectedDirectory.getAbsolutePath());
    }

    private void writeObjectToFile() {
        String filepath = pathTextField.getText() + "\\" + saveTextField.getText() + ".bin";

        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(questions);
            objectOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        saveButton.setVisible(false);
        pathButton.setVisible(false);
        cancelButton.setVisible(false);
        pathTextField.setVisible(false);
        saveTextField.setVisible(false);
        confirmButton.setVisible(true);
        dialogText2.setVisible(true);
        dialogText2.setText("Zapisano pomyślnie!");
    }

    private void back() {
        stage.close();
    }
}
