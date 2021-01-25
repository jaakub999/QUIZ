package api;

import java.io.Serializable;
import java.util.*;

public class QuestionContainer implements Comparator<Question>, Serializable {
    public List<Question> questions_list;

    public QuestionContainer() {
        questions_list = new ArrayList<Question>();
    }

    public void addQuestion(String text, String category, Level level, int points, Answer answer, String path) {
        Question question = new Question(text, category, level, points, answer, path);
        questions_list.add(question);
    }

    public void editQuestion(String text, String category, Level level, int points, Answer answer, String path, int index) {
        Question question = new Question(text, category, level, points, answer, path);
        questions_list.set(index, question);
    }

    public void removeQuestion(int index) {
        questions_list.remove(questions_list.get(index));
    }

    public void randomize() {
        Random random = new Random();

        for (int i = questions_list.size() - 1; i > 0; i--) {
            int j = random.nextInt(i);
            Question temp = questions_list.get(i);
            questions_list.set(i, questions_list.get(j));
            questions_list.set(j, temp);
        }
    }

    @Override
    public int compare(Question o1, Question o2) {
        return o1.getText().compareTo(o2.getText());
    }
}