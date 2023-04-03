package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import ru.job4j.todo.model.Task;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleTaskStore implements TaskStore {

    private final SessionFactory sf;

    /**
     * @param task задача
     * @return объект задачи
     */
    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    /**
     * Удалить задачу по id
     * @param id
     * @return true, если удалена успешно, иначе false
     */
    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Task WHERE id = :Id")
                    .setParameter("Id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    /**
     * Обновить задачу
     * @param task - объект задачи для замены текущего объекта
     * Текущий объект находим, по тому же id, что и у входного объекта
     * @return true, если обновлено успешно, иначе false
     */
    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Task SET description = :description, created = :created"
                                    + " WHERE id = :id")
                    .setParameter("description", task.getDescription())
                    .setParameter("created", Timestamp.valueOf(task.getCreated()))
                    .setParameter("id", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    /**
     * Поиск по id
     * @param id задачи
     * @return Optional<Task> если значение существует, иначе Optional.empty
     */
    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Optional<Task> optionalTask = Optional.of(session.get(Task.class, id));
            session.getTransaction().commit();
            return optionalTask;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    /**
     * Получить весь список задач
     * @return список всех задач
     */
    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        List<Task> rsl = null;
        try {
            session.beginTransaction();
            session.getTransaction().commit();
            rsl = session.createQuery("from Task").list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }
}