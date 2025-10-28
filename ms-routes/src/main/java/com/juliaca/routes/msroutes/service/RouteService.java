package com.juliaca.routes.msroutes.service;

import com.juliaca.routes.msroutes.dto.RouteDTO;
import com.juliaca.routes.msroutes.model.Route;
import com.juliaca.routes.msroutes.repository.LineRepository;
import com.juliaca.routes.msroutes.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final RouteRepository repository;
    private final LineRepository lineRepository;

    public RouteService(RouteRepository repository, LineRepository lineRepository) {
        this.repository = repository;
        this.lineRepository = lineRepository;
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

    public Route createRouteFromWKT(String name, Long lineId, String wkt) throws Exception {
        org.locationtech.jts.io.WKTReader reader = new org.locationtech.jts.io.WKTReader();
        org.locationtech.jts.geom.Geometry geometry = reader.read(wkt);

        if (!(geometry instanceof org.locationtech.jts.geom.LineString)) {
            throw new IllegalArgumentException("El WKT proporcionado no es un LineString válido");
        }

        org.locationtech.jts.geom.LineString lineString = (org.locationtech.jts.geom.LineString) geometry;

        Route route = new Route();
        route.setName(name);
        route.setLine(lineRepository.findById(lineId).orElseThrow());
        route.setPath(lineString);

        return repository.save(route);
    }

    public RouteDTO toDTO(Route route) {
        return new RouteDTO(
                route.getId(),
                route.getName(),
                route.getLine() != null ? route.getLine().getName() : null,
                route.getPath() != null ? route.getPath().toText() : null
        );
    }

    public List<RouteDTO> findAllDTO() {
        return findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
