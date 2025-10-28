package com.juliaca.routes.msvehicles.service;

import com.juliaca.routes.msvehicles.model.Location;
import com.juliaca.routes.msvehicles.model.Vehicle;
import com.juliaca.routes.msvehicles.repository.VehicleRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository repo;
    private final SimpMessagingTemplate messaging;

    public VehicleService(VehicleRepository repo, SimpMessagingTemplate messaging) {
        this.repo = repo;
        this.messaging = messaging;
    }

    public List<Vehicle> findAll() {
        return repo.findAll();
    }

    public Optional<Vehicle> findById(String id) {
        return repo.findById(id);
    }

    public Vehicle save(Vehicle v) {
        v.setLastUpdated(Instant.now().toEpochMilli());
        if (v.getPathIndex() == null) v.setPathIndex(0);
        if (v.getCurrent() == null && v.getPath() != null && !v.getPath().isEmpty()) {
            v.setCurrent(v.getPath().get(0));
        }
        return repo.save(v);
    }

    public void updateLocation(String vehicleId, Location loc) {
        Optional<Vehicle> opt = repo.findById(vehicleId);
        if (opt.isPresent()) {
            Vehicle v = opt.get();
            v.setCurrent(loc);
            v.setLastUpdated(Instant.now().toEpochMilli());
            repo.save(v);
            // publish to websocket topic
            messaging.convertAndSend("/topic/vehicles", v);
        }
    }

    public void publish(Vehicle v) {
        messaging.convertAndSend("/topic/vehicles", v);
    }
}
