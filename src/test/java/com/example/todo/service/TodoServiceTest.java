package com.example.todo.service;

import com.example.todo.todos.Repository.Todo;
import com.example.todo.todos.Repository.TodoRepository;
import com.example.todo.todos.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    private TodoRepository todoRepository;
    private TodoService todoService;

    @BeforeEach
    void beforeEach() {
        todoRepository = mock(TodoRepository.class);
        todoService = new TodoService(todoRepository);
    }

    @Test
    void shouldDisplayAllTheTodosFromTheRepository() {
        Todo todo1 = new Todo(1L, "Todo1", false);
        Todo todo2 = new Todo(2L, "Todo2", true);
        when(todoRepository.findAll()).thenReturn(List.of(todo1, todo2));

        assertThat(todoService.getAllTodos()).isEqualTo(List.of(todo1, todo2));
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void shouldAddTodoIntoTheRepo() {
        Todo testTodo = new Todo(1L, "Todo1", false);
        when(todoRepository.save(testTodo)).thenReturn(testTodo);

        assertThat(todoService.addTodo(testTodo)).isEqualTo(testTodo);
        verify(todoRepository, times(1)).save(testTodo);
    }

    @Test
    void shouldGetTodoUsingTodoId() {
        Todo testTodo = new Todo(1L, "Todo1", false);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(testTodo));

        assertThat(todoService.getTodoFromTodoId(1L)).isEqualTo(Optional.of(testTodo));
        verify(todoRepository, times(1)).findById(1L);
    }
}
