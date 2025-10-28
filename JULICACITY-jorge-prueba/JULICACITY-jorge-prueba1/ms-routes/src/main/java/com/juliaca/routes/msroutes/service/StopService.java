package com.juliaca.routes.msroutes.service;

import com.juliaca.routes.msroutes.model.Stop;
import com.juliaca.routes.msroutes.repository.StopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopService {

    private final StopRepository stopRepository;

    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    public List<Stop> findAll() {
        return stopRepository.findAll();
    }

    public Stop save(Stop stop) {
        return stopRepository.save(stop);
    }

    public List<Stop> findByRoute(Long routeId) {
        return stopRepository.findByRouteId(routeId);
    }
}
