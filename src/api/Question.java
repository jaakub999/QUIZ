package api;

import java.io.Serializable;

public class Question implements Serializable {
    private String text, imagePath, category;
    private int points;
    private Level level;
    private Answer answer;

    public Question(String text,
                    String category,
                    Level level,
                    int points,
                    Answer answer,
                    String imagePath)
    {
        this.text = text;
        this.category = category;
        this.level = level;
        this.points = points;
        this.answer = answer;
        this.imagePath = imagePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}