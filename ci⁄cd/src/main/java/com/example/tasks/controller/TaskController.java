package com.example.tasks.controller;

import com.example.tasks.model.Task;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @GetMapping
    public List<Task> getTasks() {
        return List.of(
                new Task(1L, "Setup Spring Boot project", true),
                new Task(2L, "Add REST endpoint", true),
                new Task(3L, "Configure CI/CD pipelines", false)
        );
    }
}
