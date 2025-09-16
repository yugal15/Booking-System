package com.booking.api.service.impl;


import com.booking.api.Repository.VehicleRepository;
import com.booking.api.entity.Vehicle;
import com.booking.api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(Long id,Vehicle vehicle) {
        Vehicle v = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id : "+id));
        v.setName(vehicle.getName());
        v.setType(vehicle.getType());
        v.setDescription(vehicle.getDescription());
        v.setCapacity(vehicle.getCapacity());

        if (vehicle.getActive() != null) {
            v.setActive(vehicle.getActive());
        }
        return vehicleRepository.save(v);
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id : "+id));
    }

    @Override
    public Page<Vehicle> getAllVehicles(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }
}

