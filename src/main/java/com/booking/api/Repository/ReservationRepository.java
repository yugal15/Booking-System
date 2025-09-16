package com.booking.api.Repository;

import com.booking.api.entity.Reservation;
import com.booking.api.entity.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // For USERS: find by user
    Page<Reservation> findByUser_Id(Long userId, Pageable pageable);


    // Filter by status & price range (admin/all)
    @Query("SELECT r FROM Reservation r WHERE (:status IS NULL OR r.status = :status) AND (:minPrice IS NULL OR r.price >= :minPrice) AND (:maxPrice IS NULL OR r.price <= :maxPrice)")
    Page<Reservation> filter(@Param("status") ReservationStatus status,
                             @Param("minPrice") BigDecimal minPrice,
                             @Param("maxPrice") BigDecimal maxPrice,
                             Pageable pageable);
}