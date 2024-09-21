package net.primepro.primepro.service.impl;

import net.primepro.primepro.entity.Attendance;
import net.primepro.primepro.repository.AttendanceRepository;
import net.primepro.primepro.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Attendance markAttendance(Attendance attendance) {
        if (attendance.getEmployee() == null || attendance.getEmployee().getId() == null) {
            throw new IllegalArgumentException("Employee must be provided.");
        }
        List<Attendance> existingAttendances = attendanceRepository.findByEmployeeId(attendance.getEmployee().getId());

        for (Attendance existingAttendance : existingAttendances) {
            if (isSameDay(existingAttendance.getAttendanceDate(), attendance.getAttendanceDate())) {
                throw new IllegalArgumentException("Attendance already mark to this employee.");
            }
        }
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    @Override
    public void deleteAttendance(Long attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}
