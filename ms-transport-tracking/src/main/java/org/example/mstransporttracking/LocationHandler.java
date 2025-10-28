package org.example.mstransporttracking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mstransporttracking.entity.Location;
import org.example.mstransporttracking.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CopyOnWriteArrayList;
@Component
public class LocationHandler extends TextWebSocketHandler {
    @Autowired
    private LocationRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("Nueva conexión: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Ubicación recibida: " + message.getPayload());

        // Parsear JSON recibido
        Location location = mapper.readValue(message.getPayload(), Location.class);
        location.setTimestamp(System.currentTimeMillis());
        LocalDateTime time = Instant.ofEpochMilli(location.getTimestamp())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();


        // Guardar en base de datos
        repository.save(location);

        // Reenviar a los demás clientes
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(message);
            }
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}
