package net.primepro.primepro.repository;

import net.primepro.primepro.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepo extends JpaRepository<Inventory,Long> {

}
