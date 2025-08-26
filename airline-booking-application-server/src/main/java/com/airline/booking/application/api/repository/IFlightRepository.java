package com.airline.booking.application.api.repository;

import com.airline.booking.application.api.entity.Flight;
import com.airline.booking.application.api.entity.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Created 26/08/2025 - 10:12
 * @Package com.airline.booking.application.api.repository
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Repository
public interface IFlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);

    boolean existsByFlightNumber(String flightNumber);

    List<Flight> findByDepartureAirportId(Long departureAirportId);

    List<Flight> findByArrivalAirportId(Long arrivalAirportId);

    List<Flight> findByDepartureAirportIataCodeAndArrivalAirportIataCodeAndStatusAndDepartureTimeBetween(
            String departureAirportIataCode,
            String arrivalAirportIataCode,
            FlightStatus status,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime
    );
}
