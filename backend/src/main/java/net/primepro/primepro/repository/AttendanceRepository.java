package net.primepro.primepro.repository;

import net.primepro.primepro.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByEmployeeId(Integer id);

    @Query("SELECT a FROM Attendance a WHERE a.employee.id = :employeeId ORDER BY a.attendanceDate DESC")
    List<Attendance> findByEmployeeIdOrderedByDateDesc(@Param("employeeId") Integer employeeId);
}
