package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.EmployeeSummaryDto;
import net.primepro.primepro.entity.Attendance;
import net.primepro.primepro.entity.CenterAdmin;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.repository.AttendanceRepository;
import net.primepro.primepro.repository.CenterAdminRepository;
import net.primepro.primepro.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeReportServiceImpl {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private CenterAdminRepository centerAdminRepository;

    public List<EmployeeSummaryDto> getEmployeeSummaries() {
        return employeeRepository.findAll().stream().map(employee -> new EmployeeSummaryDto(
                employee.getId().toString(),
                employee.getBranchName(),
                employee.getDesignation(),
                employee.getPhoneNumber(),
                employee.getBaseSalary(),
                employee.getNoOfAnnualLeaves(),
                employee.getNoOfCasualLeaves(),
                employee.getNoOfMedicalLeaves()
        )).collect(Collectors.toList());
    }

    public Map<String, Object> getSalarySummary() {
        Map<String, Long> salaryBrackets = employeeRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        employee -> {
                            int salary = employee.getBaseSalary();
                            if (salary < 30000) return "Below 30k";
                            if (salary < 50000) return "30k-50k";
                            return "Above 50k";
                        },
                        Collectors.counting()
                ));

        double avgSalary = employeeRepository.findAll().stream()
                .mapToInt(Employee::getBaseSalary)
                .average().orElse(0.0);

        System.out.println("salarybrackets"+salaryBrackets);
        System.out.println("avgSalary"+avgSalary);



        return Map.of(
                "salaryBrackets", salaryBrackets,
                "averageSalary", avgSalary
        );
    }

//    public Map<String, Object> getAttendanceSummary(Optional<String> dateRange, Optional<String> employeeId) {
//        List<Attendance> attendances = attendanceRepository.findAll();
//
//
//        // Apply filtering based on date range and employee
//        if (dateRange.isPresent()) {
//            String[] dates = dateRange.get().split(" to ");
//            System.out.println("dates[0] "+ dates[0]);
//            System.out.println("dates[1] "+ dates[1]);
//            LocalDate start = LocalDate.parse(dates[0]);
//            LocalDate end = LocalDate.parse(dates[1]);
//            attendances = attendances.stream()
//                    .filter(a -> !a.getAttendanceDate().isBefore(start) && !a.getAttendanceDate().isAfter(end))
//                    .collect(Collectors.toList());
//        }
//        if (employeeId.isPresent()) {
//            attendances = attendances.stream()
//                    .filter(a -> a.getEmployee() != null && a.getEmployee().getId() != null && a.getEmployee().getId().toString().equals(employeeId.get()))
//                    .collect(Collectors.toList());
//        }
//
//        System.out.println("attendences "+attendances);
//        long totalDays = attendances.size();
//        long presentDays = attendances.stream().filter(a -> a.isApproved()).count();
//        double attendancePercentage = (double) presentDays / totalDays * 100;
//
////        long totalDays =  20;
////        long presentDays = 17;
////        double attendancePercentage = (double) presentDays / totalDays * 100;
//
//        System.out.println("totalDays "+totalDays);
//        System.out.println("presentDays "+ presentDays);
//        System.out.println("attendancePercentage "+ attendancePercentage);
//
//        return Map.of(
//                "totalDays", totalDays,
//                "presentDays", presentDays,
//                "attendancePercentage", attendancePercentage
//        );
//    }




//    public Map<String, Object> getAttendanceSummary(Optional<String> dateRange, Optional<String> employeeId) {
//        List<Attendance> attendances = attendanceRepository.findAll();
//
//        // Apply filtering based on date range and employee
//        if (dateRange.isPresent()) {
//            String[] dates = dateRange.get().split(" to ");
//            LocalDate start = LocalDate.parse(dates[0]);
//            LocalDate end = LocalDate.parse(dates[1]);
//            attendances = attendances.stream()
//                    .filter(a -> !a.getAttendanceDate().isBefore(start) && !a.getAttendanceDate().isAfter(end))
//                    .collect(Collectors.toList());
//        }
//        if (employeeId.isPresent()) {
//            attendances = attendances.stream()
//                    .filter(a -> a.getEmployee() != null && a.getEmployee().getId() != null && a.getEmployee().getId().toString().equals(employeeId.get()))
//                    .collect(Collectors.toList());
//        }
//
//        long totalDays = attendances.size();
//        long presentDays = attendances.stream().filter(Attendance::isApproved).count();
//        double attendancePercentage = (totalDays > 0) ? (double) presentDays / totalDays * 100 : 0;
//
//        // Extract employee's username (assuming a single employee ID is passed)
//        String username = "";
//        if (employeeId.isPresent() && !attendances.isEmpty()) {
//            Employee employee = attendances.get(0).getEmployee();
//            if (employee != null && employee.getUser() != null) {
//                username = employee.getUser().getName(); // Assuming OurUsers has a getUsername() method
//            }
//        }
//        System.out.println("username"+username);
//        return Map.of(
//                "totalDays", totalDays,
//                "presentDays", presentDays,
//                "attendancePercentage", attendancePercentage,
//                "username", username
//        );
//    }


    public Map<String, Object> getAttendanceSummary(Optional<String> dateRange, Optional<String> employeeId) {
        List<Attendance> attendances = attendanceRepository.findAll();

        // Apply filtering based on date range and employee
        if (dateRange.isPresent()) {
            String[] dates = dateRange.get().split(" to ");
            LocalDate start = LocalDate.parse(dates[0]);
            LocalDate end = LocalDate.parse(dates[1]);
            attendances = attendances.stream()
                    .filter(a -> !a.getAttendanceDate().isBefore(start) && !a.getAttendanceDate().isAfter(end))
                    .collect(Collectors.toList());
        }
        if (employeeId.isPresent()) {
            attendances = attendances.stream()
                    .filter(a -> a.getEmployee() != null && a.getEmployee().getId() != null && a.getEmployee().getId().toString().equals(employeeId.get()))
                    .collect(Collectors.toList());
        }

        // Calculate totals and attendance percentage
        long totalDays = attendances.size();
        long presentDays = attendances.stream()
                .filter(a -> a.getIsApproved() != null && a.getIsApproved() == 1)   // Assuming 1 means approved
                .count();
        double attendancePercentage = (totalDays > 0) ? (double) presentDays / totalDays * 100 : 0;

        // Extract employee's username (assuming a single employee ID is passed)
        String username = "";
        if (employeeId.isPresent() && !attendances.isEmpty()) {
            Employee employee = attendances.get(0).getEmployee();
            if (employee != null && employee.getUser() != null) {
                username = employee.getUser().getName(); // Assuming OurUsers has a getName() method
            }
        }

        System.out.println("username: " + username);
        return Map.of(
                "totalDays", totalDays,
                "presentDays", presentDays,
                "attendancePercentage", attendancePercentage,
                "username", username
        );
    }

