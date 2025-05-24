// package com.onebridge.ouch.ai.translate.googleTrans;
//
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.List;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.core.io.Resource;
// import org.springframework.stereotype.Service;
// import org.springframework.web.socket.BinaryMessage;
// import org.springframework.web.socket.WebSocketSession;
//
// import com.google.api.gax.core.FixedCredentialsProvider;
// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.cloud.ServiceOptions;
// import com.google.cloud.texttospeech.v1.AudioConfig;
// import com.google.cloud.texttospeech.v1.AudioEncoding;
// import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
// import com.google.cloud.texttospeech.v1.SynthesisInput;
// import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
// import com.google.cloud.texttospeech.v1.TextToSpeechClient;
// import com.google.cloud.texttospeech.v1.TextToSpeechSettings;
// import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
// import com.google.cloud.translate.v3.LocationName;
// import com.google.cloud.translate.v3.TranslateTextRequest;
// import com.google.cloud.translate.v3.TranslateTextResponse;
// import com.google.cloud.translate.v3.TranslationServiceClient;
// import com.google.cloud.translate.v3.TranslationServiceSettings;
//
// import jakarta.annotation.PostConstruct;
//
// @Service
// public class GoogleTranslateTtsService {
//
//     @Value("${google.application.credentials}")
//     private Resource credential;
//
//     private TranslationServiceClient translateClient;
//     private TextToSpeechClient ttsClient;
//     private String projectId;
//     private final String location = "global";
//
//     @PostConstruct
//     public void init() throws IOException {
//         // 1) 서비스 계정 JSON 로드
//         try (InputStream is = credential.getInputStream()) {
//             GoogleCredentials creds = GoogleCredentials.fromStream(is)
//                 .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//             FixedCredentialsProvider cap = FixedCredentialsProvider.create(creds);
//
//             // 2) TranslationServiceClient 초기화
//             TranslationServiceSettings trSettings = TranslationServiceSettings.newBuilder()
//                 .setCredentialsProvider(cap)
//                 .build();
//             translateClient = TranslationServiceClient.create(trSettings);
//
//             // 3) TextToSpeechClient 초기화
//             TextToSpeechSettings ttsSettings = TextToSpeechSettings.newBuilder()
//                 .setCredentialsProvider(cap)
//                 .build();
//             ttsClient = TextToSpeechClient.create(ttsSettings);
//
//             // 4) 프로젝트 ID 자동 조회 (환경변수 또는 메타데이터)
//             projectId = ServiceOptions.getDefaultProjectId();
//         }
//     }
//
//     /**
//      * STT 핸들러에서 인식된 텍스트를 번역 → TTS 합성 → WebSocketSession 으로 전송합니다.
//      */
//     public void translateAndSynthesizeToSession(String text, WebSocketSession session) {
//         // 1) 원본 언어 자동 감지 (한글이 있으면 ko → en, 그렇지 않으면 en → ko)
//         boolean isKorean = containsKorean(text);
//         String sourceLang = isKorean ? "ko" : "en";
//         String targetLang = isKorean ? "en" : "ko";
//
//         // 2) 번역 요청
//         LocationName parent = LocationName.of(projectId, location);
//         TranslateTextRequest trReq = TranslateTextRequest.newBuilder()
//             .setParent(parent.toString())
//             .setSourceLanguageCode(sourceLang)
//             .setTargetLanguageCode(targetLang)
//             .addContents(text)
//             .build();
//         TranslateTextResponse trResp = translateClient.translateText(trReq);
//         String translatedText = trResp.getTranslations(0).getTranslatedText();
//
//         // 3) TTS 합성 요청
//         SynthesisInput input = SynthesisInput.newBuilder()
//             .setText(translatedText)
//             .build();
//         VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
//             .setLanguageCode(targetLang.equals("ko") ? "ko-KR" : "en-US")
//             .setSsmlGender(SsmlVoiceGender.NEUTRAL)
//             .build();
//         AudioConfig audioConfig = AudioConfig.newBuilder()
//             .setAudioEncoding(AudioEncoding.MP3)
//             .build();
//         SynthesizeSpeechResponse ttsResp = ttsClient.synthesizeSpeech(input, voice, audioConfig);
//         byte[] audioBytes = ttsResp.getAudioContent().toByteArray();
//
//         // 4) WebSocket으로 전송
//         try {
//             session.sendMessage(new BinaryMessage(audioBytes));
//         } catch (IOException e) {
//             // 전송 중 에러 로깅
//             System.err.println("TTS 전송 실패: " + e.getMessage());
//         }
//     }
//
//     /** 한글 글자가 하나라도 포함됐는지 검사 */
//     private boolean containsKorean(String text) {
//         return text.codePoints().anyMatch(cp ->
//             (cp >= 0xAC00 && cp <= 0xD7AF)   // 한글 완성형
//                 || (cp >= 0x1100 && cp <= 0x11FF)   // 한글 자모
//                 || (cp >= 0x3130 && cp <= 0x318F)   // 한글 호환 자모
//         );
//     }
// }
