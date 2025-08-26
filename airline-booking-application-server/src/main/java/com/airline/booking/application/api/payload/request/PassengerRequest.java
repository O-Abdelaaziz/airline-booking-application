package com.airline.booking.application.api.payload.request;

import com.airline.booking.application.api.entity.enums.PassengerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Created 26/08/2025 - 08:48
 * @Package com.airline.booking.application.api.payload.request
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerRequest {
    private Long id;

    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    private String passportNumber;

    @NotNull(message = "Passenger Type cannot be null")
    private PassengerType type;

    private String seatNumber;

    private String specialRequests;
}
