package com.juliaca.routes.msvehicles.dto;

import lombok.Data;

@Data
public class LocationDTO {
    private double lat;
    private double lng;

    public LocationDTO() {}

    public LocationDTO(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
