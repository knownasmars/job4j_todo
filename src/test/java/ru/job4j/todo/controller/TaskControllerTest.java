package ru.job4j.todo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.List;


import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskControllerTest {

    private TaskService taskService;

    private TaskController taskController;

    @BeforeEach
    public void initServices() {
        taskService = mock(TaskService.class);
        taskController = new TaskController(taskService);
    }

    @Test
    public void whenRequestTaskListThenGetTableWithTasks() {
        var task = new Task(1, "description");
        var anotherTask = new Task(2, "anotherDescription");
        var expected = List.of(task, anotherTask);

        when(taskService.findAll()).thenReturn(expected);

        var model = new ConcurrentModel();
        HttpSession session = mock(HttpSession.class);
        var view = taskController.getAll(model, session);
        var actual = model.getAttribute("tasks");

        assertThat(view).isEqualTo("tasks/all");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenChooseActiveTasksThenGetActiveList() {
        var activeTask = new Task(1, "description", now(), true, "");
        var anotherActiveTask = new Task(2, "anotherDescription", now(), true, "");
        var taskList = List.of(activeTask, anotherActiveTask);

        when(taskService.findByStatus(any(Boolean.class))).thenReturn(taskList);

        var model = new ConcurrentModel();
        HttpSession session = mock(HttpSession.class);
        var view = taskController.getActive(model, session);

        assertThat(view).isEqualTo("tasks/active");
    }

    @Test
    public void whenChooseClosedTasksThenGetClosedList() {
        var activeTask = new Task(1, "description", now(), true, "");
        var anotherActiveTask = new Task(2, "anotherDescription", now(), true, "");
        var taskList = List.of(activeTask, anotherActiveTask);

        when(taskService.findByStatus(any(Boolean.class))).thenReturn(taskList);

        var model = new ConcurrentModel();
        HttpSession session = mock(HttpSession.class);
        var view = taskController.getClosed(model, session);

        assertThat(view).isEqualTo("tasks/closed");
    }

    @Test
    public void whenDeleteTasksThenSuccessfullyDone() {
        var model = new ConcurrentModel();
        var view = taskController.remove(model, 1);

        assertThat(view).isEqualTo("redirect:/all-tasks");
    }

    @Test
    public void whenCompleteTasksThenSuccessfullyDone() {
        var model = new ConcurrentModel();
        var view = taskController.complete(new Task(), model, 1);

        assertThat(view).isEqualTo("redirect:/all-tasks");
    }

    @Test
    public void whenAddNewTaskThenOK() {
        var task = new Task(1, "description");

        var model = new ConcurrentModel();
        var view = taskController.create(task, model);

        assertThat(view).isEqualTo("redirect:/all-tasks");
    }
}