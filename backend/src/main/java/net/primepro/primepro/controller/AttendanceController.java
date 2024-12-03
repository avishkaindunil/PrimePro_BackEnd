package net.primepro.primepro.controller;

import net.primepro.primepro.dto.AttendanceDto;
import net.primepro.primepro.dto.TotalHoursResponse;
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

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody AttendanceDto attendance) {
        try {
            Attendance markedAttendance = attendanceService.markAttendance(attendance);
            return ResponseEntity.ok(markedAttendance);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Attendance>> getAttendanceByEmployee(@PathVariable Integer employeeId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByEmployee(employeeId);
        return ResponseEntity.ok(attendanceList);
    }

    @GetMapping("/calculate/{employeeId}")
    public ResponseEntity<?> calculateCurrentMonthHours(@PathVariable Integer employeeId) {
        TotalHoursResponse response = attendanceService.calculateTotalHoursAndOvertimeForCurrentMonth(employeeId);
        return ResponseEntity.ok(response);
    }
}
