package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

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
                            "DELETE Task WHERE id = :id")
                    .setParameter("id", id)
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
     * Получить весь список задач
     * @return список всех задач
     */
    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        List<Task> rsl = new ArrayList<>();
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

    /**
     * Получить список задач по статусу выполнения
     * @param done - параметр статус выполнения
     * @return список задач по статусу
     */
    public List<Task> findByStatus(boolean done) {
        Session session = sf.openSession();
        List<Task> rsl = null;
        try {
            session.beginTransaction();
            session.getTransaction().commit();
            rsl = session.createQuery("from Task where done = :done")
                    .setParameter("done", done).list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    /**
     * Завершить задание.
     * Метод меняет состояние объекта, поле done с false, на true.
     * @param task - объект заявки
     * @return значение true, если изменение в бд прошло успешно, иначе false
     */
    @Override
    public boolean complete(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Task SET done = :done WHERE id = :id")
                    .setParameter("done", true)
                    .setParameter("id", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }
}