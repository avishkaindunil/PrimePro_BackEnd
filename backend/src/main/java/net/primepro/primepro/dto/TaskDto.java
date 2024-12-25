package net.primepro.primepro.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import net.primepro.primepro.entity.Booking;
import net.primepro.primepro.entity.Employee;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Data
public class TaskDto {
    private Integer id;
    private Integer customerId;
    private Integer employeeId;
    private Integer bookingId;
    private String taskDescription;
    private LocalDate taskDate;
    private Time startTime;
    private Time endTime;
    private String taskStatus;
}
