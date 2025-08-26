package com.airline.booking.application.api.repository;

import com.airline.booking.application.api.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Created 26/08/2025 - 10:39
 * @Package com.airline.booking.application.api.repository
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingReference(String bookingReference);

    boolean existsByBookingReference(String bookingReference);

    List<Booking> findByUserIdOrderByIdDesc(Long userId);
}
