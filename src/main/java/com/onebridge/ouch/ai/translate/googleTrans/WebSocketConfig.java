package com.onebridge.ouch.ai.translate.googleTrans;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final GoogleSttTranslateSocketHandler googleHandler;

    public WebSocketConfig(GoogleSttTranslateSocketHandler googleHandler) {
        this.googleHandler = googleHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(googleHandler, "/ws/google-stt")
                .setAllowedOrigins("*");
    }
}