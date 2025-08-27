package com.airline.booking.application.api.service.impl;

import com.airline.booking.application.api.entity.Booking;
import com.airline.booking.application.api.entity.Flight;
import com.airline.booking.application.api.entity.Passenger;
import com.airline.booking.application.api.entity.User;
import com.airline.booking.application.api.entity.enums.BookingStatus;
import com.airline.booking.application.api.entity.enums.FlightStatus;
import com.airline.booking.application.api.exception.BadRequestException;
import com.airline.booking.application.api.exception.ResourceNotFoundException;
import com.airline.booking.application.api.payload.request.BookingRequest;
import com.airline.booking.application.api.payload.request.BookingSubmitRequest;
import com.airline.booking.application.api.payload.response.Response;
import com.airline.booking.application.api.repository.IBookingRepository;
import com.airline.booking.application.api.repository.IFlightRepository;
import com.airline.booking.application.api.repository.IPassengerRepository;
import com.airline.booking.application.api.service.IBookingService;
import com.airline.booking.application.api.service.IEmailNotificationService;
import com.airline.booking.application.api.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @Created 27/08/2025 - 11:39
 * @Package com.airline.booking.application.api.service.impl
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements IBookingService {
    private final IBookingRepository bookingRepo;
    private final IUserService userService;
    private final IFlightRepository flightRepo;
    private final IPassengerRepository passengerRepo;
    private final ModelMapper modelMapper;
    private final IEmailNotificationService emailNotificationService;


    @Override
    @Transactional
    public Response<?> createBooking(BookingSubmitRequest createBookingRequest) {

        User user = userService.currentUser();

        Flight flight = flightRepo.findById(createBookingRequest.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight Not Found"));

        if (flight.getStatus() != FlightStatus.SCHEDULED) {
            throw new BadRequestException("You can only book a flight that is scheduled");
        }

        Booking booking = new Booking();
        booking.setBookingReference(generateBookingReference());
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = bookingRepo.save(booking);

        if (createBookingRequest.getPassengers() != null && !createBookingRequest.getPassengers().isEmpty()) {

            List<Passenger> passengers = createBookingRequest.getPassengers().stream()
                    .map(passengerDTO -> {
                        Passenger passenger = modelMapper.map(passengerDTO, Passenger.class);
                        passenger.setBooking(savedBooking);
                        return passenger;
                    }).toList();

            passengerRepo.saveAll(passengers);
            savedBooking.setPassengers(passengers);
        }

        //SEND EMAIL TICKER OUT
        emailNotificationService.sendBookingTickerEmail(savedBooking);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Booking created successfully")
                .build();

    }

    @Override
    public Response<BookingRequest> getBookingById(Long id) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        BookingRequest bookingDTO = modelMapper.map(booking, BookingRequest.class);
        bookingDTO.getFlight().setBookings(null);

        return Response.<BookingRequest>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Booking retreived successfully")
                .data(bookingDTO)
                .build();
    }

    @Override
    public Response<List<BookingRequest>> getAllBookings() {

        List<Booking> allBookings = bookingRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<BookingRequest> bookings = allBookings.stream()
                .map(booking -> {
                    BookingRequest bookingDTO = modelMapper.map(booking, BookingRequest.class);
                    bookingDTO.getFlight().setBookings(null);
                    return bookingDTO;
                }).toList();

        return Response.<List<BookingRequest>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(bookings.isEmpty() ? "No Booking Found" : "Booking retreived successfully")
                .data(bookings)
                .build();
    }

    @Override
    public Response<List<BookingRequest>> getMyBookings() {
        User user = userService.currentUser();
        List<Booking> userBookings = bookingRepo.findByUserIdOrderByIdDesc(user.getId());


        List<BookingRequest> bookings = userBookings.stream()
                .map(booking -> {
                    BookingRequest bookingDTO = modelMapper.map(booking, BookingRequest.class);
                    bookingDTO.getFlight().setBookings(null);
                    return bookingDTO;
                }).toList();

        return Response.<List<BookingRequest>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(bookings.isEmpty() ? "No Booking Found for this user" : "User Bookings retrieved successfully")
                .data(bookings)
                .build();

    }

    @Override
    @Transactional
    public Response<?> updateBookingStatus(Long id, BookingStatus status) {

        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking Not Found"));

        booking.setStatus(status);
        bookingRepo.save(booking);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Booking Updated Successfully")
                .build();

    }

    private String generateBookingReference() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
