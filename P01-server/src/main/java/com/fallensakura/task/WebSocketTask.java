package com.fallensakura.task;

import com.fallensakura.websocket.WebSocketServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketTask {

    private final WebSocketServer webSocketServer;

    @Scheduled(cron = "0 */5 * * * *")
    public void sendMessageToClient() {
        webSocketServer.sendToAllClient("This is a message from WS server: " +
                DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
    }
}
