package api;

public class Player {
    public String nickname;
    public int score;
    public int lifes;

    public Player(String nickname) {
        this.nickname = nickname;
        score = 0;
        lifes = 0;
    }

    public void addScore(int x) {
        score += x;
    }
}