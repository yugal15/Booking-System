package com.booking.api.Repository;

import com.booking.api.entity.Reservation;
import com.booking.api.entity.User;
import com.booking.api.entity.Vehicle;
import com.booking.api.entity.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByUser_Id(Long userId, Pageable pageable);

    Page<Reservation> findByUser(User user, Pageable pageable);

    Page<Reservation> findByStatusAndPriceBetween(ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Reservation> findByUserAndStatusAndPriceBetween(User user, ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}