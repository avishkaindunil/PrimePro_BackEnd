package net.primepro.primepro.repository;

import net.primepro.primepro.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    Employee findByEmployeeId(String employeeId);

    @Query(value = "select ou.name, em.id FROM ourusers ou JOIN employee em ON ou.id = em.user_id", nativeQuery = true)
    List<?> findEmployeeDetails();
}
