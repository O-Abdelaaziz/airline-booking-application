package com.airline.booking.application.api.controller;

import com.airline.booking.application.api.payload.request.AirportRequest;
import com.airline.booking.application.api.payload.response.Response;
import com.airline.booking.application.api.service.IAirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Created 26/08/2025 - 21:27
 * @Package com.airline.booking.application.api.controller
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@RestController
@RequestMapping("/api/v1/airports")
@RequiredArgsConstructor
public class AirportController {

    private final IAirportService airportService;

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> updateAirport(@RequestBody AirportRequest airportRequest) {
        return ResponseEntity.ok(airportService.updateAirport(airportRequest));
    }

    @GetMapping
    public ResponseEntity<Response<List<AirportRequest>>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response<AirportRequest>> getAirportById(@PathVariable Long id) {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }
}
