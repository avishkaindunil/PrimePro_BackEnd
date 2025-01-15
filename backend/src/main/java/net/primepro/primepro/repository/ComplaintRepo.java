package net.primepro.primepro.repository;

import net.primepro.primepro.entity.Complaints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepo extends JpaRepository<Complaints,Integer> {
    List<Complaints> findByIsResolvedFalse();
}
