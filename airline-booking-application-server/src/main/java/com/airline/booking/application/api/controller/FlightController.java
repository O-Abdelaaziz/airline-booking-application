package com.airline.booking.application.api.controller;

import com.airline.booking.application.api.entity.enums.City;
import com.airline.booking.application.api.entity.enums.Country;
import com.airline.booking.application.api.entity.enums.FlightStatus;
import com.airline.booking.application.api.payload.request.FlightRequest;
import com.airline.booking.application.api.payload.request.FlightSubmitRequest;
import com.airline.booking.application.api.payload.response.Response;
import com.airline.booking.application.api.service.IFlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @Created 27/08/2025 - 11:30
 * @Package com.airline.booking.application.api.controller
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
public class FlightController {
    private final IFlightService iFlightService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PILOT')")
    public ResponseEntity<Response<?>> createFlight(@Valid @RequestBody FlightSubmitRequest createFlightRequest) {
        return ResponseEntity.ok(iFlightService.createFlight(createFlightRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<FlightRequest>> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(iFlightService.getFlightById(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<FlightRequest>>> getAllFlights() {
        return ResponseEntity.ok(iFlightService.getAllFlights());
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PILOT')")
    public ResponseEntity<Response<?>> updateFlight(@RequestBody FlightSubmitRequest flightRequest) {
        return ResponseEntity.ok(iFlightService.updateFlight(flightRequest));
    }


    @GetMapping("/search")
    public ResponseEntity<Response<List<FlightRequest>>> searchFlight(
            @RequestParam(required = true) String departureIataCode,
            @RequestParam(required = true) String arrivalIataCode,
            @RequestParam(required = false, defaultValue = "SCHEDULED") FlightStatus status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate
    ) {
        return ResponseEntity.ok(iFlightService.searchFlight(departureIataCode, arrivalIataCode, status, departureDate));
    }

    @GetMapping("/cities")
    public ResponseEntity<Response<List<City>>> getAllCities() {
        return ResponseEntity.ok(iFlightService.getAllCities());
    }

    @GetMapping("/countries")
    public ResponseEntity<Response<List<Country>>> getAllCountries() {
        return ResponseEntity.ok(iFlightService.getAllCountries());
    }
}
