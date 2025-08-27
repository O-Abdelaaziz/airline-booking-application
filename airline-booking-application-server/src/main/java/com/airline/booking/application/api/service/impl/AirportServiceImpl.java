package com.airline.booking.application.api.service.impl;

import com.airline.booking.application.api.entity.Airport;
import com.airline.booking.application.api.entity.enums.City;
import com.airline.booking.application.api.entity.enums.Country;
import com.airline.booking.application.api.exception.BadRequestException;
import com.airline.booking.application.api.exception.ResourceNotFoundException;
import com.airline.booking.application.api.payload.request.AirportRequest;
import com.airline.booking.application.api.payload.response.Response;
import com.airline.booking.application.api.repository.IAirportRepository;
import com.airline.booking.application.api.service.IAirportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Created 26/08/2025 - 21:20
 * @Package com.airline.booking.application.api.service.impl
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AirportServiceImpl implements IAirportService {

    private final IAirportRepository airportRepo;
    private final ModelMapper modelMapper;


    @Override
    public Response<?> createAirport(AirportRequest airportDTO) {
        log.info("Inside createAirport()");

        Country country = airportDTO.getCountry();
        City city = airportDTO.getCity();

        if (!city.getCountry().equals(country)){
            throw new BadRequestException("CITY does not belong to the country");
        }

        Airport airport = modelMapper.map(airportDTO, Airport.class);
        airportRepo.save(airport);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Airport Created Successfully")
                .build();


    }

    @Override
    public Response<?> updateAirport(AirportRequest airportDTO) {

        Long id = airportDTO.getId();

        Airport existingAirport = airportRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Airport Not Found"));


        if (airportDTO.getCity() != null){
            if (!airportDTO.getCity().getCountry().equals(existingAirport.getCountry())){
                throw new BadRequestException("CITY does not belong to the country");
            }
            existingAirport.setCity(airportDTO.getCity());
        }


        if (airportDTO.getName() != null){
            existingAirport.setName(airportDTO.getName());
        }

        if (airportDTO.getIataCode() != null){
            existingAirport.setIataCode(airportDTO.getIataCode());
        }

        airportRepo.save(existingAirport);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Airport updated Successfully")
                .build();


    }

    @Override
    public Response<List<AirportRequest>> getAllAirports() {

        List<AirportRequest> airports = airportRepo.findAll().stream()
                .map(airport -> modelMapper.map(airport, AirportRequest.class))
                .toList();

        return Response.<List<AirportRequest>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(airports.isEmpty() ? "No Airports Found": "Airports retrieved successfully")
                .data(airports)
                .build();
    }

    @Override
    public Response<AirportRequest> getAirportById(Long id) {

        Airport airport = airportRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Airport Not Found"));

        AirportRequest airportDTO = modelMapper.map(airport, AirportRequest.class);

        return Response.<AirportRequest>builder()
                .statusCode(HttpStatus.OK.value())
                .message( "Airport retrieved successfully")
                .data(airportDTO)
                .build();

    }
}
