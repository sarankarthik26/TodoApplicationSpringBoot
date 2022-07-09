package com.example.todo.repository;

import com.example.todo.todos.Repository.Todo;
import com.example.todo.todos.Repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    private Todo testTodo1 = new Todo(1L, "Todo test 1", false);
    private Todo testTodo2 = new Todo(2L, "Todo test 2", true);

    @BeforeEach
    public void beforeAll() {
        todoRepository.deleteAll();
        todoRepository.save(testTodo1);
        todoRepository.save(testTodo2);
    }

    @Test
    void shouldReturnAllTodosFromRepository() {
        assertThat(todoRepository.findAll().size()).isEqualTo(2);
        assertThat(todoRepository.findAll()).isEqualTo(List.of(testTodo1, testTodo2));
    }
}
