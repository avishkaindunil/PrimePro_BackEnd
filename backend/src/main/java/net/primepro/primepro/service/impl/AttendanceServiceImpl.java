package net.primepro.primepro.service.impl;

import net.primepro.primepro.dto.AttendanceDto;
import net.primepro.primepro.dto.AttendanceNewDTO;
import net.primepro.primepro.dto.LeaveRequestDto;
import net.primepro.primepro.dto.TotalHoursResponse;
import net.primepro.primepro.entity.Attendance;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.repository.AttendanceRepository;
import net.primepro.primepro.service.AttendanceService;
import net.primepro.primepro.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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

    public List<AttendanceNewDTO> getAllAttendanceDescending() {
        List<Object[]> results = (List<Object[]>) attendanceRepository.findAllWithUserName();
        return results.stream().map(result -> {
            AttendanceNewDTO dto = new AttendanceNewDTO();
            dto.setId((Integer) result[0]);
            dto.setAttendanceDate((LocalDate) result[1]);
            dto.setCheckInTime((Time) result[2]);
            dto.setCheckOutTime((Time) result[3]);
            dto.setIsApproved((Integer) result[4]);
            dto.setName((String) result[5]);
            return dto;
        }).toList();
    }

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
//        LocalDateTime localDateTime = attendanceDto.getAttendanceDate().atStartOfDay();
//        attendanceData.setAttendanceDate(Date.from(localDateTime
//                .atZone(ZoneId.systemDefault()).toInstant()));
        attendanceData.setAttendanceDate(attendanceDto.getAttendanceDate());
        attendanceData.setCheckInTime(attendanceDto.getCheckInTime());
        attendanceData.setCheckOutTime(attendanceDto.getCheckOutTime());
        attendanceData.setWorkHours(attendanceDto.getWorkHours());
        attendanceData.setOvertime(attendanceDto.getOvertime());
        attendanceData.setIsApproved(attendanceDto.getIsApproved());
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

    @Override
    public TotalHoursResponse calculateTotalHoursAndOvertimeForCurrentMonth(Integer employeeId) {
        // Get start and end dates for the current month
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        // Fetch attendance records for the current month
        List<Attendance> attendanceRecords = attendanceRepository.findByEmployeeIdAndAttendanceDateBetween(
                employeeId, startDate, endDate
        );

        long totalWorkMinutes = 0;
        long totalOvertimeMinutes = 0;

        for (Attendance record : attendanceRecords) {
            // Calculate working hours (from check-in to check-out)
            LocalTime checkInTime = record.getCheckInTime().toLocalTime();
            LocalTime checkOutTime = record.getCheckOutTime().toLocalTime();

            Duration workDuration = Duration.between(checkInTime, checkOutTime);
            long dailyWorkMinutes = workDuration.toMinutes();

            // Split regular work hours (8 AM to 5 PM) and overtime
            LocalTime startOfWorkDay = LocalTime.of(8, 0);
            LocalTime endOfWorkDay = LocalTime.of(17, 0);

            long regularWorkMinutes = 0;
            long overtimeMinutes = 0;

            if (!checkInTime.isAfter(endOfWorkDay)) {
                LocalTime effectiveEnd = checkOutTime.isBefore(endOfWorkDay) ? checkOutTime : endOfWorkDay;
                regularWorkMinutes = Duration.between(
                        checkInTime.isAfter(startOfWorkDay) ? checkInTime : startOfWorkDay,
                        effectiveEnd
                ).toMinutes();
            }

            if (checkOutTime.isAfter(endOfWorkDay)) {
                overtimeMinutes = Duration.between(endOfWorkDay, checkOutTime).toMinutes();
            }

            totalWorkMinutes += regularWorkMinutes;
            totalOvertimeMinutes += overtimeMinutes;
        }

        // Convert minutes to hours and minutes
        long totalWorkHours = totalWorkMinutes / 60;
        long totalWorkRemainingMinutes = totalWorkMinutes % 60;

        long totalOvertimeHours = totalOvertimeMinutes / 60;
        long totalOvertimeRemainingMinutes = totalOvertimeMinutes % 60;

        return new TotalHoursResponse((int) totalWorkHours, (int) totalOvertimeHours);
    }

    private boolean isSameDay(LocalDate date1, LocalDate date2) {
//        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date2.equals(date1);
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

    @Override
    public void approveAttendance(Integer attendanceId, Integer isApproved) {
        if (attendanceId == null || isApproved == null) {
            throw new IllegalArgumentException("Attendance ID and approval status must not be null.");
        }

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new IllegalArgumentException("Attendance not found"));

        if (isApproved != 0 && isApproved != 1 && isApproved != 2) {
            throw new IllegalArgumentException("Invalid status. Use 0 (Pending), 1 (Approved), or 2 (Rejected).");
        }

        attendance.setIsApproved(isApproved);

        attendanceRepository.save(attendance);
    }

}
