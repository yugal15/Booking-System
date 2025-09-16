package com.booking.api.service.impl;

import com.booking.api.Repository.ReservationRepository;
import com.booking.api.Repository.UserRepository;
import com.booking.api.Repository.VehicleRepository;
import com.booking.api.entity.Reservation;
import com.booking.api.entity.User;
import com.booking.api.entity.Vehicle;
import com.booking.api.entity.enums.ReservationStatus;
import com.booking.api.service.ReservationService;
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

    public ReservationServiceImpl(UserRepository userRepository, VehicleRepository vehicleRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation createReservation(Reservation reservation,Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Vehicle vehicle = vehicleRepository.findById(reservation.getVehicle().getId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        reservation.setUser(user);
        reservation.setVehicle(vehicle);
        reservation.setCreatedAt(Instant.now());
        reservation.setUpdatedAt(Instant.now());

        return reservationRepository.save(reservation);    }

    @Override
    public Reservation updateReservation(Long id,Reservation updatedReservation,Authentication authentication) {
        Reservation reservation = getReservationById(id, authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getRole().name().equals("ADMIN") && !reservation.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Access denied");
            //here we are throwing error because user is neither ADMIN and Not own the reservation   ---- author Yugal Wani
        }

        if (updatedReservation.getVehicle() != null) {
            Vehicle vehicle = vehicleRepository.findById(updatedReservation.getVehicle().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
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
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long id, Authentication authentication) {
        Reservation reservation = getReservationById(id, authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getRole().name().equals("ADMIN") && !reservation.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Access denied");
            //here we are throwing error because user is neither ADMIN and Not own the reservation   ---- author Yugal Wani
        }

        reservationRepository.delete(reservation);
    }

    @Override
    public Page<Reservation> getAllReservations(Authentication authentication, ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice, int page, int size, String sortBy, String sortDir) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean isAdmin = user.getRole().name().equals("ADMIN");

        if (isAdmin) {
            if (status != null && minPrice != null && maxPrice != null) {
                return reservationRepository.findByStatusAndPriceBetween(status, minPrice, maxPrice, pageable);
            } else {
                return reservationRepository.findAll(pageable);
            }
        } else {
            if (status != null && minPrice != null && maxPrice != null) {
                return reservationRepository.findByUserAndStatusAndPriceBetween(user, status, minPrice, maxPrice, pageable);
            } else {
                return reservationRepository.findByUser(user, pageable);
            }
        }    }

    @Override
    public Reservation getReservationById(Long id,Authentication authentication) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getRole().name().equals("ADMIN") && !reservation.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Access denied");
            //here we are throwing error because user is neither ADMIN and Not own the reservation   ---- author Yugal Wani
        }

        return reservation;
    }
}
