package com.airline.booking.application.api.entity;

import com.airline.booking.application.api.entity.common.BaseEntity;
import com.airline.booking.application.api.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created 26/08/2025 - 08:27
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
@Table(name = "bookings")
public class Booking extends BaseEntity {
    @Column(name = "booking_reference", unique = true, nullable = false)
    private String bookingReference;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Passenger> passengers = new ArrayList<>();
}
