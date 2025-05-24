// package com.onebridge.ouch.ai.translate.googleSttPlusGpt;
//
// import org.springframework.context.annotation.Configuration;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.socket.config.annotation.*;
//
// @Configuration
// @EnableWebSocket
// public class WebSocketConfig implements WebSocketConfigurer {
//     @Autowired
//     private GoogleSttSocketHandler googleSttSocketHandler;
//
//     @Override
//     public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//         registry.addHandler(googleSttSocketHandler, "/ws/stt").setAllowedOrigins("*");
//     }
// }
