package api;

public class Question {
    public String text;
    public int points;
    public Answer answer;

    public Question(String text, int points, Answer answer) {
        this.text = text;
        this.points = points;
        this.answer = answer;
    }
}