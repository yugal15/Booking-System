package com.booking.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private Long vehicleId;
    private BigDecimal price;
    private Instant startTime;
    private Instant endTime;
}