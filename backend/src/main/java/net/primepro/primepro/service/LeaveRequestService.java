package net.primepro.primepro.service;

import net.primepro.primepro.dto.LeaveRequestDto;
import net.primepro.primepro.entity.LeaveRequest;

import java.util.List;

public interface LeaveRequestService {

    LeaveRequest submitLeaveRequest(LeaveRequest leaveRequest);
    LeaveRequest approveLeaveRequest(Integer leaveRequestId, String isApproved);
    List<LeaveRequest> getLeaveRequestsByUserId(Integer userId);
    List<LeaveRequestDto> getLeaveRequests();
}
