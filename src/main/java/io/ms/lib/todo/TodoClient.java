package io.ms.lib.todo;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class TodoClient {

    private final WebClient webClient;

    public TodoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Todo> fetchAllTodos() {
        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/todos")
                .retrieve()
                .bodyToFlux(Todo.class)
                .collectList()
                .block();
    }


    public Todo fetchTodoById(Long id) {
        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/todos/" + id)
                .retrieve()
                .bodyToMono(Todo.class)
                .block();
    }
}
