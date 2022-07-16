package com.example.todo.service;

import com.example.todo.exceptions.TodoNotFoundException;
import com.example.todo.todos.Repository.Todo;
import com.example.todo.todos.Repository.TodoCategory;
import com.example.todo.todos.Repository.TodoRepository;
import com.example.todo.todos.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void shouldDeleteTodoUsingTodoId() {
        when(todoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(todoRepository).deleteById(1L);

        todoService.deleteTodoUsingId(1L);

        verify(todoRepository, times(1)).existsById(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldNotHitRepoWhileDeletingTodoWhichDoesNotExist() {
        when(todoRepository.existsById(1L)).thenReturn(false);

        todoService.deleteTodoUsingId(1L);

        verify(todoRepository, times(1)).existsById(1L);
        verify(todoRepository, times(0)).deleteById(1L);
    }

    @Test
    void shouldUpdateIsDoneForTodo() throws TodoNotFoundException {
        HashMap<String, Object> testRequest = new HashMap<>();
        testRequest.put("isDone", true);
        Todo testTodo = new Todo(1L, "Todo1", false);
        Todo updatedTodo = new Todo(1L, "Todo1", true);

        when(todoRepository.findById(1L)).thenReturn(Optional.of(testTodo));
        when(todoRepository.save(updatedTodo)).thenReturn(updatedTodo);

        todoService.updateTodo(1L, testRequest);

        assertTrue(testTodo.isDone());
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(updatedTodo);
    }

    @Test
    void shouldThrowErrorWhenTodoIdDoesNotExist() {
        HashMap<String, Object> testRequest = new HashMap<>();

        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.updateTodo(1L, testRequest));

        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(0)).save(any(Todo.class));

    }

    @Test
    void shouldUpdateDescriptionForTodo() throws TodoNotFoundException {
        HashMap<String, Object> testRequest = new HashMap<>();
        testRequest.put("description", "Test description");
        Todo testTodo = new Todo(1L, "Todo1", false);
        Todo updatedTodo = new Todo(1L, "Todo1", false, TodoCategory.TODOS, "Test description");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(testTodo));
        when(todoRepository.save(updatedTodo)).thenReturn(updatedTodo);

        todoService.updateTodo(1L, testRequest);

        assertEquals(testTodo.getDescription(), "Test description");
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(updatedTodo);
    }
}
