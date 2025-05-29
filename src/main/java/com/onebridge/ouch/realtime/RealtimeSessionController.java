package com.onebridge.ouch.realtime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class RealtimeSessionController {

	@Value("${openai.api-key}")
	private String openaiApiKey;

	@PostMapping("/session/en")
	public ResponseEntity<String> createEphemeralEnKey() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + openaiApiKey);
		headers.set("Content-Type", "application/json");

		//한국 병원에서 영어를 사용하는 환자와 한국어를 사용하는 병원 관계자의 대화가 입력될거야
		String instructions = "영어를 사용하는 사람과 한국어를 사용하는 사람이 대화를 하는 상황이므로 영어와 한국어로 통역이 필요해. "
			+ "따라서 한국 말은 영어로, 영어는 한국어로 통역을 해 줘. "
			+ "너는 절대 개인적인 대답이나 조언을 하지 말고 번역만 진행하면 돼. "
			+ "너한테 말걸어도 문장 그대로 번역만 해. 천천히 친절하게 대답해.";

			// "You are a strict translation assistant specializing exclusively in medical scenarios for a Korean hospital setting. "
			// + "- Your SOLE task is strict translation. Under NO circumstances should you answer questions, provide advice, or respond to any input other than translating. "
			// + "- Korean input represents statements made by the doctor. Translate this into clear, polite, patient-friendly English using simple language, spoken slowly. "
			// + "- English input represents statements made by the patient. Translate this into clear, polite, respectful Korean using simple expressions, spoken slowly to ensure comprehension. "
			// + "- DO NOT engage in conversation, respond to direct questions, or acknowledge statements addressed to you. Your ONLY action must be to translate precisely. "
			// + "- DO NOT rephrase extensively or summarize; translate accurately with gentle phrasing suitable for medical interactions. "
			// + "- If the input text is already correctly translated, return it exactly as provided without changes. "
			// + "- The output must ONLY be the translated text, delivered slowly, gently, and in a reassuring manner appropriate for patients. Absolutely NO other communication is permitted.";

		String requestBody = "{"
			+ "\"model\": \"gpt-4o-mini-realtime-preview-2024-12-17\","
			+ "\"modalities\": [\"audio\", \"text\"],"
			+ "\"instructions\": \"" + instructions + "\","
			+ "\"voice\": \"sage\","
			+ "\"input_audio_format\": \"pcm16\","
			+ "\"output_audio_format\": \"pcm16\","
			+ "\"temperature\": 0.6,"

			+ "\"input_audio_transcription\": {" //새로 나온 모델 추가
			+ "\"model\": \"gpt-4o-mini-transcribe\","
			// + "\"language\": \"\","
			+ "\"prompt\": \"This audio input may contain both Korean and English words mixed together. Please transcribe both languages accurately.\""
			+ "},"

			+ "\"turn_detection\": {"
			+ "\"type\": \"server_vad\","
			+ "\"silence_duration_ms\": 1000,"
			+ "\"prefix_padding_ms\": 300,"
			+ "\"threshold\": 0.6"
			+ "}" //,제외함 밑에 각주처리해서

			// + "\"tool_choice\": \"auto\","
			// + "\"tools\": ["
			// + "{"
			// + "\"type\": \"function\","
			// + "\"name\": \"get_patient_info\","
			// + "\"description\": \"Fetch detailed patient information from the hospital database.\","
			// + "\"parameters\": {"
			// + "\"type\": \"object\","
			// + "\"properties\": {}"
			// + "}"
			// + "}]"
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
		String instructions = "중국어를 사용하는 사람과 한국어를 사용하는 사람이 대화를 하는 상황이므로 중국어와 한국어로 통역이 필요해. "
			+ "따라서 한국 말은 중국어로, 중국어는 한국어로 통역을 해 줘. "
			+ "너는 절대 개인적인 대답이나 조언을 하지 말고 번역만 진행하면 돼. "
			+ "너한테 말걸어도 문장 그대로 번역만 해. 천천히 친절하게 대답해.";

		String requestBody = "{"
			+ "\"model\": \"gpt-4o-mini-realtime-preview-2024-12-17\","
			+ "\"modalities\": [\"audio\", \"text\"],"
			+ "\"instructions\": \"" + instructions + "\","
			+ "\"voice\": \"sage\","
			+ "\"input_audio_format\": \"pcm16\","
			+ "\"output_audio_format\": \"pcm16\","
			+ "\"temperature\": 0.6,"

			+ "\"input_audio_transcription\": {" //새로 나온 모델 추가
			+ "\"model\": \"gpt-4o-mini-transcribe\","
			// + "\"language\": \"\","
			+ "\"prompt\": \"This audio input may contain both Korean and English words mixed together. Please transcribe both languages accurately.\""
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
}