package com.booking.api.service.impl;

import com.booking.api.Repository.ReservationRepository;
import com.booking.api.Repository.UserRepository;
import com.booking.api.Repository.VehicleRepository;
import com.booking.api.dto.request.ReservationRequestDto;
import com.booking.api.dto.response.ReservationResponseDto;
import com.booking.api.entity.Reservation;
import com.booking.api.entity.User;
import com.booking.api.entity.Vehicle;
import com.booking.api.entity.enums.ReservationStatus;
import com.booking.api.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    public ReservationServiceImpl(UserRepository userRepository, VehicleRepository vehicleRepository, ReservationRepository reservationRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReservationResponseDto createReservation(ReservationRequestDto dto, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username : " + userDetails.getUsername()));

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with ID : " + dto.getVehicleId()));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setVehicle(vehicle);
        if (dto.getPrice() != null) {
            reservation.setPrice(dto.getPrice());
        }
        reservation.setCreatedAt(Instant.now());
        reservation.setUpdatedAt(Instant.now());
        if (dto.getStartTime() != null) {
            reservation.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            reservation.setEndTime(dto.getEndTime());
        }
        reservationRepository.save(reservation);

        return modelMapper.map(reservation, ReservationResponseDto.class);
    }

    @Override
    public ReservationResponseDto updateReservation(Long id, ReservationRequestDto updatedReservation, Authentication authentication) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID : " + id));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username : " + userDetails.getUsername()));

        if (!user.getRole().name().equals("ADMIN") && !reservation.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Access denied  : User doesn't own the reservation");
            //here we are throwing error because user is neither ADMIN and Not own the reservation   ---- author Yugal Wani
        }

        if (updatedReservation.getVehicleId() != null) {
            Vehicle vehicle = vehicleRepository.findById(updatedReservation.getVehicleId())
                    .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id : " + id));
            reservation.setVehicle(vehicle);
        }

        if (updatedReservation.getStatus() != null)
            reservation.setStatus(updatedReservation.getStatus());

        if (updatedReservation.getPrice() != null)
            reservation.setPrice(updatedReservation.getPrice());

        if (updatedReservation.getStartTime() != null)
            reservation.setStartTime(updatedReservation.getStartTime());

        if (updatedReservation.getEndTime() != null)
            reservation.setEndTime(updatedReservation.getEndTime());

        reservation.setUpdatedAt(Instant.now());
        reservationRepository.save(reservation);

        return modelMapper.map(reservation, ReservationResponseDto.class);
    }

    @Override
    public void deleteReservation(Long id, Authentication authentication) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID : " + id));
        ;

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username : " + userDetails.getUsername()));

        if (!user.getRole().name().equals("ADMIN") && !reservation.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Access denied  : User doesn't own the reservation");
            //here we are throwing error because user is neither ADMIN and Not own the reservation   ---- author Yugal Wani
        }

        reservationRepository.delete(reservation);
    }

    @Override
    public Page<ReservationResponseDto> getAllReservations(Authentication authentication, ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice, int page, int size, String sortBy, String sortDir) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username : " + userDetails.getUsername()));

        boolean isAdmin = user.getRole().name().equals("ADMIN");

        if (isAdmin) {
            if (status != null && minPrice != null && maxPrice != null) {
                Page<Reservation> reservationPages = reservationRepository.findByStatusAndPriceBetween(status, minPrice, maxPrice, pageable);
                Page<ReservationResponseDto> dtoPage = reservationPages.map(reservation ->
                        modelMapper.map(reservation, ReservationResponseDto.class)
                );
                return dtoPage;
            } else if (status != null) {
                Page<Reservation> reservationPages = reservationRepository.findByStatus(status, pageable);
                Page<ReservationResponseDto> dtoPage = reservationPages.map(reservation ->
                        modelMapper.map(reservation, ReservationResponseDto.class)
                );
                return dtoPage;
            } else {
                Page<Reservation> reservationPages = reservationRepository.findAll(pageable);
                Page<ReservationResponseDto> dtoPage = reservationPages.map(reservation ->
                        modelMapper.map(reservation, ReservationResponseDto.class)
                );
                return dtoPage;
            }
        } else {
            if (status != null && minPrice != null && maxPrice != null) {
                Page<Reservation> reservationPages = reservationRepository.findByUserAndStatusAndPriceBetween(user, status, minPrice, maxPrice, pageable);
                Page<ReservationResponseDto> dtoPage = reservationPages.map(reservation ->
                        modelMapper.map(reservation, ReservationResponseDto.class)
                );
                return dtoPage;
            } else {
                Page<Reservation> reservationPages = reservationRepository.findByUser(user, pageable);
                Page<ReservationResponseDto> dtoPage = reservationPages.map(reservation ->
                        modelMapper.map(reservation, ReservationResponseDto.class)
                );
                return dtoPage;
            }
        }
    }

    @Override
    public ReservationResponseDto getReservationById(Long id, Authentication authentication) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID : " + id));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username : " + userDetails.getUsername()));

        if (!user.getRole().name().equals("ADMIN") && !reservation.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Access denied  : User doesn't own the reservation");
            //here we are throwing error because user is neither ADMIN and Not own the reservation   ---- author Yugal Wani
        }

        return modelMapper.map(reservation, ReservationResponseDto.class);
    }
}
