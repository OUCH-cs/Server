// package com.onebridge.ouch.ai.translate.whisper;
//
// import java.io.IOException;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.util.LinkedMultiValueMap;
// import org.springframework.util.MultiValueMap;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.http.*;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.core.io.ByteArrayResource;
//
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @Service
// public class OpenAiService {
//
// 	@Value("${openai.api-key}")
// 	private String openAiApiKey;
//
// 	private final RestTemplate restTemplate = new RestTemplate();
//
// 	// (1) 전체 프로세스: 오디오 파일 -> 번역된 음성 오디오 바이너리 반환
// 	public byte[] processAudio(MultipartFile audioFile) {
// 		try {
// 			// 1. 음성 인식(STT)
// 			String sttText = speechToText(audioFile);
//
// 			if (sttText.trim().length() < 2) {
// 				// 무의미한 결과, 번역/TTS skip
// 				return new byte[0];
// 			}
//
// 			// 2. 번역 (영어<->한국어 자동 감지)
// 			String translatedText = translateText(sttText);
//
// 			// 3. 번역문을 음성으로 (TTS)
// 			byte[] ttsAudio = textToSpeech(translatedText);
//
// 			return ttsAudio;
// 		} catch (Exception e) {
// 			throw new RuntimeException("OpenAI Service Error", e);
// 		}
// 	}
//
// 	// (2) OpenAI Whisper - STT API 호출 예시
// 	private String speechToText(MultipartFile audioFile) {
// 		String url = "https://api.openai.com/v1/audio/transcriptions";
// 		HttpHeaders headers = new HttpHeaders();
// 		headers.setBearerAuth(openAiApiKey);
// 		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
// 		// 파일 파트 구성
// 		ByteArrayResource audioResource;
// 		try {
// 			audioResource = new ByteArrayResource(audioFile.getBytes()) {
// 				@Override
// 				public String getFilename() {
// 					return audioFile.getOriginalFilename();
// 				}
// 			};
// 		} catch (IOException e) {
// 			throw new RuntimeException("파일을 바이트 배열로 읽는 데 실패했습니다", e);
// 		}
//
// 		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
// 		body.add("file", audioResource);
// 		body.add("model", "gpt-4o-mini-transcribe"); // OpenAI Whisper
// 		body.add("response_format", "text"); // 텍스트만
//
// 		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
// 		ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
// 		if (!response.getStatusCode().is2xxSuccessful()) {
// 			throw new RuntimeException("STT API 실패: " + response.getBody());
// 		}
// 		return response.getBody().trim();
// 	}
//
// 	private final ObjectMapper objectMapper = new ObjectMapper();
//
// 	// (3) GPT-4 등으로 번역 (EN→KO, KO→EN 자동 감지)
// 	private String translateText(String text) {
// 		String url = "https://api.openai.com/v1/chat/completions";
// 		HttpHeaders headers = new HttpHeaders();
// 		headers.setBearerAuth(openAiApiKey);
// 		headers.setContentType(MediaType.APPLICATION_JSON);
//
// 		// System 프롬프트: 양방향 자동 번역
// 		String systemPrompt = "Detect the language and translate the following text to Korean if it is not Korean, or to English if it is Korean. Output only the translation.";
//
// 		String reqBody = """
//         {
//           "model": "gpt-4o-mini",
//           "messages": [
//             {"role": "system", "content": "%s"},
//             {"role": "user", "content": "%s"}
//           ],
//           "max_tokens": 512
//         }
//         """.formatted(systemPrompt, text.replace("\"", "\\\""));
//
// 		HttpEntity<String> requestEntity = new HttpEntity<>(reqBody, headers);
//
// 		ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
// 		if (!response.getStatusCode().is2xxSuccessful()) {
// 			throw new RuntimeException("번역 API 실패: " + response.getBody());
// 		}
//
// 		// 여기서 안전하게 JSON 파싱
// 		// 번역 결과 파싱 (아래는 GPT 응답에서 번역 텍스트만 추출)
// 		String respBody = response.getBody();
// 		// gpt-4o 응답 파싱, 실제 응답 json 구조에 따라 아래 코드 수정 필요! gpt-4o API는 아래 경로로 결과가 나옴
// 		try {
// 			JsonNode root = objectMapper.readTree(respBody);
// 			// gpt-4o API는 아래 경로로 결과가 나옴
// 			String translated = root
// 				.path("choices")
// 				.get(0)
// 				.path("message")
// 				.path("content")
// 				.asText()
// 				.trim();
// 			return translated;
// 		} catch (Exception e) {
// 			throw new RuntimeException("GPT 응답 파싱 실패: " + respBody, e);
// 		}
// 	}
//
// 	// (4) OpenAI TTS (텍스트→음성)
// 	private byte[] textToSpeech(String text) {
// 		String url = "https://api.openai.com/v1/audio/speech";
// 		HttpHeaders headers = new HttpHeaders();
// 		headers.setBearerAuth(openAiApiKey);
// 		headers.setContentType(MediaType.APPLICATION_JSON);
//
// 		String reqBody = """
//         {
//           "model": "gpt-4o-mini-tts",
//           "input": "%s",
//           "voice": "nova",
//           "response_format": "opus"
//         }
//         """.formatted(text.replace("\"", "\\\""));
//
// 		// 여성 음성, 남성은 alloy, echo, etc. // webm/opus로 반환, mp3 등으로 바꿔도 됨
//
// 		HttpEntity<String> requestEntity = new HttpEntity<>(reqBody, headers);
//
// 		ResponseEntity<byte[]> response = restTemplate.exchange(
// 			url, HttpMethod.POST, requestEntity, byte[].class
// 		);
//
// 		if (!response.getStatusCode().is2xxSuccessful()) {
// 			throw new RuntimeException("TTS API 실패: " + response.getBody());
// 		}
//
// 		return response.getBody();
// 	}
// }
