package net.primepro.primepro.service;

import net.primepro.primepro.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);
    Optional<Task> getTaskById(Long taskId);
    List<Task> getTasksByEmployee(Long employeeId);
    Task updateTask(Long taskId, Task task);
    void deleteTask(Long taskId);
}
