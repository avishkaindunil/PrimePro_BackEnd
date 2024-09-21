package net.primepro.primepro.controller;

import net.primepro.primepro.entity.Employee;
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
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Employee employee = employeeService.getEmployee(task.getEmployee().getId());
            if (employee == null) {
                return ResponseEntity.badRequest().body("Employee not found");
            }

            task.setEmployee(employee);
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.ok(createdTask);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Task>> getTasksByEmployee(@PathVariable Long employeeId) {
        List<Task> tasks = taskService.getTasksByEmployee(employeeId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(taskId, task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
