package com.airline.booking.application.api.service.impl;

import com.airline.booking.application.api.entity.Role;
import com.airline.booking.application.api.entity.User;
import com.airline.booking.application.api.entity.enums.AuthenticationMethod;
import com.airline.booking.application.api.exception.BadRequestException;
import com.airline.booking.application.api.exception.ResourceNotFoundException;
import com.airline.booking.application.api.payload.request.LoginRequest;
import com.airline.booking.application.api.payload.request.RegistrationRequest;
import com.airline.booking.application.api.payload.response.LoginResponse;
import com.airline.booking.application.api.payload.response.Response;
import com.airline.booking.application.api.repository.IRoleRepository;
import com.airline.booking.application.api.repository.IUserRepository;
import com.airline.booking.application.api.security.TokenProvider;
import com.airline.booking.application.api.service.IAuthenticationService;
import com.airline.booking.application.api.service.IEmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Created 26/08/2025 - 19:55
 * @Package com.airline.booking.application.api.service.impl
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final IRoleRepository iRoleRepository;
    private final IEmailNotificationService iEmailNotificationService;


    @Override
    public Response<?> register(RegistrationRequest registrationRequest) {
        log.info("Inside register()");
        if (iUserRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new BadRequestException("Email already exist");
        }

        Set<Role> userRoles;

        if (registrationRequest.getRoles() != null && !registrationRequest.getRoles().isEmpty()) {
            userRoles = registrationRequest.getRoles().stream()
                    .map(roleName -> iRoleRepository.findByName(roleName.toUpperCase())
                            .orElseThrow(() -> new ResourceNotFoundException("Role " + roleName + "Not Found")))
                    .collect(Collectors.toSet());
        } else {
            Role defaultRole = iRoleRepository.findByName("CUSTOMER")
                    .orElseThrow(() -> new ResourceNotFoundException("Role CUSTOMER DOESN'T EXISTS"));
            userRoles = Set.of(defaultRole);
        }

        User userToSave = new User();
        userToSave.setName(registrationRequest.getName());
        userToSave.setEmail(registrationRequest.getEmail());
        userToSave.setPhoneNumber(registrationRequest.getPhoneNumber());
        userToSave.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userToSave.setRoles(userRoles);
        userToSave.setCreatedAt(LocalDateTime.now());
        userToSave.setUpdatedAt(LocalDateTime.now());
        userToSave.setProvider(AuthenticationMethod.LOCAL);
        userToSave.setActive(true);

        User savedUser = iUserRepository.save(userToSave);

        iEmailNotificationService.sendWelcomeEmail(savedUser);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("user registered sucessfully")
                .build();
    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest) {

        log.info("Inside login()");
        User user = iUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email Not Found"));

        if (!user.isActive()) {
            throw new ResourceNotFoundException("Acount Not Active, Please reach Out to Customer Care...");
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid Password");
        }

        String token = tokenProvider.generateToken(user.getEmail());

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRoles(roleNames);

        return Response.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login Successful")
                .data(loginResponse)
                .build();
    }
}
