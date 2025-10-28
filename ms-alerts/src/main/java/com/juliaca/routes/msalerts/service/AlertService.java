package com.juliaca.routes.msalerts.service;

import com.juliaca.routes.msalerts.model.Alert;
import com.juliaca.routes.msalerts.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository repo;

    public AlertService(AlertRepository repo) {
        this.repo = repo;
    }

    public List<Alert> findAll() {
        return repo.findAll();
    }

    public List<Alert> findActive() {
        return repo.findByActiveTrue();
    }

    public List<Alert> findByVehicle(String vehicleId) {
        return repo.findByVehicleId(vehicleId);
    }

    public Alert save(Alert alert) {
        alert.setTimestamp(Instant.now().toEpochMilli());
        return repo.save(alert);
    }

    public void deactivate(String id) {
        repo.findById(id).ifPresent(alert -> {
            alert.setActive(false);
            repo.save(alert);
        });
    }
}
