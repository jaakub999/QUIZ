package api;

public class Question {
    public String text, imagePath;
    public int points;
    public Answer answer;

    public Question(String text, int points, Answer answer, String imagePath) {
        this.text = text;
        this.points = points;
        this.answer = answer;
        this.imagePath = imagePath;
    }
}