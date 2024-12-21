package net.primepro.primepro.service.impl;

import net.primepro.primepro.entity.LeaveRequest;
import net.primepro.primepro.repository.LeaveRequestRepository;
import net.primepro.primepro.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Override
    public LeaveRequest submitLeaveRequest(LeaveRequest leaveRequest) {
        Integer userId = leaveRequest.getUserId();

        List<LeaveRequest> existingLeaveRequests = leaveRequestRepository
                .findByUserIdAndDateRange(userId, leaveRequest.getStartDate(), leaveRequest.getEndDate());

        if (!existingLeaveRequests.isEmpty()) {
            throw new IllegalArgumentException("Leave request already exists for the given date range.");
        }

        leaveRequest.setApproved(false);
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public LeaveRequest approveLeaveRequest(Integer leaveRequestId, Boolean isApproved) {
        if (leaveRequestId == null || isApproved == null){
            throw new IllegalArgumentException("Leave request ID and approval status must not be null.");
        }
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        leaveRequest.setApproved(isApproved);

        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public List<LeaveRequest> getLeaveRequestsByUserId(Integer userId) {
        return leaveRequestRepository.findByUserId(userId);
    }
}
