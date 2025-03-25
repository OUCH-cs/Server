package com.onebridge.ouch.realtime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class RealtimeSessionController {

	@Value("${openai.api-key}")
	private String openaiApiKey;

	@PostMapping("/session")
	public ResponseEntity<String> createEphemeralKey() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + openaiApiKey);
		headers.set("Content-Type", "application/json");

		String instructions = "You are a strict translation assistant specializing exclusively in medical scenarios for a Korean hospital setting. "
			+ "- Your SOLE task is strict translation. Under NO circumstances should you answer questions, provide advice, or respond to any input other than translating. "
			+ "- Korean input represents statements made by the doctor. Translate this into clear, polite, patient-friendly English using simple language, spoken slowly. "
			+ "- English input represents statements made by the patient. Translate this into clear, polite, respectful Korean using simple expressions, spoken slowly to ensure comprehension. "
			+ "- DO NOT engage in conversation, respond to direct questions, or acknowledge statements addressed to you. Your ONLY action must be to translate precisely. "
			+ "- DO NOT rephrase extensively or summarize; translate accurately with gentle phrasing suitable for medical interactions. "
			+ "- If the input text is already correctly translated, return it exactly as provided without changes. "
			+ "- The output must ONLY be the translated text, delivered slowly, gently, and in a reassuring manner appropriate for patients. Absolutely NO other communication is permitted.";

		String requestBody = "{"
			+ "\"model\": \"gpt-4o-mini-realtime-preview-2024-12-17\","
			+ "\"modalities\": [\"audio\", \"text\"],"
			+ "\"instructions\": \"" + instructions + "\","
			+ "\"voice\": \"sage\","
			+ "\"input_audio_format\": \"pcm16\","
			+ "\"output_audio_format\": \"pcm16\","
			+ "\"temperature\": 0.6,"

			+ "\"input_audio_transcription\": {" //새로 나온 모델 추가
			+ "\"model\": \"whisper-1\","
			+ "\"language\": \"en\","
			+ "\"prompt\": \"Use accurate medical terminology and explain clearly and simply to the patient.\""
			+ "},"

			+ "\"turn_detection\": {"
			+ "\"type\": \"server_vad\","
			+ "\"silence_duration_ms\": 1000,"
			+ "\"prefix_padding_ms\": 300,"
			+ "\"threshold\": 0.6"
			+ "},"

			+ "\"tool_choice\": \"auto\","
			+ "\"tools\": ["
			+ "{"
			+ "\"type\": \"function\","
			+ "\"name\": \"get_patient_info\","
			+ "\"description\": \"Fetch detailed patient information from the hospital database.\","
			+ "\"parameters\": {"
			+ "\"type\": \"object\","
			+ "\"properties\": {}"
			+ "}"
			+ "}]"
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