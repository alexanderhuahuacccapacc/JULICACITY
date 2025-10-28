package com.juliaca.routes.msalerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private String id;
    private String plate;
    private String line;
    private String status;
    private LocationDTO current;
    private Long lastUpdated;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationDTO {
        private Double lat;
        private Double lon;
        private Long timestamp;
    }
}
