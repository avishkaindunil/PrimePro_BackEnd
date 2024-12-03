package net.primepro.primepro.service;

import net.primepro.primepro.dto.AttendanceDto;
import net.primepro.primepro.entity.Attendance;

import java.util.List;

public interface AttendanceService {
    Attendance markAttendance(AttendanceDto attendance);
    List<Attendance> getAttendanceByEmployee(Integer employeeId);
    void deleteAttendance(Integer attendanceId);
}
