package service;

import model.Question;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class JavaQuestionService implements QuestionService {
    private final Set<Question> questions = new ConcurrentSkipListSet<>(Comparator.comparing(Question::getQuestion));

    @Override
    public Question add(String questionText, String answerText) {
        Question question = new Question(questionText, answerText);
        if (questions.contains(question)) {
            throw new IllegalArgumentException("Такой вопрос уже существует " + questionText);
        }
        questions.add(question);
        return question;
    }

    @Override
    public Question remove(String questionText, String answerText) {
        Question question = new Question(questionText, answerText);
        if (questions.contains(question)) {
            throw new NoSuchElementException("Вопрос не найден " + questionText);
        }
        questions.remove(question);
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return new ArrayList<>(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new NoSuchElementException("Нет доступных вопросов");
        }
        List<Question> questionList = new ArrayList<>(questions);
        Random random = new Random();
        int randomIndex = random.nextInt(questionList.size());
        return questionList.get(randomIndex);
    }


}
