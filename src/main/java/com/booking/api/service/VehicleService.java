package com.booking.api.service;

import com.booking.api.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VehicleService {
    Vehicle createVehicle(Vehicle vehicle);
    Vehicle updateVehicle(Long Id,Vehicle vehicle);
    void deleteVehicle(Long id);
    Vehicle getVehicleById(Long id);
    Page<Vehicle> getAllVehicles(Pageable pageable);
}
