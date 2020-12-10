package api;

import java.util.*;

public class PlayerContainer {
    public Map<String, Player> players_list;

    public PlayerContainer() {
        players_list = new HashMap<String, Player>();
    }

    public void addPlayer(String nickname) {
        Player player = new Player(nickname);
        players_list.put(nickname, player);
    }

    public void removePlayer(String nickname) {
        players_list.remove(nickname);
    }
}