package com.airline.booking.application.api.service;

import com.airline.booking.application.api.payload.request.LoginRequest;
import com.airline.booking.application.api.payload.request.RegistrationRequest;
import com.airline.booking.application.api.payload.response.LoginResponse;
import com.airline.booking.application.api.payload.response.Response;

/**
 * @Created 26/08/2025 - 19:55
 * @Package com.airline.booking.application.api.service
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public interface IAuthenticationService {
    Response<?> register(RegistrationRequest registrationRequest);

    Response<LoginResponse> login(LoginRequest loginRequest);
}
