package FX;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOverController implements Initializable  {
    public final Stage stage;
    private final ObservableList<GameTableView> data;

    @FXML private Label label11;
    @FXML private Label label12;
    @FXML private Label label13;
    @FXML private Label label21;
    @FXML private Label label22;
    @FXML private Label label23;
    @FXML private Label label31;
    @FXML private Label label32;
    @FXML private Label label33;
    @FXML private Button dialogButton;

    public GameOverController(ObservableList<GameTableView> data) {
        this.data = data;
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/gameOver.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dialogButton.setOnAction(event -> stage.close());

        if (data.size() >= 1) {
            label11.setText("1 miejsce:");
            label21.setText(data.get(0).getNickname());
            label31.setText(data.get(0).getScore() + "pkt");

            if (data.size() >= 2) {
                label22.setText(data.get(1).getNickname());
                label32.setText(data.get(1).getScore() + "pkt");

                if (data.get(1).getScore() != data.get(0).getScore())
                    label12.setText("2 miejsce:");

                if (data.size() >= 3){
                    label23.setText(data.get(2).getNickname());
                    label33.setText(data.get(2).getScore() + "pkt");

                    if (data.get(2).getScore() != data.get(1).getScore())
                        label13.setText("3 miejsce:");
                }
            }
        }
    }
}