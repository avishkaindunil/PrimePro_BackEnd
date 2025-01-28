package net.primepro.primepro.service.impl;

import net.primepro.primepro.dto.LeaveRequestDto;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.entity.LeaveRequest;
import net.primepro.primepro.entity.OurUsers;
import net.primepro.primepro.repository.EmployeeRepository;
import net.primepro.primepro.repository.LeaveRequestRepository;
import net.primepro.primepro.repository.UsersRepo;
import net.primepro.primepro.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UsersRepo usersRepo;

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
        if (leaveRequestId == null || isApproved == null) {
            throw new IllegalArgumentException("Leave request ID and approval status must not be null.");
        }

        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
        leaveRequest.setIsApproved(isApproved);

        if (isApproved.equals("approved")) {
            // Calculate the number of leave days
            LocalDate startDate = leaveRequest.getStartDate();
            LocalDate endDate = leaveRequest.getEndDate();
            Integer noOfDays = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate) + 1); // Include the start date

            Integer userId = leaveRequest.getUserId(); // Assuming LeaveRequest contains userId
            OurUsers user = usersRepo.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for the given userId"));

            Employee employee = user.getEmployee();
            if (employee == null) {
                throw new IllegalArgumentException("Employee not associated with this user.");
            }

            String leaveType = leaveRequest.getLeaveType();
            switch (leaveType.toLowerCase()) {
                case "annual":
                    if (employee.getNoOfAnnualLeaves() < noOfDays) {
                        throw new IllegalArgumentException("Insufficient annual leave balance.");
                    }
                    employee.setNoOfAnnualLeaves(employee.getNoOfAnnualLeaves() - noOfDays);
                    break;
                case "sick":
                    if (employee.getNoOfMedicalLeaves() < noOfDays) {
                        throw new IllegalArgumentException("Insufficient sick leave balance.");
                    }
                    employee.setNoOfMedicalLeaves(employee.getNoOfMedicalLeaves() - noOfDays);
                    break;
                case "casual":
                    if (employee.getNoOfCasualLeaves() < noOfDays) {
                        throw new IllegalArgumentException("Insufficient casual leave balance.");
                    }
                    employee.setNoOfCasualLeaves(employee.getNoOfCasualLeaves() - noOfDays);
                    break;
                case "earned":
                    System.out.println("earned leave");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid leave type: " + leaveType);
            }

            employeeRepository.save(employee);

        } else if (isApproved.equals("rejected")) {
            // Handle rejected logic (if needed)
        } else {
            throw new IllegalArgumentException("Approval status must be either 'approved' or 'rejected'.");
        }

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

    @Override
    public List<LeaveRequestDto> getAllLeaveRequests() {
        List<Object[]> requests = leaveRequestRepository.findAllLeaveRequests();
        return requests.stream().map(request -> {
            LeaveRequestDto dto = new LeaveRequestDto();
            dto.setEmployeeName((String) request[0]);
            dto.setLeaveRequestId((int) request[1]);
            dto.setLeaveType((String) request[2]);
            dto.setStartDate((Date) request[3]);
            dto.setEndDate((Date) request[4]);
            dto.setReason((String) request[5]);
            dto.setIsApproved((String) request[6]);
            return dto;
        }).toList();
    }
}
