package net.primepro.primepro.service;

import net.primepro.primepro.constants.BookingStatusEnum;
import net.primepro.primepro.dto.CarWashBookingDto;
import net.primepro.primepro.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Integer customerId, CarWashBookingDto bookingDto);
    Optional<Task> getTaskById(Integer taskId);
    List<Task> getTasksByEmployee(Integer employeeId);
    Task updateTask(Integer taskId, Task task);
    void deleteTask(Integer taskId);
    Task assignEmployeeToTask(Integer taskId, Integer employeeId);
    Task changeTaskStatus(Integer taskId, BookingStatusEnum newStatus);
}
