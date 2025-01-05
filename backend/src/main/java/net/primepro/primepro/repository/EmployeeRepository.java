package net.primepro.primepro.repository;

import net.primepro.primepro.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    @Query(value = "select us.name, us.email, us.city, em.phone_number, em.designation, em.nic, em.phone_number, em.date_of_birth, em.no_of_annual_leaves, em.no_of_casual_leaves from ourusers us join\n" +
            "employee em on us.id = em.user_id where us.id = :employeeId", nativeQuery = true)
    Object findByEmployeeId(@Param("employeeId") Integer employeeId);

    @Query(value = "select ou.name, em.id FROM ourusers ou JOIN employee em ON ou.id = em.user_id", nativeQuery = true)
    List<?> findEmployeeDetails();
}
