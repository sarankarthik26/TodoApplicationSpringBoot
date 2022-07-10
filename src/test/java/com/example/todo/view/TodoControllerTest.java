package com.example.todo.view;

import com.example.todo.todos.Repository.Todo;
import com.example.todo.todos.Repository.TodoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeAll
    void beforeAll() {
        todoRepository.deleteAll();
        Todo testTodo1 = new Todo("TestTodo-1");
        Todo testTodo2 = new Todo("TestTodo-2");
        todoRepository.saveAll(List.of(testTodo1, testTodo2));
    }

    @Test
    @Order(1)
    void shouldReturnUsername() throws Exception {
        mockMvc.perform(get("/username")
                        .with(httpBasic("saran", "saran")))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\":\"saran\"}"));
    }

    @Test
    @Order(2)
    void shouldReturnAllTodos() throws Exception {
        mockMvc.perform(get("/todos")
                        .with(httpBasic("saran", "saran")))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"id\":1,\"todoName\":\"TestTodo-1\",\"isDone\":false}," +
                                "{\"id\":2,\"todoName\":\"TestTodo-2\",\"isDone\":false}]"));
    }

    @Test
    @Order(3)
    void shouldAddTodoForPostRequest() throws Exception {
        mockMvc.perform(post("/todos")
                        .with(httpBasic("saran", "saran"))
                        .contentType("application/json")
                        .content("{\"todoName\":\"TestTodo-3\"}"))
                .andExpect(status().isOk());

        assertThat(todoRepository.findAll().size()).isEqualTo(3);
        assertTrue(todoRepository.findAll().contains(new Todo(3L, "TestTodo-3", false)));
    }

    @Test
    @Order(4)
    void shouldGetTodoUsingItsId() throws Exception {
        mockMvc.perform(get("/todos/1")
                        .with(httpBasic("saran", "saran")))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"todoName\":\"TestTodo-1\",\"isDone\":false}"));
    }

    @Test
    @Order(5)
    void shouldThrowErrorWhenTodoDoesNotExist() throws Exception {
        mockMvc.perform(get("/todos/4")
                        .with(httpBasic("saran", "saran")))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"Todo does not exist for the given ID\",\"details\":[]}"));
    }

    @Test
    @Order(6)
    void shouldDeleteTodoUsingItsId() throws Exception {
        mockMvc.perform(delete("/todos/3")
                        .with(httpBasic("saran", "saran")))
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    void shouldNotThrowErrorWhenDeletingTodoThatDoesNotExist() throws Exception {
        mockMvc.perform(delete("/todos/10")
                        .with(httpBasic("saran", "saran")))
                .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    void shouldUpdateTodoIsDoneUsingID() throws Exception {
        mockMvc.perform(patch("/todos/2")
                        .with(httpBasic("saran", "saran"))
                        .contentType("application/json")
                        .content("{\"isDone\":true}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":2,\"todoName\":\"TestTodo-2\",\"isDone\":true}"));
    }
}
