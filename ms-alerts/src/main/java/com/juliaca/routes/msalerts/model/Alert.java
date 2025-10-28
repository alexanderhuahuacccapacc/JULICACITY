package com.juliaca.routes.msalerts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "alerts")
public class Alert {

    @Id
    private String id;

    private String vehicleId;
    private String type; // e.g., "DELAY", "NO_SIGNAL", "OUT_OF_ROUTE", "CANCELLED"
    private String message;
    private String severity; // e.g., "INFO", "WARN", "CRITICAL"

    @Builder.Default
    private long timestamp = Instant.now().toEpochMilli();

    private boolean active;
}
