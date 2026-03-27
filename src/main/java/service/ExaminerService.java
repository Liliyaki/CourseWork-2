package service;

import model.Question;

import java.util.List;

public interface ExaminerService {
    List <Question> getQuestions (int amount);
}
