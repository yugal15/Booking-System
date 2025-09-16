package com.booking.api.service;

import com.booking.api.entity.Reservation;
import com.booking.api.entity.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Optional;

public interface ReservationService {
    Page<Reservation> getAllReservations(Authentication authentication,
                                         ReservationStatus status,
                                         BigDecimal minPrice,
                                         BigDecimal maxPrice,
                                         int page, int size,
                                         String sortBy, String sortDir);

    Reservation getReservationById(Long id, Authentication authentication);

    Reservation createReservation(Reservation reservation, Authentication authentication);

    Reservation updateReservation(Long id, Reservation reservation, Authentication authentication);

    void deleteReservation(Long id, Authentication authentication);

}
