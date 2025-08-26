package com.airline.booking.application.api.controller;

import com.airline.booking.application.api.payload.request.RoleRequest;
import com.airline.booking.application.api.payload.response.Response;
import com.airline.booking.application.api.service.IRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Created 26/08/2025 - 18:19
 * @Package com.airline.booking.application.api.controller
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    private final IRoleService roleService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> createRole(@Valid @RequestBody RoleRequest roleRequest) {
        return ResponseEntity.ok(roleService.createRole(roleRequest));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> updateRole(@Valid @RequestBody RoleRequest roleRequest) {
        return ResponseEntity.ok(roleService.updateRole(roleRequest));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PILOT')")
    public ResponseEntity<Response<?>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
