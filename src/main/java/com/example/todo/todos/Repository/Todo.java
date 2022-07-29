package com.example.todo.todos.Repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @JsonProperty
    @Column(name = "todo_name", nullable = false, unique = true)
    private String todoName;

    @JsonProperty(value = "isDone")
    @NotNull
    @Column(name = "is_done", columnDefinition = "Boolean DEFAULT false")
    private boolean isDone;

    @JsonProperty(value = "category")
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private TodoCategory category = TodoCategory.TODOS;

    @JsonProperty(value = "description")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Todo() {
    }

    public Todo(String todoName) {
        this.todoName = todoName;
    }

    public Todo(Long id, String todoName, boolean isDone) {
        this.id = id;
        this.todoName = todoName;
        this.isDone = isDone;
    }

    public Todo(Long id, String todoName, boolean isDone, TodoCategory category, String description) {
        this.id = id;
        this.todoName = todoName;
        this.isDone = isDone;
        this.category = category;
        this.description = description;
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

    public TodoCategory getCategory() {
        return category;
    }

    public void setCategory(TodoCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "Todo no: " + id + ") " + todoName + "/" + category + "---" + isDone;
    }
}
