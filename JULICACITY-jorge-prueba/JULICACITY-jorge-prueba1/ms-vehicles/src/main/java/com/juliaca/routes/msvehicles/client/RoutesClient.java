package com.juliaca.routes.msvehicles.client;

import com.juliaca.routes.msvehicles.dto.RouteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-routes", url = "http://localhost:8081")
public interface RoutesClient {

    @GetMapping("/api/routes/{routeId}")
    RouteDTO getRoute(@PathVariable String routeId);
}
