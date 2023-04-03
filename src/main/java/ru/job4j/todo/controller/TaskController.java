package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.todo.service.SimpleTaskService;

@Controller
public class TaskController {

    private final SimpleTaskService taskService;

    public TaskController(SimpleTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping({"/", "/index", "/list"})
    public String getIndex() {
        return "tasks/list";
    }
}
