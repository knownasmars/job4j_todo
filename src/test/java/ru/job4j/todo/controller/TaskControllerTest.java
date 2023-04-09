package ru.job4j.todo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

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
        var view = taskController.getAll(model);
        var actual = model.getAttribute("tasks");

        assertThat(view).isEqualTo("tasks/all");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenChooseActiveTasksThenGetActiveList() {
        var activeTask = new Task(1, "description", now(), true);
        var anotherActiveTask = new Task(2, "anotherDescription", now(), true);
        var taskList = List.of(activeTask, anotherActiveTask);

        when(taskService.findByStatus(any(Boolean.class))).thenReturn(taskList);

        var model = new ConcurrentModel();
        var view = taskController.getActive(model);

        assertThat(view).isEqualTo("tasks/active");
    }

    @Test
    public void whenChooseClosedTasksThenGetClosedList() {
        var activeTask = new Task(1, "description", now(), true);
        var anotherActiveTask = new Task(2, "anotherDescription", now(), true);
        var taskList = List.of(activeTask, anotherActiveTask);

        when(taskService.findByStatus(any(Boolean.class))).thenReturn(taskList);

        var model = new ConcurrentModel();
        var view = taskController.getClosed(model);

        assertThat(view).isEqualTo("tasks/closed");
    }

    @Test
    public void whenDeleteTasksThenSuccessfullyDone() {
        when(taskService.deleteById(any(Integer.class))).thenReturn(true);

        var model = new ConcurrentModel();
        var view = taskController.remove(model, 1);

        assertThat(view).isEqualTo("redirect:/ex1-tabs-1");
    }

    @Test
    public void whenDeleteTasksThenGotError() {
        when(taskService.deleteById(any(Integer.class))).thenReturn(false);

        var model = new ConcurrentModel();
        var view = taskController.remove(model, 1);
        var message = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(message).isEqualTo("The task with specified \"id\" is not found!");
    }

    @Test
    public void whenCompleteTasksThenSuccessfullyDone() {
        when(taskService.complete(any(Task.class))).thenReturn(true);

        var model = new ConcurrentModel();
        var view = taskController.complete(new Task(), model, 1);

        assertThat(view).isEqualTo("redirect:/ex1-tabs-1");
    }

    @Test
    public void whenCompleteTasksThenGotError() {
        when(taskService.deleteById(any(Integer.class))).thenReturn(false);

        var model = new ConcurrentModel();
        var view = taskController.complete(new Task(), model, 1);
        var message = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(message).isEqualTo("The task with specified \"id\" is not found!");
    }

    @Test
    public void whenAddNewTaskThenOK() {
        var task = new Task(1, "description");

        var model = new ConcurrentModel();
        var view = taskController.create(task, model);

        assertThat(view).isEqualTo("redirect:/ex1-tabs-1");
    }
}