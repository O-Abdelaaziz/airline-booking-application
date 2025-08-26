package com.airline.booking.application.api.exception;

/**
 * @Created 26/08/2025 - 12:27
 * @Package com.airline.booking.application.api.exception
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
