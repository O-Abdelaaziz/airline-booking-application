package com.airline.booking.application.api.service;

import com.airline.booking.application.api.entity.Booking;
import com.airline.booking.application.api.entity.User;

/**
 * @Created 26/08/2025 - 16:22
 * @Package com.airline.booking.application.api.service
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public interface IEmailNotificationService {
    void sendBookingTickerEmail(Booking booking);
    void sendWelcomeEmail(User user);
}
