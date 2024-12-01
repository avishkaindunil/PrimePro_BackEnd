package net.primepro.primepro.repository;

import net.primepro.primepro.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByEmployeeId(Integer employeeId);

    List<Task> findByCustomerId(Integer customerId);
}
