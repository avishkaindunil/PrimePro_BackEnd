package net.primepro.primepro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    private Integer employeeId;
    private LocalDate attendanceDate;
    private Time checkInTime;
    private Time checkOutTime;
    private Integer overtime;
    private Integer workHours;
    private boolean isApproved;
}
