package com.example.todo.repository;

import com.example.todo.todos.Repository.Todo;
import com.example.todo.todos.Repository.TodoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    private final Todo testTodo1 = new Todo(1L, "Test Todo 1", false);
    private final Todo testTodo2 = new Todo(2L, "Test Todo 2", false);

    @BeforeAll
    public void beforeAll() {
        todoRepository.deleteAll();
        todoRepository.save(testTodo1);
        todoRepository.save(testTodo2);
    }

    @Test
    @Order(1)
    void shouldReturnAllTodosFromRepository() {
        List<Todo> allTodos = todoRepository.findAll();

        assertThat(allTodos.size()).isEqualTo(2);
        assertThat(allTodos).isEqualTo(List.of(testTodo1, testTodo2));
    }

    @Test
    @Order(2)
    void shouldSaveTodo() {
        Todo testTodo3 = new Todo("Test Todo 3");

        todoRepository.save(testTodo3);

        List<Todo> allTodos = todoRepository.findAll();
        assertThat(allTodos.size()).isEqualTo(3);
        assertTrue(allTodos.contains(new Todo(3L, "Test Todo 3", false)));
    }

    @Test
    @Order(3)
    void shouldGetTodoFromTodoId() {
        Optional<Todo> queriedTodo = todoRepository.findById(1L);

        assertTrue(queriedTodo.isPresent());
        assertThat(queriedTodo.get()).isEqualTo(new Todo(1L, "Test Todo 1", false));
    }

    @Test
    @Order(4)
    void shouldBeNullWhenTodoIdDoesNotExist() {
        Optional<Todo> queriedTodo = todoRepository.findById(3L);

        assertTrue(queriedTodo.isEmpty());
    }

    @Test
    @Order(5)
    void shouldDeleteTodoUsingId() {
        assertThat(todoRepository.findAll().size()).isEqualTo(2);

        todoRepository.deleteById(2L);

        assertThat(todoRepository.findAll().size()).isEqualTo(1);
        assertTrue(todoRepository.findById(2L).isEmpty());
    }

    @Test
    @Order(6)
    void shouldUpdateTodoWithoutCreatingNewRow() {
        Todo todoBeforeUpdating = todoRepository.findById(2L).get();
        assertFalse(todoBeforeUpdating.isDone());
        assertThat(todoRepository.findAll().size()).isEqualTo(2);

        todoBeforeUpdating.setIsDone(true);
        todoRepository.save(todoBeforeUpdating);

        Todo todoAfterUpdating = todoRepository.findById(2L).get();
        assertTrue(todoAfterUpdating.isDone());
        assertThat(todoRepository.findAll().size()).isEqualTo(2);

    }

    @Test
    void shouldGetTodoUsingTodoName() {
        String testTodoName = testTodo2.getTodoName();
        assertEquals(todoRepository.findByTodoName(testTodoName).get(), testTodo2);
    }

    @Test
    void shouldGetTodoThatAreDone() {
        assertEquals(todoRepository.findByIsDoneTrue(), emptyList());
    }

    @Test
    void shouldGetTodoThatAreNotDone() {
        assertEquals(todoRepository.findByIsDoneFalse(), List.of(testTodo1, testTodo2));
    }
}