//    public Map<String, Object> getLeaveUsageSummary() {
//        List<Employee> employees = employeeRepository.findAll();
//
//        int totalAnnualLeaves = 0;
//        int totalCasualLeaves = 0;
//        int totalMedicalLeaves = 0;
//
//        for (Employee employee : employees) {
//            totalAnnualLeaves += employee.getNoOfAnnualLeaves();
//            totalCasualLeaves += employee.getNoOfCasualLeaves();
//            totalMedicalLeaves += employee.getNoOfMedicalLeaves();
//        }
//
//        int totalEmployees = employees.size();
//        Map<String, Object> summary = new HashMap<>();
//        summary.put("totalEmployees", totalEmployees);
//        summary.put("totalAnnualLeaves", totalAnnualLeaves);
//        summary.put("totalCasualLeaves", totalCasualLeaves);
//        summary.put("totalMedicalLeaves", totalMedicalLeaves);
//
//        if (totalEmployees > 0) {
//            summary.put("averageAnnualLeaves", (double) totalAnnualLeaves / totalEmployees);
//            summary.put("averageCasualLeaves", (double) totalCasualLeaves / totalEmployees);
//            summary.put("averageMedicalLeaves", (double) totalMedicalLeaves / totalEmployees);
//        } else {
//            summary.put("averageAnnualLeaves", 0.0);
//            summary.put("averageCasualLeaves", 0.0);
//            summary.put("averageMedicalLeaves", 0.0);
//        }
//
//        return summary;
//    }

    public Map<String, Object> getLeaveUsageSummary(Integer employeeId) {
        Map<String, Object> summary = new HashMap<>();

        if (employeeId != null) {
            // Fetch the specific employee by ID
            Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                summary.put("totalEmployees", 1);
                summary.put("totalAnnualLeaves", employee.getNoOfAnnualLeaves());
                summary.put("totalCasualLeaves", employee.getNoOfCasualLeaves());
                summary.put("totalMedicalLeaves", employee.getNoOfMedicalLeaves());
                summary.put("averageAnnualLeaves", (double) employee.getNoOfAnnualLeaves());
                summary.put("averageCasualLeaves", (double) employee.getNoOfCasualLeaves());
                summary.put("averageMedicalLeaves", (double) employee.getNoOfMedicalLeaves());
            } else {
                // If employee ID is invalid, return an empty summary
                summary.put("error", "Employee not found for the given ID");
            }
        } else {
            // Fetch all employees if no specific ID is provided
            List<Employee> employees = employeeRepository.findAll();

            int totalAnnualLeaves = 0;
            int totalCasualLeaves = 0;
            int totalMedicalLeaves = 0;

            for (Employee employee : employees) {
                totalAnnualLeaves += employee.getNoOfAnnualLeaves();
                totalCasualLeaves += employee.getNoOfCasualLeaves();
                totalMedicalLeaves += employee.getNoOfMedicalLeaves();
            }

            int totalEmployees = employees.size();
            summary.put("totalEmployees", totalEmployees);
            summary.put("totalAnnualLeaves", totalAnnualLeaves);
            summary.put("totalCasualLeaves", totalCasualLeaves);
            summary.put("totalMedicalLeaves", totalMedicalLeaves);

            if (totalEmployees > 0) {
                summary.put("averageAnnualLeaves", (double) totalAnnualLeaves / totalEmployees);
                summary.put("averageCasualLeaves", (double) totalCasualLeaves / totalEmployees);
                summary.put("averageMedicalLeaves", (double) totalMedicalLeaves / totalEmployees);
            } else {
                summary.put("averageAnnualLeaves", 0.0);
                summary.put("averageCasualLeaves", 0.0);
                summary.put("averageMedicalLeaves", 0.0);
            }
        }

        return summary;
    }


    public List<CenterAdmin> getAllCenters(){
        return  centerAdminRepository.findAll();
    }

//    2024-01-01 to 2024-12-31

}
