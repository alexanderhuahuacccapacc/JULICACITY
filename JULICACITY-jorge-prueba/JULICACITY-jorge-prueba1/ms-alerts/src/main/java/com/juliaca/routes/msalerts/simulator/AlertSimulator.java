package com.juliaca.routes.msalerts.simulator;

import com.juliaca.routes.msalerts.model.Alert;
import com.juliaca.routes.msalerts.service.AlertService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class AlertSimulator {

    private final AlertService service;
    private final boolean enabled;
    private final int interval;
    private final Random random = new Random();

    private final List<String> types = List.of("DELAY", "NO_SIGNAL", "OUT_OF_ROUTE", "CANCELLED");
    private final List<String> severities = List.of("INFO", "WARN", "CRITICAL");

    public AlertSimulator(
            AlertService service,
            @Value("${alerts.simulator.enabled:true}") boolean enabled,
            @Value("${alerts.simulator.interval-seconds:10}") int interval) {
        this.service = service;
        this.enabled = enabled;
        this.interval = interval;
    }

    @Scheduled(fixedDelayString = "${alerts.simulator.interval-seconds:10}000")
    public void simulate() {
        if (!enabled) return;

        String type = types.get(random.nextInt(types.size()));
        String severity = severities.get(random.nextInt(severities.size()));

        Alert alert = Alert.builder()
                .vehicleId("U-" + (1000 + random.nextInt(20)))
                .type(type)
                .message("Simulación de alerta: " + type)
                .severity(severity)
                .active(true)
                .build();

        service.save(alert);
        System.out.println("Generada alerta simulada: " + alert.getType());
    }
}
