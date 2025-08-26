package com.airline.booking.application.api.entity;

import com.airline.booking.application.api.entity.common.BaseEntity;
import com.airline.booking.application.api.entity.enums.City;
import com.airline.booking.application.api.entity.enums.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Created 26/08/2025 - 08:33
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
@Table(name = "airports")
public class Airport extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "city", nullable = false)
    @Enumerated(EnumType.STRING)
    private City city;

    @Enumerated(EnumType.STRING)
    @Column(name = "country", nullable = false)
    private Country country;

    @Column(name = "iata_code", unique = true, nullable = false, length = 3)
    private String iataCode;
}
