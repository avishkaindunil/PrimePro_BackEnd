package net.primepro.primepro.service.impl;

import net.primepro.primepro.dto.LeaveRequestDto;
import net.primepro.primepro.entity.LeaveRequest;
import net.primepro.primepro.repository.LeaveRequestRepository;
import net.primepro.primepro.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

        //leaveRequest.setApproved(false);
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public LeaveRequest approveLeaveRequest(Integer leaveRequestId, String isApproved) {
        if (leaveRequestId == null || isApproved == null){
            throw new IllegalArgumentException("Leave request ID and approval status must not be null.");
        }
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
        leaveRequest.setIsApproved(isApproved);

        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public List<LeaveRequest> getLeaveRequestsByUserId(Integer userId) {
        return leaveRequestRepository.findByUserId(userId);
    }

    @Override
    public List<LeaveRequestDto> getLeaveRequests() {
        List<Object[]> results = leaveRequestRepository.findLeaveRequest();
        return results.stream().map(result -> {
            LeaveRequestDto dto = new LeaveRequestDto();
            dto.setEmployeeName((String) result[0]);
            dto.setLeaveRequestId((int) result[1]);
            dto.setLeaveType((String) result[2]);
            dto.setStartDate((Date) result[3]);
            dto.setEndDate((Date) result[4]);
            dto.setReason((String) result[5]);
            return dto;
        }).toList();
    }
}
