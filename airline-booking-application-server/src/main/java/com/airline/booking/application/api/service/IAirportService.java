package com.airline.booking.application.api.service;

import com.airline.booking.application.api.payload.request.AirportRequest;
import com.airline.booking.application.api.payload.response.Response;

import java.util.List;

/**
 * @Created 26/08/2025 - 21:17
 * @Package com.airline.booking.application.api.service
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public interface IAirportService {
    Response<?> createAirport(AirportRequest airportRequest);

    Response<?> updateAirport(AirportRequest airportRequest);

    Response<List<AirportRequest>> getAllAirports();

    Response<AirportRequest> getAirportById(Long id);

}
