package net.primepro.primepro.response;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class BookingResponse {
    private Integer bookingId;
    private String centerName;
    private Integer userId;
    private Date date;
    private String carName;
    private String service;

    private Integer customerId;
    private String taskDescription;
    private Date taskDate;
    private Time startTime;
    private Time endTime;
    private String taskStatus;

    private String employeeId;
}
