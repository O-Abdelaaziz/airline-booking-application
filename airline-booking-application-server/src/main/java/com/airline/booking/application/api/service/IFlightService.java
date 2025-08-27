package com.airline.booking.application.api.service;

import com.airline.booking.application.api.entity.enums.City;
import com.airline.booking.application.api.entity.enums.Country;
import com.airline.booking.application.api.entity.enums.FlightStatus;
import com.airline.booking.application.api.payload.request.FlightRequest;
import com.airline.booking.application.api.payload.request.FlightSubmitRequest;
import com.airline.booking.application.api.payload.response.Response;

import java.time.LocalDate;
import java.util.List;

/**
 * @Created 27/08/2025 - 11:00
 * @Package com.airline.booking.application.api.service
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public interface IFlightService {
    Response<?> createFlight(FlightSubmitRequest createFlightRequest);

    Response<FlightRequest> getFlightById(Long id);

    Response<List<FlightRequest>> getAllFlights();

    Response<?> updateFlight(FlightSubmitRequest createFlightRequest);

    Response<List<FlightRequest>> searchFlight(String departurePortIata, String arrivalPortIata, FlightStatus status, LocalDate departureDate);

    Response<List<City>> getAllCities();

    Response<List<Country>> getAllCountries();
}
