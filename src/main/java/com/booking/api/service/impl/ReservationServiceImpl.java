package com.booking.api.service.impl;

import com.booking.api.Repository.ReservationRepository;
import com.booking.api.entity.Reservation;
import com.booking.api.entity.enums.ReservationStatus;
import com.booking.api.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Page<Reservation> getReservations(ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable, Long userId) {
//        // If userId is provided, filter by user
//        if (userId != null) {
//            return reservationRepository.findByStatusAndPriceBetweenAndUserId(status, minPrice, maxPrice, userId, pageable);
//        } else {
//            return reservationRepository.findByStatusAndPriceBetween(
//                    status, minPrice, maxPrice, pageable);
//        }
        return null;
    }
}
