package com.onebridge.ouch.ai.translate.googleTrans;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechSettings;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1.StreamingRecognitionConfig;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.protobuf.ByteString;

@Component
public class GoogleSttTranslateSocketHandler extends BinaryWebSocketHandler {

    // 이제 이 한 가지만 주입합니다.
    private final GoogleTranslateTtsService translateService;

    // 세션별 STT 스트림 저장소
    private final Map<String, ClientStream<StreamingRecognizeRequest>> streams = new ConcurrentHashMap<>();

    public GoogleSttTranslateSocketHandler(GoogleTranslateTtsService translateService) {
        this.translateService = translateService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            // 1) SpeechClient 생성 (인증은 서비스 레이어에서 이미 처리됨)
            SpeechSettings speechSettings = SpeechSettings.newBuilder().build();
            SpeechClient speechClient   = SpeechClient.create(speechSettings);

            // 2) STT 설정: 한국어 기본, 영어 자동 감지
            RecognitionConfig recogConfig = RecognitionConfig.newBuilder()
                .setEncoding(AudioEncoding.WEBM_OPUS)
                .setSampleRateHertz(48000)
                .setLanguageCode("ko-KR")
                .addAllAlternativeLanguageCodes(List.of("en-US"))
                .build();

            StreamingRecognitionConfig streamingConfig = StreamingRecognitionConfig.newBuilder()
                .setConfig(recogConfig)
                .setInterimResults(false)  // 중간 결과 무시
                .build();

            // 3) 스트리밍 시작
            ClientStream<StreamingRecognizeRequest> clientStream =
                speechClient.streamingRecognizeCallable()
                    .splitCall(new ResponseObserver<StreamingRecognizeResponse>() {
                        @Override public void onStart(StreamController controller) { }
                        @Override public void onError(Throwable t) { t.printStackTrace(); }
                        @Override public void onComplete() { }

                        @Override
                        public void onResponse(StreamingRecognizeResponse resp) {
                            for (StreamingRecognitionResult result : resp.getResultsList()) {
                                if (!result.getIsFinal()) continue;
                                String text = result.getAlternatives(0).getTranscript().trim();
                                // 숫자나 너무 짧은 건 필터
                                if (text.matches("^\\d+$") || text.length() <= 2) continue;
                                // ▶ Google Translate API + Cloud TTS 파이프라인으로 처리
                                translateService.translateAndSynthesizeToSession(text, session);
                            }
                        }
                    });

            // 4) 최초 config 요청
            clientStream.send(
                StreamingRecognizeRequest.newBuilder()
                    .setStreamingConfig(streamingConfig)
                    .build()
            );
            streams.put(session.getId(), clientStream);

        } catch (Exception e) {
            try { session.close(CloseStatus.SERVER_ERROR); } catch (IOException ignore) {}
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        ClientStream<StreamingRecognizeRequest> stream = streams.get(session.getId());
        if (stream != null) {
            stream.send(StreamingRecognizeRequest.newBuilder()
                .setAudioContent(ByteString.copyFrom(message.getPayload().array()))
                .build());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        ClientStream<StreamingRecognizeRequest> stream = streams.remove(session.getId());
        if (stream != null) stream.closeSend();
    }
}
