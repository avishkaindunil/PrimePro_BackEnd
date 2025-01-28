package net.primepro.primepro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceNewDTO {

    private Integer id;
    private LocalDate attendanceDate;
    private String name;
    private Time checkInTime;
    private Time checkOutTime;
    private Integer isApproved;


}
