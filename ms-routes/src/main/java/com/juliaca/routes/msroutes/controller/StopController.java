package com.juliaca.routes.msroutes.controller;

import com.juliaca.routes.msroutes.model.Stop;
import com.juliaca.routes.msroutes.service.StopService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StopController {

    private final StopService stopService;

    public StopController(StopService stopService) {
        this.stopService = stopService;
    }

    @GetMapping("/stops")
    public List<Stop> getAllStops() {
        return stopService.findAll();
    }

    @PostMapping("/stops")
    public Stop createStop(@RequestBody Stop stop) {
        return stopService.save(stop);
    }
}
