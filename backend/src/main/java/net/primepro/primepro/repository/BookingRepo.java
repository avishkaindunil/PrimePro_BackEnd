package net.primepro.primepro.repository;

import net.primepro.primepro.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking,Integer> {

}
