package com.airline.booking.application.api.service;

import com.airline.booking.application.api.payload.request.RoleRequest;
import com.airline.booking.application.api.payload.response.Response;

import java.util.List;

/**
 * @Created 26/08/2025 - 17:52
 * @Package com.airline.booking.application.api.service
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public interface IRoleService {
    Response<?> createRole(RoleRequest roleRequest);
    Response<?> updateRole(RoleRequest roleRequest);
    Response<List<RoleRequest>> getAllRoles();
}
