package com.airline.booking.application.api.service.impl;

import com.airline.booking.application.api.entity.Airport;
import com.airline.booking.application.api.entity.Flight;
import com.airline.booking.application.api.entity.User;
import com.airline.booking.application.api.entity.enums.City;
import com.airline.booking.application.api.entity.enums.Country;
import com.airline.booking.application.api.entity.enums.FlightStatus;
import com.airline.booking.application.api.exception.BadRequestException;
import com.airline.booking.application.api.exception.ResourceNotFoundException;
import com.airline.booking.application.api.payload.request.FlightRequest;
import com.airline.booking.application.api.payload.request.FlightSubmitRequest;
import com.airline.booking.application.api.payload.response.Response;
import com.airline.booking.application.api.repository.IAirportRepository;
import com.airline.booking.application.api.repository.IFlightRepository;
import com.airline.booking.application.api.repository.IUserRepository;
import com.airline.booking.application.api.service.IFlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Created 27/08/2025 - 11:03
 * @Package com.airline.booking.application.api.service.impl
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class FlightServiceImpl implements IFlightService {

    private final IFlightRepository iFlightRepository;
    private final IAirportRepository iAirportRepository;
    private final IUserRepository iUserRepository;
    private final ModelMapper modelMapper;


    @Override
    public Response<?> createFlight(FlightSubmitRequest createFlightRequest) {

        if (createFlightRequest.getArrivalTime().isBefore(createFlightRequest.getDepartureTime())) {
            throw new BadRequestException("Arrival Time cannot be before the departure time");
        }

        if (iFlightRepository.existsByFlightNumber(createFlightRequest.getFlightNumber())) {
            throw new BadRequestException("Flight with this number already exists");
        }

        //fetch and validate the departure airport
        Airport departureAirport = iAirportRepository.findByIataCode(createFlightRequest.getDepartureAirportIataCode())
                .orElseThrow(() -> new ResourceNotFoundException("Departure Airport Not Found"));

        //fetch and validate the departure airport
        Airport arrivalAirport = iAirportRepository.findByIataCode(createFlightRequest.getArrivalAirportIataCode())
                .orElseThrow(() -> new ResourceNotFoundException("Arrival Airport Not Found"));


        Flight flightToSave = new Flight();
        flightToSave.setFlightNumber(createFlightRequest.getFlightNumber());
        flightToSave.setDepartureAirport(departureAirport);
        flightToSave.setArrivalAirport(arrivalAirport);
        flightToSave.setDepartureTime(createFlightRequest.getDepartureTime());
        flightToSave.setArrivalTime(createFlightRequest.getArrivalTime());
        flightToSave.setBasePrice(createFlightRequest.getBasePrice());
        flightToSave.setStatus(FlightStatus.SCHEDULED);

        //assign pilot to the flight(get and validate the pilot)
        if (createFlightRequest.getPilotId() != null){

            User pilot = iUserRepository.findById(createFlightRequest.getPilotId())
                    .orElseThrow(()-> new ResourceNotFoundException("Pilot is not found"));

            boolean isPilot = pilot.getRoles().stream()
                    .anyMatch(role -> role.getName().equalsIgnoreCase("PILOT"));

            if (!isPilot){
                throw new BadRequestException("Claimed User-Pilot not a certified pilot");
            }
            flightToSave.setAssignedPilot(pilot);
        }

        //save the flight
        iFlightRepository.save(flightToSave);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("FLight saved successfully")
                .build();

    }

    @Override
    public Response<FlightRequest> getFlightById(Long id) {

        Flight flight = iFlightRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("FLight Not Found"));

        FlightRequest flightDTO = modelMapper.map(flight, FlightRequest.class);

        if (flightDTO.getBookings() != null){
            flightDTO.getBookings().forEach(bookingDTO -> bookingDTO.setFlight(null));
        }

        return Response.<FlightRequest>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Flight retrieved Successfully")
                .data(flightDTO)
                .build();

    }

    @Override
    public Response<List<FlightRequest>> getAllFlights() {

        Sort sortByIdDesc = Sort.by(Sort.Direction.DESC, "id");

        List<FlightRequest> flights = iFlightRepository.findAll(sortByIdDesc).stream()
                .map(flight -> {
                    FlightRequest flightDTO = modelMapper.map(flight, FlightRequest.class);
                    if (flightDTO.getBookings() != null){
                        flightDTO.getBookings().forEach(bookingDTO -> bookingDTO.setFlight(null));
                    }
                    return flightDTO;
                }).toList();

        return Response.<List<FlightRequest>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(flights.isEmpty() ? "No Flights Found" : "Flights retrieved successfully")
                .data(flights)
                .build();
    }


    @Override
    public Response<?> updateFlight(FlightSubmitRequest flightRequest) {
        Long id = flightRequest.getId();
        Flight existingFlight = iFlightRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Flight Not Found"));

        if (flightRequest.getDepartureTime() != null){
            existingFlight.setDepartureTime(flightRequest.getDepartureTime());
        }

        if (flightRequest.getArrivalTime() != null){
            existingFlight.setArrivalTime(flightRequest.getArrivalTime());
        }

        if (flightRequest.getBasePrice() != null){
            existingFlight.setBasePrice(flightRequest.getBasePrice());
        }

        if (flightRequest.getStatus() != null){
            existingFlight.setStatus(flightRequest.getStatus());
        }

        //if pilot id is passed in validate the pilot and update it

        if (flightRequest.getPilotId() != null){

            User pilot = iUserRepository.findById(flightRequest.getPilotId())
                    .orElseThrow(()-> new ResourceNotFoundException("Pilot is not found"));

            boolean isPilot = pilot.getRoles().stream()
                    .anyMatch(role -> role.getName().equalsIgnoreCase("PILOT"));

            if (!isPilot){
                throw new BadRequestException("Claimed User-Pilot not a certified pilot");
            }
            existingFlight.setAssignedPilot(pilot);
        }

        iFlightRepository.save(existingFlight);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("FLight Updated Successfully")
                .build();

    }

    @Override
    public Response<List<FlightRequest>> searchFlight(String departurePortIata, String arrivalPortIata, FlightStatus status, LocalDate departureDate) {

        // Define the start and end of the day for the given departureDate
        LocalDateTime startOfDay = departureDate.atStartOfDay();
        LocalDateTime endOfDay = departureDate.plusDays(1).atStartOfDay().minusNanos(1); // End of day (23:59:59.999...)

        List<Flight> flights = iFlightRepository.findByDepartureAirportIataCodeAndArrivalAirportIataCodeAndStatusAndDepartureTimeBetween(
                departurePortIata, arrivalPortIata, status, startOfDay, endOfDay
        );

        List<FlightRequest> flightDTOS = flights.stream()
                .map(flight -> {
                    FlightRequest flightDTO = modelMapper.map(flight, FlightRequest.class);
                    flightDTO.setAssignedPilot(null);
                    flightDTO.setBookings(null);
                    return flightDTO;
                }).toList();


        return Response.<List<FlightRequest>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(flightDTOS.isEmpty() ? "No FLights Found": "FLight retreived sucessfully")
                .data(flightDTOS)
                .build();
    }

    @Override
    public Response<List<City>> getAllCities() {
        return Response.<List<City>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success")
                .data(List.of(City.values()))
                .build();
    }

    @Override
    public Response<List<Country>> getAllCountries() {
        return Response.<List<Country>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success")
                .data(List.of(Country.values()))
                .build();
    }
}
