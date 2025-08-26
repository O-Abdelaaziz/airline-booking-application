package com.airline.booking.application.api.payload.request;

import com.airline.booking.application.api.entity.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Created 26/08/2025 - 08:46
 * @Package com.airline.booking.application.api.payload.request
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Long id;

    private String bookingReference;

    private UserRequest user;

    private FlightRequest flight;

    private LocalDateTime bookingDate;

    private BookingStatus status;

    private List<PassengerRequest> passengers;
}
