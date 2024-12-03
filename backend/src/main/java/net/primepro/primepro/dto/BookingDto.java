package net.primepro.primepro.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.primepro.primepro.entity.Booking;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class BookingDto {

    private int statusCode;
    private String centerName;
    private Integer UserID;
    private Date date;
    private String carName;
    private String service;
    private Booking booking;

    public int getStatusCode() {
        return statusCode;
    }


}



