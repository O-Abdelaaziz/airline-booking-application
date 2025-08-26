package com.airline.booking.application.api.repository;

import com.airline.booking.application.api.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Created 26/08/2025 - 11:06
 * @Package com.airline.booking.application.api.repository
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Repository
public interface IPassengerRepository extends JpaRepository<Passenger, Long> {
}
