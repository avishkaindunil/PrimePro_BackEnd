package net.primepro.primepro.repository;

import net.primepro.primepro.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {

}
