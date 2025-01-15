package net.primepro.primepro.controller;


import net.primepro.primepro.dto.BookingDto;
import net.primepro.primepro.dto.ReqRes;
import net.primepro.primepro.entity.Booking;
import net.primepro.primepro.service.BookingService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/booking/add")
    public Booking add_booking(@RequestBody Booking booking) {
        System.out.println("kkkkkkkkk");
        return  bookingService.addBooking(booking);
//        return ResponseEntity.ok(bookingService.addBooking(booking));
    }

//    @PostMapping("/auth/register")
//    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg){
//        return ResponseEntity.ok(usersManagementService.register(reg));
//    }
    @GetMapping("/booking/view")
    public List<Booking> view_all_bookings() {
        return bookingService.viewAllBookings();

    }

    @GetMapping("/booking/weekly-progress")
    public Map<String, Object> getWeeklyBookingProgress() {
        List<Object[]> results = bookingService.getBookingsForCurrentWeek();
        Map<String, Integer> bookingCounts = new HashMap<>();

        for (Object[] row : results) {
            bookingCounts.put((String) row[0], ((Long) row[1]).intValue());
        }
        return Map.of("data", bookingCounts);
    }


}
