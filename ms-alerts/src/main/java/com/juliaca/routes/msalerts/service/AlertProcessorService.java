package com.juliaca.routes.msalerts.service;

import com.juliaca.routes.msalerts.model.Alert;
import com.juliaca.routes.msalerts.model.VehicleDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AlertProcessorService {

    private final AlertService alertService;
    private final Map<String, Long> lastSeen = new ConcurrentHashMap<>();

    private final long noSignalThresholdMillis;

    public AlertProcessorService(AlertService alertService,
                                 @Value("${alerts.nosignal.threshold-millis:30000}") long noSignalThresholdMillis) {
        this.alertService = alertService;
        this.noSignalThresholdMillis = noSignalThresholdMillis;
    }

    public void processVehicleUpdate(VehicleDTO v) {
        String vehicleId = v.getId() != null ? v.getId() : v.getPlate();
        long now = v.getLastUpdated() != null ? v.getLastUpdated() : Instant.now().toEpochMilli();
        lastSeen.put(vehicleId, now);

        if ("CANCELLED".equalsIgnoreCase(v.getStatus())) {
            Alert alert = Alert.builder()
                    .vehicleId(vehicleId)
                    .type("CANCELLED")
                    .message("Unidad cancelada: " + vehicleId)
                    .severity("CRITICAL")
                    .active(true)
                    .build();
            alertService.save(alert);
            return;
        }

        alertService.findByVehicle(vehicleId).stream()
                .filter(a -> "NO_SIGNAL".equalsIgnoreCase(a.getType()) && a.isActive())
                .forEach(a -> alertService.deactivate(a.getId()));

        if ("OFFLINE".equalsIgnoreCase(v.getStatus())) {
            Alert alert = Alert.builder()
                    .vehicleId(vehicleId)
                    .type("NO_SIGNAL")
                    .message("Unidad sin señal (status OFFLINE): " + vehicleId)
                    .severity("WARN")
                    .active(true)
                    .build();
            alertService.save(alert);
        }
    }

    @Scheduled(fixedDelayString = "${alerts.nosignal.threshold-millis:30000}")
    public void checkNoSignal() {
        long now = Instant.now().toEpochMilli();
        lastSeen.forEach((vehicleId, lastTs) -> {
            if (now - lastTs > noSignalThresholdMillis) {
                boolean already = alertService.findByVehicle(vehicleId).stream()
                        .anyMatch(a -> "NO_SIGNAL".equalsIgnoreCase(a.getType()) && a.isActive());
                if (!already) {
                    Alert alert = Alert.builder()
                            .vehicleId(vehicleId)
                            .type("NO_SIGNAL")
                            .message("No se recibe señal del vehículo desde hace " + (now - lastTs)/1000 + "s")
                            .severity("WARN")
                            .active(true)
                            .build();
                    alertService.save(alert);
                    System.out.println("Generada alerta NO_SIGNAL para: " + vehicleId);
                }
            }
        });
    }
}