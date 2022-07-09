package com.example.todo.view;

import com.example.todo.todos.Repository.Todo;
import com.example.todo.todos.Repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void beforeEach() {
        todoRepository.deleteAll();
    }

    @Test
    void shouldReturnUsername() throws Exception {
        mockMvc.perform(get("/username")
                        .with(httpBasic("saran", "saran")))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\":\"saran\"}"));
    }

    @Test
    void shouldReturnAllTodos() throws Exception {
        Todo testTodo = new Todo(1L, "TestTodo-1", false);
        todoRepository.save(testTodo);

        mockMvc.perform(get("/todos")
                        .with(httpBasic("saran", "saran")))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"todoName\":\"TestTodo-1\",\"isDone\":false}]"));
    }
}
