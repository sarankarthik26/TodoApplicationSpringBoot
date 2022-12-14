package com.example.todo.handlers;

import com.example.todo.exceptions.TodoAlreadyExistsException;
import com.example.todo.exceptions.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTodoNotFoundException() {
        ErrorResponse error = new ErrorResponse("Todo does not exist for the given ID", new ArrayList<>());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TodoAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleTodoAlreadyExistsException() {
        ErrorResponse error = new ErrorResponse("Todo already exists for the given name. Check your todos or try with another name", new ArrayList<>());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
