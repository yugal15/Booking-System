package com.booking.api.service;

import com.booking.api.entity.Reservation;
import com.booking.api.entity.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.Optional;

public interface ReservationService {
    Reservation createReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    void deleteReservation(Long id);
    Optional<Reservation> getReservationById(Long id);

    // Filtering & pagination
    Page<Reservation> getReservations(ReservationStatus status, BigDecimal minPrice,
                                      BigDecimal maxPrice, Pageable pageable, Long userId);
}
