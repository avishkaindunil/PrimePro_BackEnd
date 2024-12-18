package net.primepro.primepro.controller;

import net.primepro.primepro.constants.BookingStatusEnum;
import net.primepro.primepro.dto.AssignEmployeeDto;
import net.primepro.primepro.dto.CarWashBookingDto;
import net.primepro.primepro.dto.ChangeStatusDto;
import net.primepro.primepro.dto.TaskCountByMonth;
import net.primepro.primepro.entity.Task;
import net.primepro.primepro.service.EmployeeService;
import net.primepro.primepro.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<?> createTask(@RequestBody CarWashBookingDto bookingDto) {
        try {
            if(bookingDto.getCustomerId() == null){
                return ResponseEntity.badRequest().body("Customer Id cannot be null");
            }
            Task createdTask = taskService.createTask(bookingDto.getCustomerId(), bookingDto);
            return ResponseEntity.ok(createdTask);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Task>> getTasksByEmployee(@PathVariable Integer employeeId) {
        List<Task> tasks = taskService.getTasksByEmployee(employeeId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/employee/all/{employeeId}")
    public ResponseEntity<List<Task>> getAllTasksByEmployee(@PathVariable Integer employeeId) {
        List<Task> tasks = taskService.getAllTasksByEmployee(employeeId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer taskId, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(taskId, task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assignEmployee")
    public ResponseEntity<?> assignEmployee(@RequestBody AssignEmployeeDto assignEmployeeDto) {
        try {
            if(assignEmployeeDto == null || assignEmployeeDto.getEmployeeId() == null){
                return ResponseEntity.badRequest().body("Employee Id cannot be null");
            }
            Task updatedTask = taskService.assignEmployeeToTask(assignEmployeeDto.getTaskId(), assignEmployeeDto.getEmployeeId());
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestBody ChangeStatusDto changeStatusDto) {
        try {
            if (changeStatusDto.getTaskId() == null) {
                return ResponseEntity.badRequest().body("Task ID cannot be null");
            }

            if (changeStatusDto.getStatus() == null) {
                return ResponseEntity.badRequest().body("Status cannot be null");
            }

            Task updatedTask = taskService.changeTaskStatus(changeStatusDto.getTaskId(), BookingStatusEnum.valueOf(changeStatusDto.getStatus()));
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/employee/count/{employeeId}")
    public ResponseEntity<Long> getTaskCountByEmployeeId(@PathVariable Integer employeeId) {
        Long taskCount = taskService.getTaskCountByEmployeeId(employeeId);
        return ResponseEntity.ok(taskCount);
    }

    @GetMapping("/customer/count/{customerId}")
    public ResponseEntity<Long> getTaskCountByCustomerId(@PathVariable Integer customerId) {
        Long taskCount = taskService.getTaskCountByCustomerId(customerId);
        return ResponseEntity.ok(taskCount);
    }

    @GetMapping("/employee/fiveMonths/count/{employeeId}")
    public ResponseEntity<List<TaskCountByMonth>> getTaskCountForLastFiveMonths(@PathVariable Integer employeeId) {
        List<TaskCountByMonth> taskCountByMonth = taskService.getTaskCountForLastFiveMonths(employeeId);
        return ResponseEntity.ok(taskCountByMonth);
    }

}
