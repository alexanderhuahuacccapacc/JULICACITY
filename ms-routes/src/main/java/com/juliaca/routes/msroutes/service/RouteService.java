package com.juliaca.routes.msroutes.service;

import com.juliaca.routes.msroutes.model.Route;
import com.juliaca.routes.msroutes.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository repository;

    public RouteService(RouteRepository repository) {
        this.repository = repository;
    }

    public List<Route> findAll() {
        return repository.findAll();
    }

    public Route findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Route save(Route route) {
        return repository.save(route);
    }
}
