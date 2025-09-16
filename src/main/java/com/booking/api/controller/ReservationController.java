package com.booking.api.controller;

import com.booking.api.dto.request.ReservationRequestDto;
import com.booking.api.dto.response.ReservationResponseDto;
import com.booking.api.entity.Reservation;
import com.booking.api.entity.enums.ReservationStatus;
import com.booking.api.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<Page<ReservationResponseDto>> getAll(
            Authentication authentication,
            @RequestParam(required = false) ReservationStatus status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(reservationService.getAllReservations(authentication, status, minPrice, maxPrice, page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(reservationService.getReservationById(id, authentication));
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@RequestBody ReservationRequestDto reservation, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(reservation, authentication));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> update(@PathVariable Long id, @RequestBody ReservationRequestDto reservation, Authentication authentication) {
        return ResponseEntity.ok(reservationService.updateReservation(id, reservation, authentication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        reservationService.deleteReservation(id, authentication);
        return ResponseEntity.noContent().build();
    }
}

