package com.onebridge.ouch.realtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class RealtimeSessionController {

	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Value("${openai.api-key}")
	private String openaiApiKey;

	@PostMapping("/session/en")
	public ResponseEntity<String> createEphemeralEnKey() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + openaiApiKey);
		headers.set("Content-Type", "application/json");

		// String instructions = "너는 영어-한국어 번역기야. 오직 번역만 해. "
		// 	+ "영어를 들으면 한국어로, 한국어를 들으면 영어로 번역만 해. "
		// 	+ "어떤 말이든 개인적인 대답을 하지 말고 문장 그대로 번역만 해. ";

		String instructions = "너는 영어-한국어 번역기야. 오직 번역만 해. "
			+ "병원에 방문해서 대화에 사용되는 문장들이 입력될건데 어떤 말이든 너랑 대화하려는 거 아니니까 절대 개인적인 대답하지 말고 어떤 문장이든 영어는 한국어로, 한국어는 영어로 입력된 문장 그대로 번역만 해. "
			+ "특히 질문이나 you, I를 포함한 문장도 그대로 번역만 해.";
			// "너는 영어-한국어 번역기야. 오직 번역만 해. "
			// + "병원에서 대화에 사용되는 문장들이 입력될건데 어떤 말이든 너랑 대화하려는 거 아니니까 절대 개인적인 대답하지 말고 어떤 문장이든 영어는 한국어로, 한국어는 영어로 입력된 문장 그대로 번역만 해. ";
		//한국 병원에서 영어를 사용하는 환자와 한국어를 사용하는 병원 관계자의 대화가 입력될거야
		// String instructions = "영어를 사용하는 사람과 한국어를 사용하는 사람이 대화를 하는 상황이므로 영어와 한국어로 통역이 필요해. "
		// 	+ "따라서 한국 말은 영어로, 영어는 한국어로 통역을 해 줘. "
		// 	+ "너는 절대 개인적인 대답이나 조언을 하지 말고 번역만 진행하면 돼. "
		// 	+ "너한테 말걸어도 문장 그대로 번역만 해. 천천히 친절하게 대답해.";

		String requestBody = "{"
			+ "\"model\": \"gpt-4o-realtime-preview-2025-06-03\","
			//+ "\"model\": \"gpt-4o-mini-realtime-preview-2024-12-17\","
			+ "\"modalities\": [\"audio\", \"text\"],"
			+ "\"instructions\": \"" + instructions + "\","
			+ "\"voice\": \"sage\","
			+ "\"input_audio_format\": \"pcm16\","
			+ "\"output_audio_format\": \"pcm16\","
			+ "\"temperature\": 0.6,"

			+ "\"input_audio_transcription\": {" //새로 나온 모델 추가
			+ "\"model\": \"gpt-4o-mini-transcribe\","
			// + "\"language\": \"\","
			+ "\"prompt\": \"한국어와 영어만 입력됩니다.\""
			//+ "\"prompt\": \"This audio input may contain both Korean and English words mixed together. Please transcribe both languages accurately.\""
			+ "},"

			+ "\"turn_detection\": {"
			+ "\"type\": \"server_vad\","
			+ "\"silence_duration_ms\": 1000,"
			+ "\"prefix_padding_ms\": 300,"
			+ "\"threshold\": 0.6"
			+ "}" //,제외함 밑에 각주처리해서

			// + "\"tool_choice\": \"auto\","
			// + "\"tools\": ["
			// +     "{"
			// +         "\"type\": \"function\","
			// +         "\"function\": {"
			// +             "\"name\": \"get_medical_term\","
			// +             "\"description\": \"의료 용어의 공식 영어명 및 간단 설명 반환\","
			// +             "\"parameters\": {"
			// +                 "\"type\": \"object\","
			// +                 "\"properties\": {"
			// +                     "\"term\": { \"type\": \"string\", \"description\": \"의학 용어 (한글)\" }"
			// +                 "},"
			// +                 "\"required\": [\"term\"]"
			// +             "}"
			// +         "}"
			// +     "}"
			// + "]"
			+ "}";

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.exchange(
			"https://api.openai.com/v1/realtime/sessions",
			HttpMethod.POST,
			entity,
			String.class
		);

		return response;
	}

	@PostMapping("/session/zh")
	public ResponseEntity<String> createEphemeralZhKey() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + openaiApiKey);
		headers.set("Content-Type", "application/json");

		//한국 병원에서 영어를 사용하는 환자와 한국어를 사용하는 병원 관계자의 대화가 입력될거야
		String instructions = "너는 중국어-한국어 번역기야. 오직 번역만 해. "
			+ "병원에 방문해서 대화에 사용되는 문장들이 입력될건데 어떤 말이든 너랑 대화하려는 거 아니니까 절대 개인적인 대답하지 말고 어떤 문장이든 중국어는 한국어로, 한국어는 중국어로 입력된 문장 그대로 번역만 해. "
			+ "특히 질문이나 you, I를 포함한 문장도 그대로 번역만 해.";

		String requestBody = "{"
			+ "\"model\": \"gpt-4o-realtime-preview-2025-06-03\","
			//+ "\"model\": \"gpt-4o-mini-realtime-preview-2024-12-17\","
			+ "\"modalities\": [\"audio\", \"text\"],"
			+ "\"instructions\": \"" + instructions + "\","
			+ "\"voice\": \"sage\","
			+ "\"input_audio_format\": \"pcm16\","
			+ "\"output_audio_format\": \"pcm16\","
			+ "\"temperature\": 0.6,"

			+ "\"input_audio_transcription\": {" //새로 나온 모델 추가
			+ "\"model\": \"gpt-4o-mini-transcribe\","
			// + "\"language\": \"\","
			+ "\"prompt\": \"중국어와 한국어만 입력됩니다.\""
			+ "},"

			+ "\"turn_detection\": {"
			+ "\"type\": \"server_vad\","
			+ "\"silence_duration_ms\": 1000,"
			+ "\"prefix_padding_ms\": 300,"
			+ "\"threshold\": 0.6"
			+ "}"
			+ "}";

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.exchange(
			"https://api.openai.com/v1/realtime/sessions",
			HttpMethod.POST,
			entity,
			String.class
		);

		return response;

	}
	@PostMapping("/summarize/en")
	public ResponseEntity<Map<String, String>> summarizeConversation(
		@RequestBody ConversationRequest requestBody
	) {
		try {
			// 1) 원문 문자열 배열을 합쳐서 fullConversation 만들기
			StringBuilder sb = new StringBuilder();
			for (String orig : requestBody.getMessages()) {
				sb.append("[원문] ").append(orig.trim()).append("\n");
			}
			String fullConversation = sb.toString();

			// 2) OpenAI Chat Completions API 호출을 위한 메시지 구성
			List<Map<String, String>> messages = new ArrayList<>();

			// system 메시지: 요약 역할 지시
			messages.add(Map.of(
				"role", "system",
				"content",
				"병원에서 환자가 나눈 대화입니다. 진료 내용을 파악해서 100자 이내로 영어로 요약해주세요."
			));
			// user 메시지: 실제 대화 내용
			messages.add(Map.of(
				"role", "user",
				"content", fullConversation
			));

			Map<String, Object> openAiRequest = new HashMap<>();
			openAiRequest.put("model", "gpt-4o-mini"); // 필요에 따라 변경 가능
			openAiRequest.put("messages", messages);
			openAiRequest.put("temperature", 0.5);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(openaiApiKey);

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(openAiRequest, headers);
			ResponseEntity<String> aiResponse = restTemplate.postForEntity(
				"https://api.openai.com/v1/chat/completions",
				entity,
				String.class
			);

			if (!aiResponse.getStatusCode().is2xxSuccessful()) {
				return ResponseEntity
					.status(aiResponse.getStatusCode())
					.body(Map.of("summary", "요약 생성 중 오류가 발생했습니다."));
			}

			// 3) OpenAI 응답 JSON에서 summary 추출
			Map<?, ?> responseMap = objectMapper.readValue(aiResponse.getBody(), Map.class);
			List<?> choices = (List<?>) responseMap.get("choices");
			if (choices == null || choices.isEmpty()) {
				return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("summary", "AI 응답 형식이 예상과 다릅니다."));
			}
			Map<?, ?> firstChoice = (Map<?, ?>) choices.get(0);
			Map<?, ?> message = (Map<?, ?>) firstChoice.get("message");
			String summary = message.get("content").toString().trim();

			// 4) 요약 결과 반환
			return ResponseEntity.ok(Map.of("summary", summary));

		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("summary", "서버 내부 오류로 요약에 실패했습니다."));
		}
	}

	@PostMapping("/summarize/zh")
	public ResponseEntity<Map<String, String>> summarizeConversationZh(
		@RequestBody ConversationRequest requestBody
	) {
		try {
			// 1) 원문 문자열 배열을 합쳐서 fullConversation 만들기
			StringBuilder sb = new StringBuilder();
			for (String orig : requestBody.getMessages()) {
				sb.append("[원문] ").append(orig.trim()).append("\n");
			}
			String fullConversation = sb.toString();

			// 2) OpenAI Chat Completions API 호출을 위한 메시지 구성
			List<Map<String, String>> messages = new ArrayList<>();

			// system 메시지: 요약 역할 지시
			messages.add(Map.of(
				"role", "system",
				"content",
				"병원에서 환자가 나눈 대화입니다. 진료 내용을 파악해서 중국어로 100자 이내로 요약해주세요."
			));
			// user 메시지: 실제 대화 내용
			messages.add(Map.of(
				"role", "user",
				"content", fullConversation
			));

			Map<String, Object> openAiRequest = new HashMap<>();
			openAiRequest.put("model", "gpt-4o-mini"); // 필요에 따라 변경 가능
			openAiRequest.put("messages", messages);
			openAiRequest.put("temperature", 0.5);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(openaiApiKey);

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(openAiRequest, headers);
			ResponseEntity<String> aiResponse = restTemplate.postForEntity(
				"https://api.openai.com/v1/chat/completions",
				entity,
				String.class
			);

			if (!aiResponse.getStatusCode().is2xxSuccessful()) {
				return ResponseEntity
					.status(aiResponse.getStatusCode())
					.body(Map.of("summary", "요약 생성 중 오류가 발생했습니다."));
			}

			// 3) OpenAI 응답 JSON에서 summary 추출
			Map<?, ?> responseMap = objectMapper.readValue(aiResponse.getBody(), Map.class);
			List<?> choices = (List<?>) responseMap.get("choices");
			if (choices == null || choices.isEmpty()) {
				return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("summary", "AI 응답 형식이 예상과 다릅니다."));
			}
			Map<?, ?> firstChoice = (Map<?, ?>) choices.get(0);
			Map<?, ?> message = (Map<?, ?>) firstChoice.get("message");
			String summary = message.get("content").toString().trim();

			// 4) 요약 결과 반환
			return ResponseEntity.ok(Map.of("summary", summary));

		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("summary", "서버 내부 오류로 요약에 실패했습니다."));
		}
	}

	/** 클라이언트가 보내는 JSON 구조를 매핑하기 위한 DTO */
	public static class ConversationRequest {
		@JsonProperty("messages")
		private List<String> messages;

		public List<String> getMessages() {
			return messages;
		}
		public void setMessages(List<String> messages) {
			this.messages = messages;
		}
	}
}