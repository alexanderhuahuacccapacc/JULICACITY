package com.juliaca.routes.msroutes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouteDTO {
    private Long id;
    private String name;
    private String lineName;
    private String wktPath;
}
