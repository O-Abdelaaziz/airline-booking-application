package com.airline.booking.application.api.payload.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Created 26/08/2025 - 09:52
 * @Package com.airline.booking.application.api.payload.request
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingSubmitRequest {
    @NotNull(message = "Flight ID cannot be null")
    private Long flightId;

    @NotEmpty(message = "At least one passenger must be provided")
    private List<PassengerRequest> passengers;
}
