package net.primepro.primepro.repository;

import net.primepro.primepro.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
