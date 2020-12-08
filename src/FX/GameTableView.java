package FX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GameTableView {
    final private SimpleIntegerProperty lp;
    final private SimpleStringProperty nickname;
    final private SimpleIntegerProperty score;
    final private SimpleIntegerProperty lifebuoy;

    public GameTableView(int lp, String nickname, int score, int lifebuoy) {
        this.lp = new SimpleIntegerProperty(lp);
        this.nickname = new SimpleStringProperty(nickname);
        this.score = new SimpleIntegerProperty(score);
        this.lifebuoy = new SimpleIntegerProperty(lifebuoy);
    }

    public int getLp() {
        return lp.get();
    }

    public SimpleIntegerProperty lpProperty() {
        return lp;
    }

    public String getNickname() {
        return nickname.get();
    }

    public SimpleStringProperty nicknameProperty() {
        return nickname;
    }

    public int getScore() {
        return score.get();
    }

    public SimpleIntegerProperty scoreProperty() {
        return score;
    }

    public int getLifebuoy() {
        return lifebuoy.get();
    }

    public SimpleIntegerProperty lifebuoyProperty() {
        return lifebuoy;
    }
}