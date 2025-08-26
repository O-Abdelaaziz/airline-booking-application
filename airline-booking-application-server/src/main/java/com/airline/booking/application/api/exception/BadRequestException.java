package com.airline.booking.application.api.exception;

/**
 * @Created 26/08/2025 - 12:55
 * @Package com.airline.booking.application.api.exception
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
