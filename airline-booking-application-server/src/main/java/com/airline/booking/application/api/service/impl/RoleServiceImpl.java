package com.airline.booking.application.api.service.impl;

import com.airline.booking.application.api.entity.Role;
import com.airline.booking.application.api.exception.ResourceNotFoundException;
import com.airline.booking.application.api.payload.request.RoleRequest;
import com.airline.booking.application.api.payload.response.Response;
import com.airline.booking.application.api.repository.IRoleRepository;
import com.airline.booking.application.api.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Created 26/08/2025 - 17:54
 * @Package com.airline.booking.application.api.service.impl
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response<?> createRole(RoleRequest roleDTO) {

        log.info("Inside createRole()");

        Role role = modelMapper.map(roleDTO, Role.class);

        role.setName(role.getName().toUpperCase());

        roleRepository.save(role);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role Created Successfully")
                .build();
    }

    @Override
    public Response<?> updateRole(RoleRequest roleDTO) {
        log.info("Inside updateRole()");

        Long id = roleDTO.getId();

        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        existingRole.setName(roleDTO.getName().toUpperCase());
        roleRepository.save(existingRole);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role updated Successfully")
                .build();

    }

    @Override
    public Response<List<RoleRequest>> getAllRoles() {
        log.info("Inside getAllRoles()");
        List<RoleRequest> roles = roleRepository.findAll().stream()
                .map(role -> modelMapper.map(role, RoleRequest.class))
                .toList();

        return Response.<List<RoleRequest>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(roles.isEmpty() ? "No Roles Found" : "Roles Retreived Successfully")
                .data(roles)
                .build();
    }
}
