package com.airline.booking.application.api.payload.request;

import com.airline.booking.application.api.entity.Booking;
import com.airline.booking.application.api.entity.Role;
import com.airline.booking.application.api.entity.enums.AuthenticationMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Created 26/08/2025 - 08:28
 * @Package com.airline.booking.application.api.payload.request
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean emailVerified;
    private AuthenticationMethod provider;
    private String providerId;
    private boolean active;
    private Set<Role> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
