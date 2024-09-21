package net.primepro.primepro.service.impl;

import net.primepro.primepro.entity.Task;
import net.primepro.primepro.repository.TaskRepository;
import net.primepro.primepro.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        if (task.getEmployee() == null || task.getEmployee().getId() == null) {
            throw new IllegalArgumentException("Employee must be provided.");
        }
        List<Task> existingTasks = taskRepository.findAll();
        for (Task existingTask : existingTasks) {
            if (existingTask.getEmployee().getId().equals(task.getEmployee().getId())) {
                boolean isOverlapping = task.getStartTime().before(existingTask.getEndTime()) &&
                        existingTask.getStartTime().before(task.getEndTime());

                if (isOverlapping) {
                    throw new IllegalArgumentException("Task cannot overlap with existing task.");
                }
            }
        }
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> getTasksByEmployee(Long employeeId) {
        return taskRepository.findByEmployeeId(employeeId);
    }

    @Override
    public Task updateTask(Long taskId, Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setTaskDescription(updatedTask.getTaskDescription());
            existingTask.setTaskDate(updatedTask.getTaskDate());
            existingTask.setStartTime(updatedTask.getStartTime());
            existingTask.setEndTime(updatedTask.getEndTime());
            existingTask.setTaskStatus(updatedTask.getTaskStatus());
            return taskRepository.save(existingTask);
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
