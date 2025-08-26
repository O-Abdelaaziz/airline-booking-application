package com.airline.booking.application.api.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Created 26/08/2025 - 08:19
 * @Package com.airline.booking.application.api.payload.request
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private Long id;
    private String name;
    private String description;
}
