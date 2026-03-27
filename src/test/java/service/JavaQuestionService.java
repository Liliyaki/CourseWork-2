package service;

import com.example.exam.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import java.util.NoSuchElementException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JavaQuestionServiceTest {
    private JavaQuestionService questionService;;

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
        assertThat(result.getAnswer()).isEqualTo("Язык программирования");
        assertThat(questionService.getAll()).hasSize(1);
    }

    @Test
    void addQuestion_WhenDuplicate_ShouldThrowException() {
        questionService.add("What is Java?", "A programming language");

        assertThatThrownBy(() -> questionService.add("What is Java?", "A programming language"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Такой вопрос уже существует");
    }

    @Test
    void removeQuestion_ShouldRemoveAndReturnQuestion() {
        questionService.add("What is Java?", "A programming language");

        Question removed = questionService.remove("What is Java?", "A programming language");

        assertThat(removed).isNotNull();
        assertThat(questionService.getAll()).isEmpty();
    }

    @Test
    void removeQuestion_WhenQuestionNotFound_ShouldThrowException() {
        assertThatThrownBy(() -> questionService.remove("Non existent", "Answer"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Вопрос не найден");
    }

    @Test
    void getAll_ShouldReturnAllQuestions() {
        questionService.add("Q1", "A1");
        questionService.add("Q2", "A2");

        Collection<Question> all = questionService.getAll();

        assertThat(all).hasSize(2);
    }

    @Test
    void getAll_ShouldReturnQuestionsSortedByQuestionText() {
        questionService.add("B Question", "Answer B");
        questionService.add("A Question", "Answer A");
        questionService.add("C Question", "Answer C");

        Collection<Question> all = questionService.getAll();
        List<Question> list = new ArrayList<>(all);

        assertThat(list.get(0).getQuestion()).isEqualTo("A Question");
        assertThat(list.get(1).getQuestion()).isEqualTo("B Question");
        assertThat(list.get(2).getQuestion()).isEqualTo("C Question");
    }

    @Test
    void getRandomQuestion_ShouldReturnRandomQuestion() {
        questionService.add("Q1", "A1");
        questionService.add("Q2", "A2");

        Question random = questionService.getRandomQuestion();

        assertThat(random).isNotNull();
        assertThat(random.getQuestion()).isIn("Q1", "Q2");
    }

    @Test
    void getRandomQuestion_WhenNoQuestions_ShouldThrowException() {
        assertThatThrownBy(() -> questionService.getRandomQuestion())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Нет доступных вопросов");
    }
}
