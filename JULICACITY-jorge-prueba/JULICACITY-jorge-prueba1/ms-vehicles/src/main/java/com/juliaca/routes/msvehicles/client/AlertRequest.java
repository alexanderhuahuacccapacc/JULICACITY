package com.juliaca.routes.msvehicles.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertRequest {
    private String vehicleId;
    private String type;
    private String message;
    private String severity;
    private boolean active;
}