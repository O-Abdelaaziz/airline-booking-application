package com.airline.booking.application.api.controller;

import com.airline.booking.application.api.payload.request.UserRequest;
import com.airline.booking.application.api.payload.response.Response;
import com.airline.booking.application.api.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Created 26/08/2025 - 20:24
 * @Package com.airline.booking.application.api.controller
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;


    @PutMapping
    public ResponseEntity<Response<?>> updateMyAccount(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.updateMyAccount(userRequest));
    }

    @GetMapping("/pilots")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PILOT')")
    public ResponseEntity<Response<List<UserRequest>>> getAllPilots(){
        return ResponseEntity.ok(userService.getAllPilots());
    }

    @GetMapping("/me")
    public ResponseEntity<Response<UserRequest>> getAccountDetails(){
        return ResponseEntity.ok(userService.getAccountDetails());
    }
}
