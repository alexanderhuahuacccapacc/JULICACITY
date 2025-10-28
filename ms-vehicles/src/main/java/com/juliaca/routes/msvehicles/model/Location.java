package com.juliaca.routes.msvehicles.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    private double lat;
    private double lng;
    private long timestamp;
}
