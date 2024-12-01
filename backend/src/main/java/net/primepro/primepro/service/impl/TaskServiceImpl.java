package net.primepro.primepro.service.impl;

import net.primepro.primepro.constants.BookingStatusEnum;
import net.primepro.primepro.dto.CarWashBookingDto;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.entity.Task;
import net.primepro.primepro.repository.EmployeeRepository;
import net.primepro.primepro.repository.TaskRepository;
import net.primepro.primepro.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Task createTask(Integer customerId, CarWashBookingDto bookingDto) {
        if (!isTaskTimeValidForCustomer(customerId, bookingDto.getTaskDate(), bookingDto.getStartTime(), bookingDto.getEndTime())) {
            throw new IllegalArgumentException("The selected time is not available.");
        }
        Task task = new Task();
        task.setCustomerId(customerId);
        task.setTaskDescription(bookingDto.getDescription());
        task.setTaskDate(bookingDto.getTaskDate());
        task.setStartTime(bookingDto.getStartTime());
        task.setEndTime(bookingDto.getEndTime());
        task.setTaskStatus(BookingStatusEnum.PENDING.toString());

        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(Integer taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> getTasksByEmployee(Integer employeeId) {
        return taskRepository.findByEmployeeId(employeeId);
    }

    @Override
    public Task updateTask(Integer taskId, Task updatedTask) {
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
    public void deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task assignEmployeeToTask(Integer taskId, Integer employeeId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found."));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found."));

        if(!isTaskTimeValidForEmployee(employeeId, task.getTaskDate(), task.getStartTime(), task.getEndTime())){
            throw new IllegalArgumentException("The selected time is not available.");
        }

        task.setEmployee(employee);
        task.setTaskStatus(BookingStatusEnum.ASSIGN.toString());

        return taskRepository.save(task);
    }

    public Task changeTaskStatus(Integer taskId, BookingStatusEnum newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        task.setTaskStatus(newStatus.toString());

        return taskRepository.save(task);
    }

    private boolean isTaskTimeValidForEmployee(Integer employeeId, Date taskDate, Time startTime, Time endTime) {
        List<Task> existingTasks = taskRepository.findByEmployeeId(employeeId);

        return findValidTaskTime(taskDate, startTime, endTime, existingTasks);
    }

    private boolean findValidTaskTime(Date taskDate, Time startTime, Time endTime, List<Task> existingTasks) {
        for (Task existingTask : existingTasks) {
            if (isSameDay(taskDate, existingTask.getTaskDate())) {
                boolean isOverlapping = startTime.before(existingTask.getEndTime()) &&
                        existingTask.getStartTime().before(endTime);
                if (isOverlapping) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isTaskTimeValidForCustomer(Integer customerId, Date taskDate, Time startTime, Time endTime) {
        List<Task> existingTasks = taskRepository.findByCustomerId(customerId);

        return findValidTaskTime(taskDate, startTime, endTime, existingTasks);
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

}
