package com.juliaca.routes.msroutes.dto;

import lombok.Data;

@Data
public class StopRequest {
    private String name;
    private Long routeId;
    private double lng;
    private double lat;
}
