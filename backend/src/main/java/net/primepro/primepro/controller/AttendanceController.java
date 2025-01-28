package net.primepro.primepro.controller;

import net.primepro.primepro.dto.AttendanceDto;
import net.primepro.primepro.dto.AttendanceNewDTO;
import net.primepro.primepro.dto.TotalHoursResponse;
import net.primepro.primepro.entity.Attendance;
import net.primepro.primepro.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PutMapping("/{attendanceId}/approve")
    public ResponseEntity<String> approveAttendance(
            @PathVariable Integer attendanceId,
            @RequestParam Integer isApproved) {
        try {
            attendanceService.approveAttendance(attendanceId, isApproved);
            return ResponseEntity.ok("Attendance status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating attendance.");
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<AttendanceNewDTO>> getAllAttendance() {
        List<AttendanceNewDTO> attendances = attendanceService.getAllAttendanceDescending();
        return ResponseEntity.ok(attendances);
    }

}
