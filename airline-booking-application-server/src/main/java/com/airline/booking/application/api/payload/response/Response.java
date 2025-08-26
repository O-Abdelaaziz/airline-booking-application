package com.airline.booking.application.api.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * @Created 26/08/2025 - 10:02
 * @Package com.airline.booking.application.api.payload.response
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private int statusCode;
    private String message;
    private T data;
}
