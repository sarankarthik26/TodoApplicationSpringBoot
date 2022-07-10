package com.example.todo.repository;

import com.example.todo.todos.Repository.Todo;
import com.example.todo.todos.Repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    private Todo testTodo1 = new Todo("Test Todo 1");
    private Todo testTodo2 = new Todo("Test Todo 2");

    @BeforeEach
    public void beforeAll() {
        todoRepository.deleteAll();
        todoRepository.save(testTodo1);
        todoRepository.save(testTodo2);
    }

    @Test
    void shouldReturnAllTodosFromRepository() {
        List<Todo> allTodos = todoRepository.findAll();

        assertThat(allTodos.size()).isEqualTo(2);
        assertThat(allTodos).isEqualTo(List.of(testTodo1, testTodo2));
    }

    @Test
    void shouldSaveTodo() {
        Todo testTodo3 = new Todo("Test Todo 3");

        todoRepository.save(testTodo3);

        List<Todo> allTodos = todoRepository.findAll();
        assertThat(allTodos.size()).isEqualTo(3);
        assertTrue(allTodos.contains(new Todo(3L, "Test Todo 3", false)));
    }
}
