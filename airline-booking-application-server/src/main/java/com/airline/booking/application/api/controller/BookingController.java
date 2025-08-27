package com.airline.booking.application.api.controller;

import com.airline.booking.application.api.entity.enums.BookingStatus;
import com.airline.booking.application.api.payload.request.BookingRequest;
import com.airline.booking.application.api.payload.request.BookingSubmitRequest;
import com.airline.booking.application.api.payload.response.Response;
import com.airline.booking.application.api.service.IBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Created 27/08/2025 - 11:44
 * @Package com.airline.booking.application.api.controller
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final IBookingService bookingService;


    @PostMapping
    public ResponseEntity<Response<?>> createBooking(@Valid @RequestBody BookingSubmitRequest createBookingRequest) {
        return ResponseEntity.ok(bookingService.createBooking(createBookingRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<BookingRequest>> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PILOT')")
    public ResponseEntity<Response<List<BookingRequest>>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/me")
    public ResponseEntity<Response<List<BookingRequest>>> getMyBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PILOT')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateBookingStatus(@PathVariable Long id, @RequestBody BookingStatus status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }

}
