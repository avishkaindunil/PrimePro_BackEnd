package net.primepro.primepro.controller;


import net.primepro.primepro.dto.EmployeeSummaryDto;
import net.primepro.primepro.entity.CenterAdmin;
import net.primepro.primepro.service.impl.EmployeeReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private EmployeeReportServiceImpl employeeReportService;


    @GetMapping("/employee-summary")
    public ResponseEntity<List<EmployeeSummaryDto>> getEmployeeSummaries() {
        return ResponseEntity.ok(employeeReportService.getEmployeeSummaries());
    }

    @GetMapping("/salary-summary")
    public ResponseEntity<Map<String, Object>> getSalarySummary() {
        return ResponseEntity.ok(employeeReportService.getSalarySummary());
    }

    @GetMapping("/attendance-summary")
    public ResponseEntity<Map<String, Object>> getAttendanceSummary(
            @RequestParam Optional<String> dateRange,
            @RequestParam Optional<String> employeeId) {
        return ResponseEntity.ok(employeeReportService.getAttendanceSummary(dateRange, employeeId));
    }

//    @GetMapping("/leave-usage-summary")
//    public ResponseEntity<Map<String, Object>> getLeaveUsageSummary() {
//        return ResponseEntity.ok(employeeReportService.getLeaveUsageSummary());
//    }

    @GetMapping("/leave-usage-summary")
    public ResponseEntity<Map<String, Object>> getLeaveUsageSummary(@RequestParam(value = "employeeId", required = false) Integer employeeId) {
        Map<String, Object> summary = employeeReportService.getLeaveUsageSummary(employeeId);
        return ResponseEntity.ok(summary);
    }


    @GetMapping("/get-all-centers")
    public ResponseEntity<List<CenterAdmin>> getAllCenters(){
        return ResponseEntity.ok(employeeReportService.getAllCenters());
    }


}
