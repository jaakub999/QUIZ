package FX;

import javafx.beans.property.SimpleStringProperty;

public class PlayersList {
    final private SimpleStringProperty player;

    public PlayersList(String player) {
        this.player = new SimpleStringProperty(player);
    }

    public String getPlayer() {
        return player.get();
    }

    public SimpleStringProperty playerProperty() {
        return player;
    }
}
