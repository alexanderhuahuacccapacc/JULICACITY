package com.juliaca.routes.msvehicles.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-alerts", url = "http://localhost:8083")
public interface AlertClient {

    @PostMapping("/api/alerts")
    void createAlert(@RequestBody AlertRequest alert);
}
