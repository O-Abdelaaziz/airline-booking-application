package com.airline.booking.application.api.entity;

import com.airline.booking.application.api.entity.common.BaseEntity;
import com.airline.booking.application.api.entity.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created 26/08/2025 - 08:40
 * @Package com.airline.booking.application.api.entity
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flights")
public class Flight extends BaseEntity {

    @Column(name = "flight_number", unique = true, nullable = false)
    private String flightNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FlightStatus status;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport_id")
    private Airport arrivalAirport;

    @ManyToOne
    @JoinColumn(name = "assigned_pilot_id")
    private User assignedPilot;

    @OneToMany(mappedBy = "flight")
    private List<Booking> bookings = new ArrayList<>();
}
