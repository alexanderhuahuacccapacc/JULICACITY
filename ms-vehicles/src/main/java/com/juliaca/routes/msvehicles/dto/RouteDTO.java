package com.juliaca.routes.msvehicles.dto;

import lombok.Data;

import java.util.List;

@Data
public class RouteDTO {
    private String id;
    private String name;
    private String wktPath;
    private List<LocationDTO> path;
}
