package com.example.todo.todos.service;

import com.example.todo.todos.Repository.Todo;
import com.example.todo.todos.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo addTodo(Todo todoToBeAdded) {
        return todoRepository.save(todoToBeAdded);
    }

    public Optional<Todo> getTodoFromTodoId(Long todoId) {
        return todoRepository.findById(todoId);
    }

    public void deleteTodoUsingId(Long todoId) {
        if (todoRepository.existsById(todoId)) {
            todoRepository.deleteById(todoId);
        }
    }
}
