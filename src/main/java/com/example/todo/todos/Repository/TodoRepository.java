package com.example.todo.todos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findByTodoName(String todoName);

    List<Todo> findByIsDoneTrue();

    List<Todo> findByIsDoneFalse();
}
