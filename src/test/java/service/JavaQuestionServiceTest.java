package service;


import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JavaQuestionServiceTest {
    private JavaQuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new JavaQuestionService();
    }

    @Test
    void addQuestion_ShouldAddAndReturnQuestion() {
        Question result = questionService.add("Что такое Java?", "Язык программирования");

        assertThat(result).isNotNull();
        assertThat(result.getQuestion()).isEqualTo("Что такое Java?");
        assertThat(result.getAnswer()).isEqualTo("Язык программировани");
        assertThat(questionService.getAll()).hasSize(1);
    }

    @Test
    void addQuestion_WhenDuplicate_ShouldThrowException() {
        questionService.add("Что такое Java?", "Язык программирования");

        assertThatThrownBy(() -> questionService.add("Что такое Java?", "Язык программирования"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Такой вопрос уже существует");
    }

    @Test
    void removeQuestion_ShouldRemoveAndReturnQuestion() {
        questionService.add("Что такое Java?", "Язык программирования");

        Question removed = questionService.remove("Что такое Java?", "Язык программирования");

        assertThat(removed).isNotNull();
        assertThat(questionService.getAll()).isEmpty();
    }

    @Test
    void removeQuestion_WhenQuestionNotFound_ShouldThrowException() {
        assertThatThrownBy(() -> questionService.remove("Что такое полиморфизм?", "Полиморфизм — это способность программы идентично использовать объекты с одинаковым интерфейсом без информации о конкретном типе этого объекта. Как говорится, один интерфейс — множество реализаций."))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Вопрос не найден");
    }

    @Test
    void getAll_ShouldReturnAllQuestions() {
        questionService.add("Как объявить класс в Java?", "public class MyClass {}");
        questionService.add("Какой метод является точкой входа в Java программу?", "public static void main(String[] args)");

        Collection<Question> all = questionService.getAll();

        assertThat(all).hasSize(2);
    }

    @Test
    void getAll_ShouldReturnQuestionsSortedByQuestionText() {
        questionService.add("Какой метод используется для вывода текста в консоль в Java?", "System.out.println()");
        questionService.add("Какой из следующих вариантов является правильным объявлением целочисленной переменной?", "int number = 10;");
        questionService.add("Какой тип данных используется для хранения целых чисел в Java?", "int");

        Collection<Question> all = questionService.getAll();
        List<Question> list = new ArrayList<>(all);

        assertThat(list.get(0).getQuestion()).isEqualTo("Какой тип данных используется для хранения целых чисел в Java?");
        assertThat(list.get(1).getQuestion()).isEqualTo("Какой метод используется для вывода текста в консоль в Java?");
        assertThat(list.get(2).getQuestion()).isEqualTo("Какой из следующих вариантов является правильным объявлением целочисленной переменной?");
    }

    @Test
    void getRandomQuestion_ShouldReturnRandomQuestion() {
        questionService.add("Как объявить класс в Java?", "public class MyClass {}");
        questionService.add("Какой метод является точкой входа в Java программу?", "public static void main(String[] args)");

        Question random = questionService.getRandomQuestion();

        assertThat(random).isNotNull();
        assertThat(random.getQuestion()).isIn("Как объявить класс в Java?", "Какой метод является точкой входа в Java программу?");
    }

    @Test
    void getRandomQuestion_WhenNoQuestions_ShouldThrowException() {
        assertThatThrownBy(() -> questionService.getRandomQuestion())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Нет доступных вопросов");
    }
}
