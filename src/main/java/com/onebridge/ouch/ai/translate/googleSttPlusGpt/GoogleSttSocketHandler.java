// package com.onebridge.ouch.ai.translate.googleSttPlusGpt;
//
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.List;
// import java.util.concurrent.ConcurrentHashMap;
//
// import jakarta.annotation.PostConstruct;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.core.io.Resource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.socket.BinaryMessage;
// import org.springframework.web.socket.CloseStatus;
// import org.springframework.web.socket.TextMessage;
// import org.springframework.web.socket.WebSocketSession;
// import org.springframework.web.socket.handler.BinaryWebSocketHandler;
//
// import com.google.api.gax.core.FixedCredentialsProvider;
// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.api.gax.rpc.ClientStream;
// import com.google.api.gax.rpc.ResponseObserver;
// import com.google.api.gax.rpc.StreamController;
// import com.google.cloud.speech.v1.RecognitionConfig;
// import com.google.cloud.speech.v1.StreamingRecognitionConfig;
// import com.google.cloud.speech.v1.StreamingRecognizeRequest;
// import com.google.cloud.speech.v1.StreamingRecognizeResponse;
// import com.google.cloud.speech.v1.StreamingRecognitionResult;
// import com.google.cloud.speech.v1.SpeechClient;
// import com.google.cloud.speech.v1.SpeechSettings;
// import com.google.protobuf.ByteString;
//
// @Component
// public class GoogleSttSocketHandler extends BinaryWebSocketHandler {
//
//     @Value("${google.application.credentials}")
//     private Resource googleCredentialResource;
//
//     private SpeechClient speechClient;
//     private final ConcurrentHashMap<String, ClientStream<StreamingRecognizeRequest>> clientStreams = new ConcurrentHashMap<>();
//     private final GptTtsService gptTtsService;
//
//     public GoogleSttSocketHandler(GptTtsService gptTtsService) {
//         this.gptTtsService = gptTtsService;
//     }
//
//     @PostConstruct
//     public void init() throws IOException {
//         // 서비스 계정 JSON을 직접 로드해 명시적 자격 증명 설정
//         try (InputStream is = googleCredentialResource.getInputStream()) {
//             GoogleCredentials creds = GoogleCredentials.fromStream(is)
//                 .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//
//             SpeechSettings settings = SpeechSettings.newBuilder()
//                 .setCredentialsProvider(FixedCredentialsProvider.create(creds))
//                 .build();
//
//             speechClient = SpeechClient.create(settings);
//         }
//     }
//
//     @Override
//     public void afterConnectionEstablished(WebSocketSession session) {
//         System.out.println("[서버] WebSocket 연결됨: " + session.getId());
//         try {
//             // STT 설정
//             RecognitionConfig recogConfig = RecognitionConfig.newBuilder()
//                 .setEncoding(RecognitionConfig.AudioEncoding.WEBM_OPUS)
//                 .setSampleRateHertz(48000)
//                 .setLanguageCode("ko-KR")
//                 .build();
//
//             StreamingRecognitionConfig streamingConfig = StreamingRecognitionConfig.newBuilder()
//                 .setConfig(recogConfig)
//                 .setInterimResults(true)
//                 .build();
//
//             // STT 스트림 시작
//             ClientStream<StreamingRecognizeRequest> clientStream =
//                 speechClient.streamingRecognizeCallable().splitCall(new ResponseObserver<StreamingRecognizeResponse>() {
//                     @Override
//                     public void onStart(StreamController controller) {
//                         System.out.println("[서버] STT Streaming 시작 (세션: " + session.getId() + ")");
//                     }
//
//                     @Override
//                     public void onResponse(StreamingRecognizeResponse response) {
//                         for (StreamingRecognitionResult result : response.getResultsList()) {
//                             if (!result.getIsFinal()) continue;
//
//                             String transcript = result.getAlternatives(0).getTranscript().trim();
//                             if (transcript.isEmpty()) continue;
//
//                             try {
//                                 byte[] audioBytes = gptTtsService.translateAndSynthesize(transcript);
//                                 session.sendMessage(new BinaryMessage(audioBytes));
//                             } catch (Exception e) {
//                                 try {
//                                     session.sendMessage(new TextMessage("TTS 처리 중 오류가 발생했습니다."));
//                                 } catch (IOException ioe) {
//                                     System.err.println("[서버] 클라이언트 알림 전송 오류: " + ioe.getMessage());
//                                 }
//                             }
//                         }
//                     }
//
//                     @Override
//                     public void onError(Throwable t) {
//                         System.err.println("[서버] STT 오류 (세션: " + session.getId() + "): " + t.getMessage());
//                         try {
//                             session.sendMessage(new TextMessage("STT 처리 중 오류가 발생했습니다."));
//                         } catch (IOException ioe) {
//                             System.err.println("[서버] 클라이언트 알림 전송 오류: " + ioe.getMessage());
//                         }
//                     }
//
//                     @Override
//                     public void onComplete() {
//                         System.out.println("[서버] STT Streaming 종료 (세션: " + session.getId() + ")");
//                         // 세션은 클라이언트가 stop할 때까지 유지
//                     }
//                 });
//
//             // 초기 설정 전송
//             clientStream.send(StreamingRecognizeRequest.newBuilder()
//                 .setStreamingConfig(streamingConfig)
//                 .build());
//
//             clientStreams.put(session.getId(), clientStream);
//
//         } catch (Exception e) {
//             e.printStackTrace();
//             try {
//                 session.close(CloseStatus.SERVER_ERROR);
//             } catch (IOException ioe) {
//                 System.err.println("[서버] 세션 종료 오류: " + ioe.getMessage());
//             }
//         }
//     }
//
//     @Override
//     protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
//         ClientStream<StreamingRecognizeRequest> clientStream = clientStreams.get(session.getId());
//         if (clientStream != null) {
//             try {
//                 clientStream.send(StreamingRecognizeRequest.newBuilder()
//                     .setAudioContent(ByteString.copyFrom(message.getPayload().array()))
//                     .build());
//             } catch (Exception e) {
//                 System.err.println("[서버] 오디오 청크 전송 오류 (세션: " + session.getId() + "): " + e.getMessage());
//             }
//         }
//     }
//
//     @Override
//     public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//         System.out.println("[서버] WebSocket 종료: " + session.getId() + " (" + status + ")");
//         ClientStream<StreamingRecognizeRequest> stream = clientStreams.remove(session.getId());
//         if (stream != null) stream.closeSend();
//     }
// }
