package net.primepro.primepro.service;

import net.primepro.primepro.entity.LeaveRequest;

import java.util.List;

public interface LeaveRequestService {

    LeaveRequest submitLeaveRequest(LeaveRequest leaveRequest);
    LeaveRequest approveLeaveRequest(Integer leaveRequestId, Boolean isApproved);
    List<LeaveRequest> getLeaveRequestsByUserId(Integer userId);

}
