package com.juliaca.routes.msroutes.repository;

import com.juliaca.routes.msroutes.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StopRepository extends JpaRepository<Stop, Long> {
    List<Stop> findByRouteId(Long routeId);
}
