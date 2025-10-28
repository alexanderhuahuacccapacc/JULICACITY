package com.juliaca.routes.msvehicles.simulator;
import com.juliaca.routes.msvehicles.client.AlertClient;
import com.juliaca.routes.msvehicles.client.AlertRequest;
import com.juliaca.routes.msvehicles.client.RoutesClient;
import com.juliaca.routes.msvehicles.dto.LocationDTO;
import com.juliaca.routes.msvehicles.dto.RouteDTO;
import com.juliaca.routes.msvehicles.model.Location;
import com.juliaca.routes.msvehicles.model.Vehicle;
import com.juliaca.routes.msvehicles.repository.VehicleRepository;
import com.juliaca.routes.msvehicles.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class VehicleSimulator {

    private final VehicleRepository repo;
    private final VehicleService service;
    private final AlertClient alertClient;
    private final RoutesClient routesClient;
    private final int intervalSeconds;

    public VehicleSimulator(VehicleRepository repo,
                            VehicleService service,
                            AlertClient alertClient,
                            RoutesClient routesClient,
                            @Value("${vehicles.simulator.interval-seconds:5}") int intervalSeconds) {
        this.repo = repo;
        this.service = service;
        this.alertClient = alertClient;
        this.routesClient = routesClient;
        this.intervalSeconds = intervalSeconds;
    }

    @Scheduled(fixedDelayString = "${vehicles.simulator.interval-seconds:5}000")
    public void tick() {
        List<Vehicle> vehicles = repo.findAll();
        for (Vehicle v : vehicles) {
            if (v.getPath() == null || v.getPath().isEmpty()) continue;

            int idx = (v.getPathIndex() == null ? 0 : v.getPathIndex()) % v.getPath().size();
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
                    log.error("Error al enviar alerta NO_SIGNAL para vehículo {}: {}", v.getPlate(), e.getMessage(), e);
                }
            }

            if (v.getRouteId() != null && !v.getRouteId().isBlank()) {
                try {
                    RouteDTO route = routesClient.getRoute(v.getRouteId());
                    if (route != null) {
                        List<LocationDTO> path = convertWktToPath(route);
                        route.setPath(path); // rellena path desde WKT
                        if (!path.isEmpty() && isOutOfRoute(v, route)) {
                            AlertRequest alert = AlertRequest.builder()
                                    .vehicleId(v.getPlate())
                                    .type("OUT_OF_ROUTE")
                                    .message("El vehículo " + v.getPlate() + " se salió de la ruta " + route.getName())
                                    .severity("WARN")
                                    .active(true)
                                    .build();
                            alertClient.createAlert(alert);
                            log.info("Alerta OUT_OF_ROUTE enviada para vehículo {}", v.getPlate());
                        }
                    } else {
                        log.warn("Ruta {} no encontrada", v.getRouteId());
                    }
                } catch (Exception e) {
                    log.error("Error consultando ruta o enviando alerta OUT_OF_ROUTE para vehículo {}: {}",
                            v.getPlate(), e.getMessage(), e);
                }
            } else {
                log.warn("Vehículo {} no tiene routeId asignado", v.getPlate());
            }
        }
    }

    private List<LocationDTO> convertWktToPath(RouteDTO route) {
        List<LocationDTO> path = new ArrayList<>();
        try {
            if (route.getPath() != null && !route.getPath().isEmpty()) {
                return route.getPath();
            }

            String wkt = route.getWktPath();
            if (wkt == null || wkt.isBlank()) return path;

            WKTReader reader = new WKTReader();
            org.locationtech.jts.geom.Geometry geom = reader.read(wkt);
            for (Coordinate c : geom.getCoordinates()) {
                path.add(new LocationDTO(c.y, c.x));
            }
        } catch (Exception e) {
            log.error("Error convirtiendo WKT a path para ruta {}: {}", route.getId(), e.getMessage(), e);
        }
        return path;
    }

    private boolean isOutOfRoute(Vehicle v, RouteDTO route) {
        double maxDistanceMeters = 100; // umbral de distancia
        for (LocationDTO point : route.getPath()) {
            if (haversine(v.getCurrent(), point) <= maxDistanceMeters) {
                return false;
            }
        }
        return true;
    }

    private double haversine(Location vehicleLoc, LocationDTO routePoint) {
        final int R = 6371000; // radio Tierra en metros
        double lat1 = Math.toRadians(vehicleLoc.getLat());
        double lat2 = Math.toRadians(routePoint.getLat());
        double dLat = lat2 - lat1;
        double dLon = Math.toRadians(routePoint.getLng() - vehicleLoc.getLng());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return 2 * R * Math.asin(Math.sqrt(a));
    }
}