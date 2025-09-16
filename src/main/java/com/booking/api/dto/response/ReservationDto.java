package com.booking.api.dto.response;

import com.booking.api.entity.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Long id;
    private Long userId;
    private String username;
    private Long vehicleId;
    private String vehicleName;
    private ReservationStatus status;
    private BigDecimal price;
    private Instant startTime;
    private Instant endTime;
    private Instant createdAt;
    private Instant updatedAt;
}