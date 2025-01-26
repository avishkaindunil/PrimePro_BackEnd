package net.primepro.primepro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name= "booking")
@Data
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;
    private String centerName;
    private Integer UserID;
    private Date date;
    private String carName;
    private String service;
    private boolean isTaskAssigned;
    private Time startTime;
    private Time endTime;

    public Booking() {
    }
}

//    public Integer getBookingId() {
//        return bookingId;
//    }
//
//    public void setBookingId(Integer bookingId) {
//        this.bookingId = bookingId;
//    }
//
//    public String getCenterName() {
//        return centerName;
//    }
//
//    public void setCenterName(String centerName) {
//        this.centerName = centerName;
//    }
//
//    public Integer getUserID() {
//        return UserID;
//    }
//
//    public void setUserID(Integer userID) {
//        this.UserID = userID;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public String getCarName() {
//        return carName;
//    }
//
//    public void setCarName(String carName) {
//        this.carName = carName;
//    }
//
//    public String getService() {
//        return service;
//    }
//
//    public void setService(String service) {
//        this.service = service;
//    }
//}
