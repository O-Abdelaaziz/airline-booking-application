package com.airline.booking.application.api.service;

import com.airline.booking.application.api.entity.User;
import com.airline.booking.application.api.payload.request.UserRequest;
import com.airline.booking.application.api.payload.response.Response;

import java.util.List;

/**
 * @Created 26/08/2025 - 20:13
 * @Package com.airline.booking.application.api.service
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public interface IUserService {
    User currentUser();

    Response<?> updateMyAccount(UserRequest userRequest);

    Response<List<UserRequest>> getAllPilots();

    Response<UserRequest> getAccountDetails();
}
