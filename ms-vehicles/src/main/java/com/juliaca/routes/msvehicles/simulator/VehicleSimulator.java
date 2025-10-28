package com.juliaca.routes.msvehicles.simulator;

import com.juliaca.routes.msvehicles.client.AlertClient;
import com.juliaca.routes.msvehicles.client.AlertRequest;
import com.juliaca.routes.msvehicles.model.Location;
import com.juliaca.routes.msvehicles.model.Vehicle;
import com.juliaca.routes.msvehicles.repository.VehicleRepository;
import com.juliaca.routes.msvehicles.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
public class VehicleSimulator {

    private final VehicleRepository repo;
    private final VehicleService service;
    private final AlertClient alertClient;
    private final int intervalSeconds;

    public VehicleSimulator(VehicleRepository repo,
                            VehicleService service,
                            AlertClient alertClient,
                            @Value("${vehicles.simulator.interval-seconds:5}") int intervalSeconds) {
        this.repo = repo;
        this.service = service;
        this.alertClient = alertClient;
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
            v.setPathIndex(idx + 1);
            repo.save(v);

            service.publish(v);

            if (Math.random() < 0.05) {
                try {
                    AlertRequest alert = AlertRequest.builder()
                            .vehicleId(v.getPlate())
                            .type("NO_SIGNAL")
                            .message("El vehículo " + v.getPlate() + " ha perdido señal temporalmente")
                            .severity("WARN")
                            .active(true)
                            .build();

                    alertClient.createAlert(alert);
                    log.info("Enviada alerta NO_SIGNAL para vehículo {}", v.getPlate());
                } catch (Exception e) {
                    log.error("Error al enviar alerta a ms-alerts para vehículo {}: {}",
                            v.getPlate(), e.getMessage(), e);
                }
            }
        }
    }
}