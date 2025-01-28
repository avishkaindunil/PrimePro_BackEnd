package net.primepro.primepro.repository;

import net.primepro.primepro.dto.LeaveRequestDto;
import net.primepro.primepro.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    List<LeaveRequest> findByUserId(Integer userId);

    @Query("SELECT l FROM LeaveRequest l WHERE l.userId = :userId " +
            "AND ((l.startDate BETWEEN :startDate AND :endDate) OR (l.endDate BETWEEN :startDate AND :endDate))")
    List<LeaveRequest> findByUserIdAndDateRange(Integer userId, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT ou.name, lr.id, lr.leave_type, lr.start_date, lr.end_date, lr.reason FROM leave_request lr " +
            "JOIN ourusers ou ON lr.user_id = ou.id WHERE is_approved IS NULL", nativeQuery = true)
    List<Object[]> findLeaveRequest();

    @Query(value = "SELECT ou.name, lr.id, lr.leave_type, lr.start_date, lr.end_date, lr.reason, lr.is_approved FROM leave_request lr " +
            "JOIN ourusers ou ON lr.user_id = ou.id", nativeQuery = true)
    List<Object[]> findAllLeaveRequests();
}
