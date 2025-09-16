package com.booking.api.dto.response;

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
public class ReservationResponseDto {
    private Long id;
    private Long userId;
    private Long vehicleId;
    private String vehicleName;
    private ReservationStatus status;
    private BigDecimal price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Instant createdAt;
    private Instant updatedAt;
}