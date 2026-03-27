package service;

import model.Question;

import java.util.Collection;
import java.util.Collections;

public interface QuestionService {
    Question add(String question, String answer);
    Question remove (String question, String answer);
    Collection<Question> getAll();
    Question getRandomQuestion();
}
