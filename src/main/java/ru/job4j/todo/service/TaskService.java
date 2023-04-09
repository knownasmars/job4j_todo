package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;

public interface TaskService {

    Task save(Task task);

    boolean deleteById(int id);


    List<Task> findAll();

    List<Task> findByStatus(boolean status);

    boolean complete(Task task);
}
