package net.primepro.primepro.repository;


import net.primepro.primepro.entity.Booking;
import net.primepro.primepro.entity.CenterAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CenterAdminRepository extends JpaRepository<CenterAdmin,Integer> {
    Optional<CenterAdmin> findOneByEmailAndPassword(String email, String password);
    CenterAdmin findByEmail(String email);

    Optional<CenterAdmin> findByEmailAndPassword(String email, String encodedPassword);

    @Query(value = "select center_name from center_admin where id = ?",nativeQuery = true)
    String findCenterName(int centerId);

    // New development
//    @Query(value = "SELECT b FROM Booking b WHERE b.isBookingChecked = false AND b.date >= CURRENT_DATE")
//    List<Booking> findBookingsWithoutTimeAllocation();
//
//    @Query("SELECT b FROM Booking b WHERE b.isBookingChecked = true AND b.isTimeConfirmed = true AND b.isTaskAssigned = false AND b.date = CURRENT_DATE")
//    List<Booking> findBookingsWithoutTaskAssigned();

    @Query(
            value =
                    "SELECT bk.booking_id, bk.center_name, bk.userid , bk.date, bk.start_time, bk.end_time, bk.car_name, bk.service, tk.customer_id, tk.task_description, " +
                            "tk.task_status, em.employee_id, ou.name FROM booking bk JOIN task tk ON bk.booking_id = tk.booking_id " +
                            "JOIN employee em ON tk.employee_id = em.id join ourusers ou on ou.id = em.user_id where bk.date = CURRENT_DATE ",
            nativeQuery = true)
    List<?> getWorkLoadProgress();

    @Query(value = "SELECT ou.name AS employee_name, COUNT(tk.task_status) AS completed_task_count FROM task tk JOIN " +
            "employee em ON em.id = tk.employee_id JOIN ourusers ou ON ou.id = em.user_id WHERE tk.task_status = 'COMPLETED' " +
            "GROUP BY ou.name", nativeQuery = true)
    List<Object[]> getEmployeePerform();

    @Query(value = "SELECT TO_CHAR(bk.date, 'Day') AS day_of_week, COUNT(tk.task_status) AS completed_task_count FROM task tk JOIN booking bk\n" +
            "ON tk.booking_id = bk.booking_id WHERE tk.task_status = 'COMPLETED' AND bk.date >= date_trunc('week', CURRENT_DATE) GROUP BY TO_CHAR(bk.date , 'Day')\n" +
            "ORDER BY TO_CHAR(bk.date, 'Day')", nativeQuery = true)
    List<Object[]> getTaskDistribution();

    @Query(value = "select tk.booking_id from task tk JOIN employee em on em.id = tk.employee_id join ourusers ou on ou.id = em.user_id where  em.user_id = ? " +
            "and (tk.task_status = 'ACCEPTED' or tk.task_status = 'PENDING') ORDER BY tk.booking_id asc LIMIT 1", nativeQuery = true)
    Integer findAllocatedSlotCount(int empId);

}
