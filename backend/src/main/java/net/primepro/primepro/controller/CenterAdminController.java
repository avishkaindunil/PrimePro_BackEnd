package net.primepro.primepro.controller;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.*;
import net.primepro.primepro.entity.Booking;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.entity.LeaveRequest;
import net.primepro.primepro.response.BookingResponse;
import net.primepro.primepro.response.LoginResponse;
import net.primepro.primepro.service.CenterAdminService;
import net.primepro.primepro.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/centerAdmin")
@AllArgsConstructor
public class CenterAdminController {

    @Autowired
    private CenterAdminService centerAdminService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/get-center/{centerId}")
    public String getCenter(@PathVariable int centerId) {
        return centerAdminService.getCenter(centerId);
    }

    @PostMapping("/add")
    public String createCenterAdmin(@RequestBody CenterAdminDto centerAdminDto) {
        centerAdminService.addCenterAdmin(centerAdminDto);
        return "CenterAdmin Added";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCenterAdmin(@RequestBody LoginDto loginDto) {
        LoginResponse loginResponse = centerAdminService.loginCenterAdmin(loginDto);
        return ResponseEntity.ok(loginResponse);
    }

//    @GetMapping("/get-today-bookings")
//    public ResponseEntity<List<Booking>> getTodayBookings() {
//        List<Booking> todayBookings = centerAdminService.getTodayBookings();
//        return ResponseEntity.ok(todayBookings);
//    }

    @GetMapping("/get-employee-details/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeDetails(@PathVariable String employeeId) {
        EmployeeDto employeeDto = centerAdminService.getEmployeeDetails(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("/get-today-all-bookings")
    public ResponseEntity<List<BookingResponse>> getTodayAllBookings() {
        List<BookingResponse> bookingResponses = centerAdminService.getTodayAllBookings();
        return ResponseEntity.ok(bookingResponses);
    }

    // -------------------------  New Development ---------------------------------- //

    @GetMapping("/get-All-employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employeeList = centerAdminService.getAllEmployees();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/not-time-allocated")
    public ResponseEntity<List<Booking>> getBookingsWithoutTimeAllocation() {
        List<Booking> bookings = centerAdminService.getBookingsWithoutTimeAllocation();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/allocate-time/{bookingId}")
    public ResponseEntity<Booking> allocateTime(
            @PathVariable Integer bookingId,
            @RequestParam String startTime) {
        try {
            Time time = Time.valueOf(startTime + ":00");
            Booking updatedBooking = centerAdminService.allocateTime(bookingId, time);
            return ResponseEntity.ok(updatedBooking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/cant-allocate-time/{bookingId}")
    public ResponseEntity<Booking> allocateTime(
            @PathVariable Integer bookingId) {
        try {
            Booking updatedBooking = centerAdminService.cantAllocateTime(bookingId);
            return ResponseEntity.ok(updatedBooking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/not-task-assigned")
    ResponseEntity<List<Booking>> getBookingsWithoutTaskAssigned(){
        List<Booking> bookings = centerAdminService.getBookingsWithoutTaskAssigned();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/assign-tasks")
    public ResponseEntity<?> assignTasks(@RequestBody TaskDto taskDto) {
        String responseMzg = centerAdminService.assignTasks(taskDto);
        return ResponseEntity.ok(responseMzg);
    }

    @GetMapping("/get-workload-progress")
    public ResponseEntity<List<?>> getWorkLoadProgress() {
        List<?> bookingList = centerAdminService.getWorkLoadProgress();
        return ResponseEntity.ok(bookingList);
    }

    @GetMapping("/get-all-bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> allBookings = centerAdminService.getAllBookings();
        return ResponseEntity.ok(allBookings);
    }

    @GetMapping("/get-employee-perform")
    ResponseEntity<List<EmpPerformDto>> getEmployeePerform(){
        List<EmpPerformDto> empPerformList = centerAdminService.getEmployeePerform();
        return ResponseEntity.ok(empPerformList);
    }

    @GetMapping("/get-task-distribution")
    ResponseEntity<List<TaskDisDto>> getTaskDistribution(){
        List<TaskDisDto> taskDistributions = centerAdminService.getTaskDistribution();
        return ResponseEntity.ok(taskDistributions);
    }
}
