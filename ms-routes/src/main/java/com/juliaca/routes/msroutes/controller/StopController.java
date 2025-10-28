package com.juliaca.routes.msroutes.controller;

import com.juliaca.routes.msroutes.dto.StopRequest;
import com.juliaca.routes.msroutes.model.Route;
import com.juliaca.routes.msroutes.model.Stop;
import com.juliaca.routes.msroutes.service.RouteService;
import com.juliaca.routes.msroutes.service.StopService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StopController {

    private final StopService stopService;
    private final RouteService routeService;

    public StopController(StopService stopService, RouteService routeService) {
        this.stopService = stopService;
        this.routeService = routeService;
    }

    @GetMapping("/stops")
    public List<Stop> getAllStops() {
        return stopService.findAll();
    }

    @PostMapping("/stops")
    public ResponseEntity<Stop> createStop(@RequestBody StopRequest request) {

        Route route = routeService.findById(request.getRouteId());
        if (route == null) {
            return ResponseEntity.badRequest().build();
        }

        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(request.getLng(), request.getLat()));

        Stop stop = new Stop();
        stop.setName(request.getName());
        stop.setRoute(route);
        stop.setLocation(point);

        Stop saved = stopService.save(stop);
        return ResponseEntity.ok(saved);
    }
}
