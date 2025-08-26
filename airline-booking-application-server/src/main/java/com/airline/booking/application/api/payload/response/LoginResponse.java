package com.airline.booking.application.api.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Created 26/08/2025 - 09:30
 * @Package com.airline.booking.application.api.payload.response
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private List<String> roles;
}
