package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.List;

public interface TaskStore {

    Task save(Task task);

    void deleteById(int id);

    List<Task> findAll();

    List<Task> findByStatus(boolean status);

    void complete(Task task);
}
