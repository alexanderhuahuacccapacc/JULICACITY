package com.juliaca.routes.msroutes.service;

import com.juliaca.routes.msroutes.model.Route;
import com.juliaca.routes.msroutes.repository.LineRepository;
import com.juliaca.routes.msroutes.repository.RouteRepository;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository repository;
    private final LineRepository lineRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

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

    // Nuevo método para crear rutas desde WKT
    public Route createRouteFromWKT(String name, Long lineId, String wkt) throws Exception {
        WKTReader reader = new WKTReader(geometryFactory);
        Geometry geometry = reader.read(wkt);

        if (!(geometry instanceof LineString)) {
            throw new IllegalArgumentException("El WKT proporcionado no es un LineString válido");
        }

        LineString lineString = (LineString) geometry;

        Route route = new Route();
        route.setName(name);
        route.setLine(lineRepository.findById(lineId).orElseThrow());
        route.setPath(lineString);

        return repository.save(route);
    }
}
