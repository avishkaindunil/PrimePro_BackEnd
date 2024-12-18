package net.primepro.primepro.repository;

import net.primepro.primepro.entity.Attendance;
import net.primepro.primepro.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByEmployeeId(Integer employeeId);

    @Query("SELECT t FROM Task t WHERE t.employee.id = :employeeId AND t.taskDate = CURRENT_DATE")
    List<Task> findTaskByEmployeeIdAndDate(@Param("employeeId") Integer employeeId);

    List<Task> findByCustomerId(Integer customerId);

    Long countByEmployeeId(Integer employeeId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.customerId = :customerId AND t.taskDate = CURRENT_DATE")
    Long countByCustomerIdAndStartDateToday(@Param("customerId") Integer customerId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.employee.id = :employeeId AND t.taskDate = CURRENT_DATE")
    Long countByEmployerIdAndStartDateToday(@Param("employeeId") Integer employeeId);

    @Query("SELECT t.taskDate, COUNT(t) AS taskCount " +
            "FROM Task t WHERE t.taskDate BETWEEN :startDate AND :endDate " +
            "AND t.employee.id = :employeeId " +
            "GROUP BY t.taskDate " +
            "ORDER BY t.taskDate DESC")
    List<Object[]> countTasksByMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("employeeId") Integer employeeId);



}
