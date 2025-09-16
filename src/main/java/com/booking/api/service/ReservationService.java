package com.booking.api.service;

import com.booking.api.dto.request.ReservationRequestDto;
import com.booking.api.dto.response.ReservationResponseDto;
import com.booking.api.entity.Reservation;
import com.booking.api.entity.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Optional;

public interface ReservationService {
    Page<ReservationResponseDto> getAllReservations(Authentication authentication,
                                                        ReservationStatus status,
                                                        BigDecimal minPrice,
                                                        BigDecimal maxPrice,
                                                        int page, int size,
                                                        String sortBy, String sortDir);

    ReservationResponseDto getReservationById(Long id, Authentication authentication);

    ReservationResponseDto createReservation(ReservationRequestDto reservation, Authentication authentication);

    ReservationResponseDto updateReservation(Long id, ReservationRequestDto reservation, Authentication authentication);

    void deleteReservation(Long id, Authentication authentication);

}
