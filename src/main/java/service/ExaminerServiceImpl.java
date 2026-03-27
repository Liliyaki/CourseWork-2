package service;

import exception.InvalidAmountException;
import model.Question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService questionService;
    public ExaminerServiceImpl (QuestionService questionService) {
        this.questionService = questionService;
    }
    @Override
    public List <Question> getQuestions (int amount) {
        Collection <Question> allQuestions = questionService.getAll();
        if (amount > allQuestions.size()) {
            throw new InvalidAmountException("Запрошено " + amount + "вопросов, но возможно порлучитиь только " + allQuestions.size() + " вопросов");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Должно быть положительное количество вопросов");
        }
        List<Question> randomQuestions = new ArrayList<>();
        List<Question> remainingQuestions = new ArrayList<>(allQuestions);
        Random random = new Random();

        for (int i = 0; i < amount; i++) {
            int randomIndex = random.nextInt(remainingQuestions.size());
            randomQuestions.add(remainingQuestions.get(randomIndex));
            remainingQuestions.remove(randomIndex);
        }

        return randomQuestions;
    }
}
