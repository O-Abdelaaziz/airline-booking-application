package com.airline.booking.application.api.entity;

import com.airline.booking.application.api.entity.common.BaseEntity;
import com.airline.booking.application.api.entity.enums.PassengerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Created 26/08/2025 - 08:49
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
@Table(name = "passengers")
public class Passenger extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "passenger_type", nullable = false)
    private PassengerType type;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "special_requests")
    private String specialRequests;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
