package com.example.todo.todos.view;

import com.example.todo.exceptions.TodoAlreadyExistsException;
import com.example.todo.exceptions.TodoNotFoundException;
import com.example.todo.todos.Repository.Todo;
import com.example.todo.todos.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/username")
    Map<String, Object> login(Principal principal) {
        String username = principal.getName();
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", username);
        return userDetails;
    }

    @GetMapping("/todos")
    List<Todo> getTodos(@RequestParam(required = false, name = "isDone") String isDone) {
        return todoService.getAllTodos(isDone);
    }

    @PostMapping("/todos")
    Todo addTodo(@RequestBody Todo todoToBeAdded) throws TodoAlreadyExistsException {
        return todoService.addTodo(todoToBeAdded);
    }

    @GetMapping("/todos/{todoId}")
    Todo getTodoUsingTodoId(@PathVariable Long todoId) throws TodoNotFoundException {
        Optional<Todo> queriedTodo = todoService.getTodoFromTodoId(todoId);
        if (queriedTodo.isEmpty()) throw new TodoNotFoundException();
        return queriedTodo.get();
    }

    @DeleteMapping("/todos/{todoId}")
    void deleteTodoUsingTodoId(@PathVariable int todoId) {
        todoService.deleteTodoUsingId((long) todoId);
    }

    @PatchMapping("/todos/{todoId}")
    Todo updateTodoUsingTodoId(@PathVariable int todoId, @RequestBody Map<String, Object> request) throws TodoNotFoundException {
        return todoService.updateTodo((long) todoId, request);
    }

}
