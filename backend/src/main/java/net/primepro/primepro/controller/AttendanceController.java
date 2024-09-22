package net.primepro.primepro.controller;

import net.primepro.primepro.entity.Attendance;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.service.AttendanceService;
import net.primepro.primepro.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody Attendance attendance) {
        try {
            Employee employee = employeeService.getEmployee(attendance.getEmployee().getId());
            if (employee == null) {
                return ResponseEntity.badRequest().body("Employee not found");
            }

            attendance.setEmployee(employee);
            Attendance markedAttendance = attendanceService.markAttendance(attendance);
            return ResponseEntity.ok(markedAttendance);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Attendance>> getAttendanceByEmployee(@PathVariable Long employeeId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByEmployee(employeeId);
        return ResponseEntity.ok(attendanceList);
    }
}
