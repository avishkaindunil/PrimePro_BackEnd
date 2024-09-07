package net.primepro.primepro.repository;

import net.primepro.primepro.entity.Complaints;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepo extends JpaRepository<Complaints,Integer> {
}
