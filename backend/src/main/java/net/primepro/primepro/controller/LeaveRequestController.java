package net.primepro.primepro.controller;

import net.primepro.primepro.dto.LeaveRequestDto;
import net.primepro.primepro.entity.LeaveRequest;
import net.primepro.primepro.entity.Task;
import net.primepro.primepro.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave-requests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("/add")
    public ResponseEntity<?> submitLeaveRequest(@RequestBody LeaveRequest leaveRequest) {
        try {
            LeaveRequest savedRequest = leaveRequestService.submitLeaveRequest(leaveRequest);
            return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/employee/{userId}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByUserId(@PathVariable Integer userId) {
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByUserId(userId);
        return ResponseEntity.ok(leaveRequests);
    }

    @PostMapping("/approve")
    public ResponseEntity<LeaveRequest> approveLeaveRequest(@RequestBody LeaveRequestDto leaveRequestDto) {
        LeaveRequest leaveRequest = leaveRequestService.approveLeaveRequest(leaveRequestDto.getLeaveRequestId(), leaveRequestDto.getIsApproved());
        return ResponseEntity.ok(leaveRequest);
    }

}
