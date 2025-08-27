package com.airline.booking.application.api.entity.enums;

import lombok.Getter;

/**
 * @Created 25/08/2025 - 20:50
 * @Package com.airline.booking.application.api.entity.enums
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Getter
public enum City {

    LAGOS(Country.NIGERIA),
    ABUJA(Country.NIGERIA),
    KANO(Country.NIGERIA),
    KADUNA(Country.NIGERIA),
    IBRAHIMIYA(Country.NIGERIA),

    NEW_YORK(Country.USA),
    MIAMI(Country.USA),
    DALLAS(Country.USA),

    MANCHESTER(Country.UK),
    BIRMINGHAM(Country.UK),
    LIVERPOOL(Country.UK),
    LEEDS(Country.UK),
    BRIGHTON(Country.UK),
    LONDON(Country.UK);

    private final Country country;

    City(Country country) {
        this.country = country;
    }

}
