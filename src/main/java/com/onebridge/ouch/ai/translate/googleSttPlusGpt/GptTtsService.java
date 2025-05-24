// package com.onebridge.ouch.ai.translate.googleSttPlusGpt;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;
//
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @Service
// public class GptTtsService {
//     @Value("${openai.api-key}")
//     private String openAiApiKey;
//
//     private final RestTemplate restTemplate = new RestTemplate();
//
//     public byte[] translateAndSynthesize(String text) {
//         String translatedText = translateText(text);
//         return textToSpeech(translatedText);
//     }
//
//     private String translateText(String text) {
//         String url = "https://api.openai.com/v1/chat/completions";
//         HttpHeaders headers = new HttpHeaders();
//         headers.setBearerAuth(openAiApiKey);
//         headers.setContentType(MediaType.APPLICATION_JSON);
//
//         String systemPrompt = "Detect the language and translate the following text to Korean if it is not Korean, or to English if it is Korean. Output only the translation.";
//         String reqBody = String.format("""
//         {
//           "model": "gpt-4o",
//           "messages": [
//             {"role": "system", "content": "%s"},
//             {"role": "user", "content": "%s"}
//           ],
//           "max_tokens": 512
//         }
//         """, systemPrompt, text.replace("\"", "\\\""));
//
//         HttpEntity<String> requestEntity = new HttpEntity<>(reqBody, headers);
//         ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
//         if (!response.getStatusCode().is2xxSuccessful()) {
//             throw new RuntimeException("번역 API 실패: " + response.getBody());
//         }
//
//         // GPT 응답 JSON 파싱 (Jackson 등 사용)
//         try {
//             ObjectMapper mapper = new ObjectMapper();
//             JsonNode root = mapper.readTree(response.getBody());
//             return root.path("choices").get(0).path("message").path("content").asText().trim();
//         } catch (Exception e) {
//             throw new RuntimeException("GPT 응답 파싱 오류", e);
//         }
//     }
//
//     private byte[] textToSpeech(String text) {
//         String url = "https://api.openai.com/v1/audio/speech";
//         HttpHeaders headers = new HttpHeaders();
//         headers.setBearerAuth(openAiApiKey);
//         headers.setContentType(MediaType.APPLICATION_JSON);
//
//         String reqBody = String.format("""
//         {
//           "model": "tts-1",
//           "input": "%s",
//           "voice": "nova",
//           "response_format": "opus"
//         }
//         """, text.replace("\"", "\\\""));
//
//         HttpEntity<String> requestEntity = new HttpEntity<>(reqBody, headers);
//         ResponseEntity<byte[]> response = restTemplate.exchange(
//             url, HttpMethod.POST, requestEntity, byte[].class
//         );
//
//         if (!response.getStatusCode().is2xxSuccessful()) {
//             throw new RuntimeException("TTS API 실패: " + response.getBody());
//         }
//         return response.getBody();
//     }
// }
