package com.juliaca.routes.msvehicles.repository;

import com.juliaca.routes.msvehicles.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    List<Vehicle> findByLine(String line);
}