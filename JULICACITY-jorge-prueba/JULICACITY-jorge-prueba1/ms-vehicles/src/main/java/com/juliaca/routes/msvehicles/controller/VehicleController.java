package com.juliaca.routes.msvehicles.controller;

import com.juliaca.routes.msvehicles.model.Location;
import com.juliaca.routes.msvehicles.model.Vehicle;
import com.juliaca.routes.msvehicles.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping("/vehicles")
    public List<Vehicle> getAll() {
        return service.findAll();
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getOne(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/vehicles")
    public Vehicle create(@RequestBody Vehicle v) {
        return service.save(v);
    }

    @PostMapping("/vehicles/{id}/location")
    public ResponseEntity<Void> updateLocation(@PathVariable String id, @RequestBody Location loc) {
        service.updateLocation(id, loc);
        return ResponseEntity.ok().build();
    }
}
