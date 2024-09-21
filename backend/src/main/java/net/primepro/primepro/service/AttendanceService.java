package net.primepro.primepro.service;

import net.primepro.primepro.entity.Attendance;

import java.util.List;

public interface AttendanceService {
    Attendance markAttendance(Attendance attendance);
    List<Attendance> getAttendanceByEmployee(Long employeeId);
    void deleteAttendance(Long attendanceId);
}
