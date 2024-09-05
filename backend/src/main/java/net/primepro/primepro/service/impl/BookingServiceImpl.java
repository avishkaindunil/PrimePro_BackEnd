package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.BookingDto;
import net.primepro.primepro.dto.ReqRes;
import net.primepro.primepro.entity.Booking;
import net.primepro.primepro.entity.OurUsers;
import net.primepro.primepro.repository.BookingRepo;
import net.primepro.primepro.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Override
    public Booking addBooking(Booking booking) {
//        BookingDto bookingDto1 = new BookingDto();

        Booking newBooking = new Booking();

        newBooking.setCenterName(booking.getCenterName());
        newBooking.setUserID(booking.getUserID());
        newBooking.setDate(booking.getDate());
        newBooking.setService(booking.getService());
        newBooking.setCarName(booking.getCarName());

        Booking savedBooking=  bookingRepo.save(newBooking);

        return savedBooking;
//        bookingDto1.setBooking(newBooking);
//        bookingDto1.setMessage("Booked Successfully");
//        bookingDto1.setStatusCode(200);
//        bookingDto1.setCarName();
//        bookingDto1.setCenterName();
//        bookingDto1.setService();
//        bookingDto1.getToken();


//        return bookingDto1;


//        Booking savedBooking = bookingRepo.save(booking);
//        return savedBooking;
    }

    @Override
    public List<Booking> viewAllBookings() {
        return bookingRepo.findAll();
    }
}

