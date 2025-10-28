package com.juliaca.routes.msroutes.repository;

import com.juliaca.routes.msroutes.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {}