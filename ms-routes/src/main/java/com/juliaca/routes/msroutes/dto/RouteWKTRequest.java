package com.juliaca.routes.msroutes.dto;

import lombok.Data;

@Data
public class RouteWKTRequest {
    private String name;
    private Long lineId;
    private String path;
}
