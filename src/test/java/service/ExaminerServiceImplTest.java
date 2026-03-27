package service;


import exception.InvalidAmountException;
import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {
    @Mock
    private QuestionService questionService;

    private ExaminerServiceImpl examinerService;

    @BeforeEach
    void setUp() {
        examinerService = new ExaminerServiceImpl(questionService);
    }

    @Test
    void getQuestions_ShouldReturnUniqueRandomQuestions() {
        List<Question> mockQuestions = Arrays.asList(
                new Question("Какой оператор используется для проверки равенства в Java?", "=="),
                new Question("Какой класс используется для представления строк в Java?", "String"),
                new Question("Какой оператор используется для конкатенации (соединения) строк в Java?", "+")
        );
        when(questionService.getAll()).thenReturn(mockQuestions);

        List<Question> result = examinerService.getQuestions(2);

        assertThat(result).hasSize(2);
        assertThat(result).doesNotHaveDuplicates();
        assertThat(mockQuestions).containsAll(result);
    }

    @Test
    void getQuestions_WhenAmountGreaterThanAvailable_ShouldThrowException() {
        List<Question> mockQuestions = Arrays.asList(
                new Question("Какой оператор используется для проверки равенства в Java?", "=="),
                new Question("Какой класс используется для представления строк в Java?", "String")
        );
        when(questionService.getAll()).thenReturn(mockQuestions);

        assertThatThrownBy(() -> examinerService.getQuestions(3))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessageContaining("Запрошено 3 вопросов, но доступно только 2");
    }

    @Test
    void getQuestions_WhenAmountIsZero_ShouldThrowException() {
        when(questionService.getAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> examinerService.getQuestions(0))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessageContaining("Количество вопросов должно быть положительным");
    }

    @Test
    void getQuestions_WhenAmountIsNegative_ShouldThrowException() {
        when(questionService.getAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> examinerService.getQuestions(-1))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessageContaining("Количество вопросов должно быть положительным");
    }

    @Test
    void getQuestions_ShouldReturnAllQuestionsWhenAmountEqualsTotal() {
        List<Question> mockQuestions = Arrays.asList(
                new Question("Какой оператор используется для проверки равенства в Java?", "=="),
                new Question("Какой класс используется для представления строк в Java?", "String")
        );
        when(questionService.getAll()).thenReturn(mockQuestions);

        List<Question> result = examinerService.getQuestions(2);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrderElementsOf(mockQuestions);
    }
}
