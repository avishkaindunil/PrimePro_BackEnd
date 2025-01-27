package net.primepro.primepro.dto;

import lombok.Data;
import java.util.Date;

@Data
public class LeaveRequestDto {
    private int leaveRequestId;
    private String isApproved;

    private String employeeName;
    private String leaveType;
    private Date startDate;
    private Date endDate;
    private String reason;
}
