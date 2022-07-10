package com.example.todo.todos.Repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    private Long id;

    @JsonProperty
    @NotNull
    @Column(name = "todo_name")
    private String todoName;

    @JsonProperty(value = "isDone")
    @NotNull
    @Column(name = "is_done", columnDefinition = "BIT")
    private boolean isDone;

    public Todo() {
    }

    public Todo(String todoName) {
        this.todoName = todoName;
        this.isDone = false;
    }

    public Todo(Long id, String todoName, boolean isDone) {
        this.id = id;
        this.todoName = todoName;
        this.isDone = isDone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    @JsonProperty(value = "isDone")
    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(todoName, todo.todoName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, todoName);
    }

    @Override
    public String toString() {
        return "Todo no: " + id + ") " + todoName + "/" + isDone;
    }
}
