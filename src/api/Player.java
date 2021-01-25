package api;

public class Player {
    private String nickname;
    private int score;
    private int chances;

    public Player(String nickname) {
        this.nickname = nickname;
        score = 0;
        chances = 1;
    }

    public void addScore(int x) {
        score += x;
    }

    public void chanceDecrement() {
        chances--;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getChances() {
        return chances;
    }

    public void setChances(int chances) {
        this.chances = chances;
    }
}