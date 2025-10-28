package org.example.mstransporttracking.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "vehicle_id", nullable = false)
    private String vehicleId;

    @JsonProperty("lat")
    @Column(name = "latitude", nullable = false)
    private double latitude;

    @JsonProperty("lon")
    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "timestamp", nullable = false)
    private Long timestamp;



}
