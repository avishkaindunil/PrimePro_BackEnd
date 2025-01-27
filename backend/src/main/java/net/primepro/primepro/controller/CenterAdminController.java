package net.primepro.primepro.controller;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.CenterAdminDto;
import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.dto.LoginDto;
import net.primepro.primepro.dto.TaskDto;
import net.primepro.primepro.entity.Booking;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.response.BookingResponse;
import net.primepro.primepro.response.LoginResponse;
import net.primepro.primepro.service.CenterAdminService;
import net.primepro.primepro.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/centerAdmin")
@AllArgsConstructor
public class CenterAdminController {

    @Autowired
    private CenterAdminService centerAdminService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/get-center/{centerId}")
    public String getCenter(@PathVariable int centerId){
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

    @GetMapping("/get-all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> allBookings = centerAdminService.getAllBookings();
        return ResponseEntity.ok(allBookings);
    }

    @GetMapping("/get-today-bookings")
    public ResponseEntity<List<Booking>> getTodayBookings() {
        List<Booking> todayBookings = centerAdminService.getTodayBookings();
        return ResponseEntity.ok(todayBookings);
    }

    @GetMapping("/get-All-employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employeeList = centerAdminService.getAllEmployees();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/get-employee-details/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeDetails(String employeeId){
        EmployeeDto employeeDto = centerAdminService.getEmployeeDetails(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("/get-workload-progress")
    public ResponseEntity<List<?>> getWorkLoadProgress(){
        List<?> bookingList = centerAdminService.getWorkLoadProgress();
        return ResponseEntity.ok(bookingList);
    }

    @PostMapping("/assign-tasks")
    public ResponseEntity<?> assignTasks(@RequestBody TaskDto taskDto){
        String responseMzg = centerAdminService.assignTasks(taskDto);
        return ResponseEntity.ok(responseMzg);
    }

    @GetMapping("/get-today-all-bookings")
    public ResponseEntity<List<BookingResponse>> getTodayAllBookings(){
        List<BookingResponse> bookingResponses = centerAdminService.getTodayAllBookings();
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/get-today-and-future-bookings")
    public ResponseEntity<List<Booking>> getTodayAndFutureBookings() {
        List<Booking> todayBookings = centerAdminService.getBookings();
        return ResponseEntity.ok(todayBookings);
    }

}
