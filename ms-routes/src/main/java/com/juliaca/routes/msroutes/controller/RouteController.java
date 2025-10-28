package com.juliaca.routes.msroutes.controller;

import com.juliaca.routes.msroutes.dto.RouteDTO;
import com.juliaca.routes.msroutes.dto.RouteWKTRequest;
import com.juliaca.routes.msroutes.model.Line;
import com.juliaca.routes.msroutes.model.Route;
import com.juliaca.routes.msroutes.model.Stop;
import com.juliaca.routes.msroutes.service.LineService;
import com.juliaca.routes.msroutes.service.RouteService;
import com.juliaca.routes.msroutes.service.StopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RouteController {

    private final LineService lineService;
    private final RouteService routeService;
    private final StopService stopService;

    public RouteController(LineService lineService, RouteService routeService, StopService stopService) {
        this.lineService = lineService;
        this.routeService = routeService;
        this.stopService = stopService;
    }

    @GetMapping("/lines")
    public List<Line> getLines() {
        return lineService.findAll();
    }

    @GetMapping("/routes")
    public List<RouteDTO> getRoutes() {
        return routeService.findAllDTO();
    }

    @GetMapping("/routes/{id}")
    public RouteDTO getRoute(@PathVariable Long id) {
        Route route = routeService.findById(id);
        return route != null ? routeService.toDTO(route) : null;
    }

    @GetMapping("/routes/{id}/stops")
    public List<Stop> getStops(@PathVariable Long id) {
        return stopService.findByRoute(id);
    }

    @PostMapping("/routes")
    public Route saveRoute(@RequestBody Route route) {
        return routeService.save(route);
    }

    @PostMapping("/routes/wkt")
    public ResponseEntity<Route> saveRouteFromWKT(@RequestBody RouteWKTRequest request) throws Exception {
        Route saved = routeService.createRouteFromWKT(request.getName(), request.getLineId(), request.getPath());
        return ResponseEntity.ok(saved);
    }
}