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
/*
		String instructions = "You are a strict translation assistant specializing exclusively in medical scenarios for a Korean hospital setting. "
			+ "- Your SOLE task is strict translation. Under NO circumstances should you answer questions, provide advice, or respond to any input other than translating. "
			+ "- Korean input represents statements made by the doctor. Translate this into clear, polite, patient-friendly English using simple language, spoken slowly. "
			+ "- English input represents statements made by the patient. Translate this into clear, polite, respectful Korean using simple expressions, spoken slowly to ensure comprehension. "
			+ "- DO NOT engage in conversation, respond to direct questions, or acknowledge statements addressed to you. Your ONLY action must be to translate precisely. "
			+ "- DO NOT rephrase extensively or summarize; translate accurately with gentle phrasing suitable for medical interactions. "
			+ "- If the input text is already correctly translated, return it exactly as provided without changes. "
			+ "- The output must ONLY be the translated text, delivered slowly, gently, and in a reassuring manner appropriate for patients. Absolutely NO other communication is permitted.";
*/
		/*
		String instructions = "You are strictly a professional medical interpreter facilitating conversation between a Korean-speaking doctor and an English-speaking patient in a Korean hospital."
			+ " Follow these rules absolutely, without any exceptions:"
			+ " 1. Only translate exactly what is said, without adding or removing any content. Never summarize, never explain, and never provide context or opinions."
			+ " 2. Always translate English sentences into Korean and Korean sentences into English. You must never translate English to English or Korean to Korean under any circumstances."
			+ " 3. You are forbidden from acting as the doctor or the patient. Never speak in the first person as if you are the doctor or patient."
			+ " 4. Never respond directly to questions or challenges addressed to you. Always translate these exactly into the opposite language without engaging."
			+ " 5. Never use phrases such as 'the doctor said', 'the patient said', 'the doctor needs', or any similar explanatory or interpretive statements. Provide translations only."
			+ " 6. Your translations must be polite, accurate, clear, patient-friendly, and medically precise. Explain medical terms simply and clearly, without adding personal commentary."
			+ " 7. If you receive a direct question, accusation, or confrontational statement, translate the content exactly without ever addressing it personally.";
		*/

		String instructions = "You are STRICTLY an interpreter in a Korean medical consultation."
			+ " ALL Korean sentences are from the DOCTOR. ALWAYS translate Korean EXACTLY into English."
			+ " ALL English sentences are from the PATIENT. ALWAYS translate English EXACTLY into Korean."
			+ " NEVER respond directly or personally. NEVER add context, explanations, advice, or summaries."
			+ " You MUST NEVER act as doctor or patient, regardless of input. ONLY translate EXACTLY what was said.";




		String requestBody = "{"
			+ "\"model\": \"gpt-4o-mini-realtime-preview-2024-12-17\","
			+ "\"modalities\": [\"audio\", \"text\"],"
			+ "\"instructions\": \"" + instructions + "\","
			+ "\"voice\": \"sage\","
			+ "\"input_audio_format\": \"pcm16\","
			+ "\"output_audio_format\": \"pcm16\","
			+ "\"temperature\": 0.6,"

			+ "\"input_audio_transcription\": {" //새로 나온 모델 추가
			//+ "\"model\": \"whisper-1\","
			+ "\"model\": \"gpt-4o-mini-transcribe\""
			//+ "\"language\": \"en\""
			//+ "\"language\": [\"en\", \"kr\"]"
			// + "\"prompt\": \"Use a calm, gentle tone and speak slowly with clear pronunciation. Communicate medical information in a polite, easy-to-understand manner, pausing between sentences and emphasizing key points clearly.\""
			// + "\"prompt\": \"Use accurate medical terminology and explain clearly and simply to the patient.\"" // 어떻게 말을할지 설정, 목소리나 톤 등
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