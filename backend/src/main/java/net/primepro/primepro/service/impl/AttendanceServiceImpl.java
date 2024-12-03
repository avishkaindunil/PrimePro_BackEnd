package net.primepro.primepro.service.impl;

import net.primepro.primepro.dto.AttendanceDto;
import net.primepro.primepro.entity.Attendance;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.repository.AttendanceRepository;
import net.primepro.primepro.service.AttendanceService;
import net.primepro.primepro.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.*;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Attendance markAttendance(AttendanceDto attendance) {
        if (attendance.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee must be provided.");
        }

        Employee employee = employeeService.getEmployee(attendance.getEmployeeId());
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found");
        }

        List<Attendance> existingAttendances = attendanceRepository.findByEmployeeId(attendance.getEmployeeId());

        for (Attendance existingAttendance : existingAttendances) {
            if (isSameDay(existingAttendance.getAttendanceDate(), attendance.getAttendanceDate())) {
                throw new IllegalArgumentException("Attendance already mark to this employee.");
            }
        }

        AttendanceDto attendanceDto = calculateWorkAndOvertime(attendance);

        Attendance attendanceData = new Attendance();
        LocalDateTime localDateTime = attendanceDto.getAttendanceDate().atStartOfDay();
        attendanceData.setAttendanceDate(Date.from(localDateTime
                .atZone(ZoneId.systemDefault()).toInstant()));
        attendanceData.setCheckInTime(attendanceDto.getCheckInTime());
        attendanceData.setCheckOutTime(attendanceDto.getCheckOutTime());
        attendanceData.setWorkHours(attendanceDto.getWorkHours());
        attendanceData.setOvertime(attendanceDto.getOvertime());
        attendanceData.setApproved(attendanceDto.isApproved());
        attendanceData.setEmployee(employee);

        return attendanceRepository.save(attendanceData);
    }

    @Override
    public List<Attendance> getAttendanceByEmployee(Integer employeeId) {
        return attendanceRepository.findByEmployeeIdOrderedByDateDesc(employeeId);
    }

    @Override
    public void deleteAttendance(Integer attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }

    private boolean isSameDay(Date date1, LocalDate date2) {
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date2.equals(localDate1);
    }

    private AttendanceDto calculateWorkAndOvertime(AttendanceDto attendance) {
        LocalDate attendanceDate = attendance.getAttendanceDate();
        Time checkInTime = attendance.getCheckInTime();
        Time checkOutTime = attendance.getCheckOutTime();

        LocalTime workStart = LocalTime.of(8, 0);
        LocalTime workEnd = LocalTime.of(17, 0);

        LocalTime checkInLocalTime = checkInTime.toLocalTime();
        LocalTime checkOutLocalTime = checkOutTime.toLocalTime();

        LocalDateTime checkInDateTime = LocalDateTime.of(attendanceDate, checkInLocalTime);
        LocalDateTime checkOutDateTime = LocalDateTime.of(attendanceDate, checkOutLocalTime);

        if (checkInDateTime.isAfter(checkOutDateTime)) {
            throw new IllegalArgumentException("Check-out time must be after check-in time");
        }

        LocalDateTime adjustedCheckIn = checkInDateTime.isBefore(checkInDateTime.with(workStart))
                ? checkInDateTime.with(workStart)
                : checkInDateTime;

        LocalDateTime adjustedCheckOut = checkOutDateTime.isAfter(checkOutDateTime.with(workEnd))
                ? checkOutDateTime.with(workEnd)
                : checkOutDateTime;

        Duration workDuration = Duration.between(adjustedCheckIn, adjustedCheckOut);
        long workHours = workDuration.toHours();
        long workMinutes = workDuration.toMinutes() % 60;

        Duration overtimeDuration = Duration.between(
                adjustedCheckOut,
                checkOutDateTime.isAfter(checkOutDateTime.with(workEnd)) ? checkOutDateTime : adjustedCheckOut
        );
        long overtimeHours = overtimeDuration.toHours();
        long overtimeMinutes = overtimeDuration.toMinutes() % 60;

        System.out.println("Work Time: " + workHours + " hours " + workMinutes + " minutes");
        System.out.println("Overtime: " + overtimeHours + " hours " + overtimeMinutes + " minutes");

        attendance.setOvertime((int) overtimeHours);
        attendance.setWorkHours((int) workHours);

        return attendance;
    }
    
}
