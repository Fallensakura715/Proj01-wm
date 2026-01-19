package com.fallensakura.websocket;

import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/ws/{sid}")
@Slf4j
public class WebSocketServer {

    private static Map<String, Session> sessionMap = new HashMap<>();

    public void onOpen(Session session, @PathParam("sid") String sid) {
        log.info("Client: {} connected", sid);
        sessionMap.put(sid, session);
    }

    public void onMessage(String msg, @PathParam("sid") String sid) {
        log.info("Client({}) send message:{}", sid, msg);
    }

    public void onClose(@PathParam("sid") String sid) {
        log.info("Connection closed: {}", sid);
        sessionMap.remove(sid);
    }

    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        sessions.forEach(session -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
