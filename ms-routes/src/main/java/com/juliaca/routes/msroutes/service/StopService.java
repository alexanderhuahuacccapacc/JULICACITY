package com.juliaca.routes.msroutes.service;

import com.juliaca.routes.msroutes.model.Stop;
import com.juliaca.routes.msroutes.repository.StopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopService {

    private final StopRepository repository;

    public StopService(StopRepository repository) {
        this.repository = repository;
    }

    public List<Stop> findByRoute(Long routeId) {
        return repository.findByRouteId(routeId);
    }

    public Stop save(Stop stop) {
        return repository.save(stop);
    }
}
