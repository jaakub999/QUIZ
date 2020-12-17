package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionContainer implements Serializable {
    public List<Question> questions_list;

    public QuestionContainer() {
        questions_list = new ArrayList<Question>();
    }

    public void addQuestion(String text, int points, Answer answer, String path) {
        Question question = new Question(text, points, answer, path);
        questions_list.add(question);
    }

    public void editQuestion(String text, int points, Answer answer, String path, int index) {
        Question question = new Question(text, points, answer, path);
        questions_list.set(index, question);
    }

    public void removeQuestion(int index) {
        questions_list.remove(questions_list.get(index));
    }
}