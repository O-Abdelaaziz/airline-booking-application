package com.airline.booking.application.api.service.impl;

import com.airline.booking.application.api.entity.User;
import com.airline.booking.application.api.exception.ResourceNotFoundException;
import com.airline.booking.application.api.payload.request.UserRequest;
import com.airline.booking.application.api.payload.response.Response;
import com.airline.booking.application.api.repository.IUserRepository;
import com.airline.booking.application.api.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Created 26/08/2025 - 20:13
 * @Package com.airline.booking.application.api.service.impl
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    @Override
    public User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }


    @Override
    public Response<?> updateMyAccount(UserRequest userDTO) {
        log.info("Inside updateMyAccount()");

        log.info(String.valueOf(userDTO));

        User user = currentUser();

        if (userDTO.getName() != null && !userDTO.getName().isBlank()) {
            user.setName(userDTO.getName());
        }

        if (userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().isBlank()) {
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encodedPassword);
        }

        user.setUpdatedAt(LocalDateTime.now());

        iUserRepository.save(user);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Account Updated Successfully")
                .build();


    }


    @Override
    public Response<List<UserRequest>> getAllPilots() {
        log.info("Inside getAllPilots()");

        List<UserRequest> pilots = iUserRepository.findByRoleName("PILOT").stream()
                .map(user -> modelMapper.map(user, UserRequest.class))
                .toList();

        return Response.<List<UserRequest>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(pilots.isEmpty() ? "No pilots Found" : "pilots retrieved succesfully")
                .data(pilots)
                .build();
    }


    @Override
    public Response<UserRequest> getAccountDetails() {
        log.info("Inside getAccountDetails()");

        User user = currentUser();

        UserRequest userDTO = modelMapper.map(user, UserRequest.class);

        return Response.<UserRequest>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success")
                .data(userDTO)
                .build();
    }
}
