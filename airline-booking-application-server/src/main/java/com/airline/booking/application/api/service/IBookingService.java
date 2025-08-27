package com.airline.booking.application.api.service;

import com.airline.booking.application.api.entity.enums.BookingStatus;
import com.airline.booking.application.api.payload.request.BookingRequest;
import com.airline.booking.application.api.payload.request.BookingSubmitRequest;
import com.airline.booking.application.api.payload.response.Response;

import java.util.List;

/**
 * @Created 27/08/2025 - 11:37
 * @Package com.airline.booking.application.api.service
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public interface IBookingService {
    Response<?> createBooking(BookingSubmitRequest createBookingRequest);

    Response<BookingRequest> getBookingById(Long id);

    Response<List<BookingRequest>> getAllBookings();

    Response<List<BookingRequest>> getMyBookings();

    Response<?> updateBookingStatus(Long id, BookingStatus status);
}
