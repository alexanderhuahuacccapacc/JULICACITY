package com.juliaca.routes.msalerts.stomp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juliaca.routes.msalerts.model.VehicleDTO;
import com.juliaca.routes.msalerts.service.AlertProcessorService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;

@Component
public class VehicleStompClient {

    private final AlertProcessorService processor;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${vehicles.websocket.url}")
    private String websocketUrl;

    @Value("${vehicles.websocket.topic}")
    private String topic;

    public VehicleStompClient(AlertProcessorService processor) {
        this.processor = processor;
    }

    @PostConstruct
    public void connect() {
        try {
            Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
            SockJsClient sockJsClient = new SockJsClient(Collections.singletonList(webSocketTransport));
            WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
            stompClient.setMessageConverter(new org.springframework.messaging.converter.MappingJackson2MessageConverter());

            StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
                @Override
                public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                    session.subscribe(topic, new StompFrameHandler() {
                        @Override
                        public Type getPayloadType(StompHeaders headers) {
                            return String.class;
                        }

                        @Override
                        public void handleFrame(StompHeaders headers, Object payload) {
                            try {
                                String json = (String) payload;
                                VehicleDTO vehicle = mapper.readValue(json, VehicleDTO.class);
                                processor.processVehicleUpdate(vehicle);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void handleTransportError(StompSession session, Throwable exception) {
                    System.err.println("STOMP transport error: " + exception.getMessage());
                }
            };

            ListenableFuture<StompSession> f = stompClient.connect(websocketUrl, sessionHandler);
            f.addCallback(
                    session -> System.out.println("Conectado a ms-vehicles WS: " + websocketUrl),
                    ex -> System.err.println("Error conectando a ms-vehicles WS: " + ex.getMessage())
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
