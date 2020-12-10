package api;

import java.util.ArrayList;
import java.util.List;

public class QuestionContainer {
    public List<Question> questions_list;

    public QuestionContainer() {
        questions_list = new ArrayList<Question>();
    }

    public void addQuestion(String text, int points, Answer answer, String path) {
        Question question = new Question(text, points, answer, path);
        questions_list.add(question);
    }
}