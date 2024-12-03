package net.primepro.primepro.service.impl;

import net.primepro.primepro.constants.BookingStatusEnum;
import net.primepro.primepro.dto.CarWashBookingDto;
import net.primepro.primepro.dto.TaskCountByMonth;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.entity.Task;
import net.primepro.primepro.repository.EmployeeRepository;
import net.primepro.primepro.repository.TaskRepository;
import net.primepro.primepro.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.*;

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
        task.setTaskStatus(BookingStatusEnum.ACCEPTED.toString());

        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(Integer taskId) {
        return taskRepository.findById(taskId);
    }



    @Override
    public List<Task> getTasksByEmployee(Integer employeeId) {
        return taskRepository.findTaskByEmployeeIdAndDate(employeeId);
    }

    @Override
    public List<Task> getAllTasksByEmployee(Integer employeeId) {
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

    @Override
    public Long getTaskCountByEmployeeId(Integer employeeId) {
        return taskRepository.countByEmployerIdAndStartDateToday(employeeId);
    }

    @Override
    public Long getTaskCountByCustomerId(Integer customerId) {
        return taskRepository.countByCustomerIdAndStartDateToday(customerId);
    }

    @Override
    public List<TaskCountByMonth> getTaskCountForLastFiveMonths(Integer employeeId) {
        List<LocalDate> lastFiveMonths = getLastFiveMonths();
        List<TaskCountByMonth> taskCounts = new ArrayList<>();

        Collections.reverse(lastFiveMonths);

        for (LocalDate monthStart : lastFiveMonths) {
            LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());
            List<Object[]> result = taskRepository.countTasksByMonth(monthStart, monthEnd, employeeId);

            Long taskCount = 0L;

            if (!result.isEmpty()) {
                Object[] row = result.get(0);
                taskCount = (Long) row[1];
            }

            int year = monthStart.getYear();
            int month = monthStart.getMonthValue();

            String monthName = monthStart.getMonth().name();
            monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();

            taskCounts.add(new TaskCountByMonth(year, monthName, taskCount));
        }

        return taskCounts;
    }

    private boolean isTaskTimeValidForEmployee(Integer employeeId, LocalDate taskDate, Time startTime, Time endTime) {
        List<Task> existingTasks = taskRepository.findByEmployeeId(employeeId);

        return findValidTaskTime(taskDate, startTime, endTime, existingTasks);
    }

    private boolean findValidTaskTime(LocalDate taskDate, Time startTime, Time endTime, List<Task> existingTasks) {
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

    private boolean isTaskTimeValidForCustomer(Integer customerId, LocalDate taskDate, Time startTime, Time endTime) {
        List<Task> existingTasks = taskRepository.findByCustomerId(customerId);

        return findValidTaskTime(taskDate, startTime, endTime, existingTasks);
    }

    private boolean isSameDay(LocalDate date1, LocalDate date2) {
        return date1.equals(date2);
    }

    private List<LocalDate> getLastFiveMonths() {
        LocalDate currentDate = LocalDate.now();
        List<LocalDate> lastFiveMonths = new ArrayList<>();

        lastFiveMonths.add(currentDate.withDayOfMonth(1));

        for (int i = 1; i < 5; i++) {
            LocalDate previousMonth = currentDate.minusMonths(i).withDayOfMonth(1);
            lastFiveMonths.add(previousMonth);
        }

        return lastFiveMonths;
    }

}
