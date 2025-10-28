package com.juliaca.routes.msvehicles.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "vehicles")
public class Vehicle {

    @Id
    private String id;

    private String plate;
    private String line;
    private String status;

    @Builder.Default
    private Location current = new Location(0.0, 0.0, 0L);

    private List<Location> path;

    private Integer pathIndex;

    private long lastUpdated;
}
