package net.primepro.primepro.service;

import net.primepro.primepro.dto.AttendanceDto;
import net.primepro.primepro.dto.AttendanceNewDTO;
import net.primepro.primepro.dto.TotalHoursResponse;
import net.primepro.primepro.entity.Attendance;

import java.util.List;

public interface AttendanceService {

    List<AttendanceNewDTO> getAllAttendanceDescending();
    Attendance markAttendance(AttendanceDto attendance);
    List<Attendance> getAttendanceByEmployee(Integer employeeId);
    void deleteAttendance(Integer attendanceId);
    TotalHoursResponse calculateTotalHoursAndOvertimeForCurrentMonth(Integer employeeId);
    void approveAttendance(Integer attendanceId, Integer status);
}
