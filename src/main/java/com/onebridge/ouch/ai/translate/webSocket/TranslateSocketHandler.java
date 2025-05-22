// package com.onebridge.ouch.ai.translate;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.stereotype.Component;
// import org.springframework.web.socket.*;
// import org.springframework.web.socket.handler.BinaryWebSocketHandler;
//
// @Slf4j
// @Component
// @RequiredArgsConstructor
// public class TranslateSocketHandler extends BinaryWebSocketHandler {
// 	private final OpenAiService openAiService;
//
// 	@Override
// 	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
// 		log.info("오디오 청크 수신 ({} bytes)", message.getPayloadLength());
//
// 		// 1. 바이너리 데이터 -> MultipartFile 형태로 래핑
// 		ByteArrayMultipartFile audioFile = new ByteArrayMultipartFile(
// 			message.getPayload().array(),
// 			"audio",
// 			"chunk.webm",    // 실제 클라이언트에서 보내는 포맷(webm/mp3 등)에 맞게!
// 			"audio/webm"
// 		);
//
// 		// 2. AI 서비스 호출 (STT->번역->TTS)
// 		byte[] translatedAudio = openAiService.processAudio(audioFile);
//
// 		// 3. 결과 오디오를 클라이언트로 바이너리 메시지로 전송
// 		session.sendMessage(new BinaryMessage(translatedAudio));
// 	}
// }
