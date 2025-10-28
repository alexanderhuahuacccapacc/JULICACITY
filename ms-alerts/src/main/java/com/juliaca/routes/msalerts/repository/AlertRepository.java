package com.juliaca.routes.msalerts.repository;

import com.juliaca.routes.msalerts.model.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlertRepository extends MongoRepository<Alert, String> {
    List<Alert> findByActiveTrue();
    List<Alert> findByVehicleId(String vehicleId);
}