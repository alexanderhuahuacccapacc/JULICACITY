package com.juliaca.routes.msvehicles.simulator;

import com.juliaca.routes.msvehicles.model.Location;
import com.juliaca.routes.msvehicles.model.Vehicle;
import com.juliaca.routes.msvehicles.repository.VehicleRepository;
import com.juliaca.routes.msvehicles.service.VehicleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class VehicleSimulator {

    private final VehicleRepository repo;
    private final VehicleService service;
    private final int intervalSeconds;

    public VehicleSimulator(VehicleRepository repo,
                            VehicleService service,
                            @Value("${vehicles.simulator.interval-seconds:5}") int intervalSeconds) {
        this.repo = repo;
        this.service = service;
        this.intervalSeconds = intervalSeconds;
    }

    @Scheduled(fixedDelayString = "${vehicles.simulator.interval-seconds:5}000")
    public void tick() {
        List<Vehicle> vehicles = repo.findAll();
        for (Vehicle v : vehicles) {
            if (v.getPath() == null || v.getPath().isEmpty()) continue;
            Integer idx = v.getPathIndex() == null ? 0 : v.getPathIndex();
            idx = idx % v.getPath().size();
            Location next = v.getPath().get(idx);

            next.setTimestamp(Instant.now().toEpochMilli());
            v.setCurrent(next);
            v.setLastUpdated(Instant.now().toEpochMilli());
            v.setPathIndex(idx + 1); // advance
            repo.save(v);

            service.publish(v);
        }
    }
}