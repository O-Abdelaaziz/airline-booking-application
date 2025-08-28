package com.airline.booking.application.api.service.impl;

import com.airline.booking.application.api.entity.Booking;
import com.airline.booking.application.api.entity.EmailNotification;
import com.airline.booking.application.api.entity.User;
import com.airline.booking.application.api.repository.IEmailNotificationRepository;
import com.airline.booking.application.api.service.IEmailNotificationService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Created 26/08/2025 - 16:23
 * @Package com.airline.booking.application.api.service.impl
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailNotificationServiceImpl implements IEmailNotificationService {

    private final IEmailNotificationRepository emailNotificationRepository;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${frontend.login.url}")
    private String frontendLoginUrl;

    @Value("${view.booking.url}")
    private String viewBookingUrl;

    @Override
    @Transactional
    @Async
    public void sendBookingTickerEmail(Booking booking) {
        log.info("Inside sendBookingTickerEmail()");
        String recipientEmail = booking.getUser().getEmail();
        String subject = "Your Flight Booking Ticker - Reference";
        String templateName = "booking_ticket";

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("userName", booking.getUser().getName());
        templateVariables.put("bookingReference", booking.getBookingReference());
        templateVariables.put("flightNumber", booking.getFlight().getFlightNumber());
        templateVariables.put("departureAirportIataCode", booking.getFlight().getDepartureAirport().getIataCode());
        templateVariables.put("departureAirportName", booking.getFlight().getDepartureAirport().getName());
        templateVariables.put("departureAirportCity", booking.getFlight().getDepartureAirport().getCity());
        templateVariables.put("departureTime", booking.getFlight().getDepartureTime());
        templateVariables.put("arrivalAirportIataCode", booking.getFlight().getArrivalAirport().getIataCode());
        templateVariables.put("arrivalAirportName", booking.getFlight().getArrivalAirport().getName());
        templateVariables.put("arrivalAirportCity", booking.getFlight().getArrivalAirport().getCity());
        templateVariables.put("arrivalTime", booking.getFlight().getArrivalTime());
        templateVariables.put("basePrice", booking.getFlight().getBasePrice());
        templateVariables.put("passengers", booking.getPassengers());
        templateVariables.put("viewBookingUrl", viewBookingUrl);

        //Render the template content

        Context context = new Context();
        templateVariables.forEach(context::setVariable);
        String emailBody = templateEngine.process(templateName, context);

        //send the actual email with the template
        sendMailOut(recipientEmail, subject, emailBody, true, booking);
    }

    @Override
    @Transactional
    @Async
    public void sendWelcomeEmail(User user) {

        log.info("Sending welcome email to user: {}", user.getEmail());

        String recipientEmail = user.getEmail();
        String subject = "Welcome to Flight Airline!";
        String templateName = "welcome_user"; // Hardcoded template name for internal use

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("userName", user.getName());
        templateVariables.put("frontendLoginUrl", frontendLoginUrl);

        // Render the template content
        Context context = new Context();
        templateVariables.forEach(context::setVariable);
        String emailBody = templateEngine.process(templateName, context);

        sendMailOut(recipientEmail, subject, emailBody, true, null);

    }

    private void sendMailOut(String recipientEmail, String subject, String body, boolean isHtml, Booking booking) {
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(body, isHtml);

            log.info("About to send Email...");
            javaMailSender.send(mimeMessage);

            log.info("Email sent out ");

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        //save to the notification database table

        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setRecipientEmail(recipientEmail);
        emailNotification.setSubject(subject);
        emailNotification.setBody(body);
        emailNotification.setHtml(isHtml);
        emailNotification.setSentAt(LocalDateTime.now());
        emailNotification.setBooking(booking);

        emailNotificationRepository.save(emailNotification);
    }
}
