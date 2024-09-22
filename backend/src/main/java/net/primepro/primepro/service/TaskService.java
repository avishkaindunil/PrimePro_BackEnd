package net.primepro.primepro.service;

import net.primepro.primepro.constants.BookingStatusEnum;
import net.primepro.primepro.dto.CarWashBookingDto;
import net.primepro.primepro.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Long customerId, CarWashBookingDto bookingDto);
    Optional<Task> getTaskById(Long taskId);
    List<Task> getTasksByEmployee(Long employeeId);
    Task updateTask(Long taskId, Task task);
    void deleteTask(Long taskId);
    Task assignEmployeeToTask(Long taskId, Long employeeId);
    Task changeTaskStatus(Long taskId, BookingStatusEnum newStatus);
}
