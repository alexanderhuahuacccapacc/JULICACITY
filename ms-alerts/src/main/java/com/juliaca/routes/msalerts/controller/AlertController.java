package com.juliaca.routes.msalerts.controller;

import com.juliaca.routes.msalerts.model.Alert;
import com.juliaca.routes.msalerts.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "*")
public class AlertController {

    private final AlertService service;

    public AlertController(AlertService service) {
        this.service = service;
    }

    @GetMapping
    public List<Alert> getAll() {
        return service.findAll();
    }

    @GetMapping("/active")
    public List<Alert> getActive() {
        return service.findActive();
    }

    @GetMapping("/vehicle/{id}")
    public List<Alert> getByVehicle(@PathVariable("id") String id) {
        return service.findByVehicle(id);
    }

    @PostMapping
    public Alert create(@RequestBody Alert alert) {
        return service.save(alert);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable("id") String id) {
        service.deactivate(id);
        return ResponseEntity.ok().build();
    }
}
