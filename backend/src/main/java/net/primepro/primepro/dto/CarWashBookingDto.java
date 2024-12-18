package net.primepro.primepro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarWashBookingDto {
    private Integer customerId;
    private String description;
    private LocalDate taskDate;
    private Time startTime;
    private Time endTime;
}
