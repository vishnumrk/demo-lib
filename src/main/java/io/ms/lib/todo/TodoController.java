package io.ms.lib.todo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoClient todoClient;

    public TodoController(TodoClient todoClient) {
        this.todoClient = todoClient;
    }

    @GetMapping
    public List<Todo> getTodos(){
        return todoClient.fetchAllTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") Long id){
        return todoClient.fetchTodoById(id);
    }
}
