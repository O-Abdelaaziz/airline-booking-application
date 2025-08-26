package com.airline.booking.application.api.payload.request;

import com.airline.booking.application.api.entity.enums.City;
import com.airline.booking.application.api.entity.enums.Country;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Created 26/08/2025 - 08:37
 * @Package com.airline.booking.application.api.payload.request
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportRequest {
    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message = " City is required")
    private City city;

    @NotNull(message = "Country is required")
    private Country country;

    @NotBlank(message = "Iata Code is required")
    @Size(min = 3, max = 3, message = "Iata Code must be 3 characters long")
    private String iataCode;
}
