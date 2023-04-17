package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleTaskStore implements TaskStore {

    private final CrudRepository crudRepository;

    /**
     * @param task задача
     * @return объект задачи
     */
    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    /**
     * Удалить задачу по id
     * @param id
     */
    @Override
    public void deleteById(int id) {
        crudRepository.run(
                "delete from Task where id = :fId",
                Map.of("fId", id)
        );
    }

    /**
     * Получить весь список задач
     * @return список всех задач
     */
    @Override
    public List<Task> findAll() {
        return crudRepository.query(
                "from Task t join fetch t.priority order by t.priority ASC", Task.class);
    }

    /**
     * Получить список задач по статусу выполнения
     * @param done - параметр статус выполнения
     * @return список задач по статусу
     */
    public List<Task> findByStatus(boolean done) {
        return crudRepository.query(
                "from Task as t JOIN FETCH t.priority where t.done = :done order by t.priority",
                Task.class,
                Map.of("done", done));
    }

    /**
     * Завершить задание.
     * Метод меняет состояние объекта, поле done с false, на true.
     * @param task - объект заявки
     */
    @Override
    public void complete(Task task) {
        crudRepository.run(
                "UPDATE Task SET done = :done WHERE id = :id",
                Map.of("done", true, "id", task.getId())
        );
    }
}