package net.primepro.primepro.service;

import net.primepro.primepro.dto.BookingDto;
import net.primepro.primepro.entity.Booking;

import java.util.List;

public interface BookingService {

    Booking addBooking(Booking booking);
    List<Booking> viewAllBookings();
    List<Object[]> getBookingsForCurrentWeek();

}
