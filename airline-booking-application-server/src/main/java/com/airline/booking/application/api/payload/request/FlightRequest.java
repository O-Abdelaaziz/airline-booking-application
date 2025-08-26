package com.airline.booking.application.api.payload.request;

import com.airline.booking.application.api.entity.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Created 26/08/2025 - 08:44
 * @Package com.airline.booking.application.api.payload.request
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequest {

    private Long id;

    private String flightNumber;

    private FlightStatus status;

    private AirportRequest departureAirport;

    private AirportRequest arrivalAirport;

    private BigDecimal basePrice;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private UserRequest assignedPilot;

    private List<BookingRequest> bookings;

    private String departureAirportIataCode;

    private String arrivalAirportIataCode;
}
