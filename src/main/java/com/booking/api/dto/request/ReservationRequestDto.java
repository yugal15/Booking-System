package com.booking.api.dto.request;

import com.booking.api.entity.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto {
    private Long vehicleId;
    private BigDecimal price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ReservationStatus status;
}