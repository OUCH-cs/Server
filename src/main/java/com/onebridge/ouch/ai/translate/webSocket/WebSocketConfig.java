// package com.onebridge.ouch.ai.translate;
//
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.socket.config.annotation.EnableWebSocket;
// import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
// import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
// @Configuration
// @EnableWebSocket
// public class WebSocketConfig implements WebSocketConfigurer {
//
// 	private final TranslateSocketHandler translateSocketHandler;
//
// 	public WebSocketConfig(TranslateSocketHandler translateHandler) {
// 		this.translateSocketHandler = translateHandler;
// 	}
//
// 	@Override
// 	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
// 		registry
// 			.addHandler(translateSocketHandler, "/ws/translate")
// 			.setAllowedOrigins("*");  // 실제 도메인으로 제한하세요
// 	}
// }