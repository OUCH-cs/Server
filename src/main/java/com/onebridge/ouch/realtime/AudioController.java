package com.onebridge.ouch.realtime;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
public class AudioController {

	@Value("${openai.api-key}")
	private String apiKey;

	private final OkHttpClient client = new OkHttpClient();

	@PostMapping(value = "/translate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<byte[]> translateAudio(@RequestParam("audio") MultipartFile audioFile) throws IOException {
		// 1. STT (Whisper)
		String transcription = callWhisperSTT(audioFile);

		// 2. GPT-4o (Translation)
		String translation = callGPT4oTranslation(transcription);

		// 3. TTS (OpenAI TTS)
		byte[] audioData = callOpenAITTS(translation);

		return ResponseEntity.ok()
			.contentType(MediaType.parseMediaType("audio/mpeg"))
			.body(audioData);
	}

	private String callWhisperSTT(MultipartFile audioFile) throws IOException {
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
			.addFormDataPart("file", audioFile.getOriginalFilename(),
				RequestBody.create(audioFile.getBytes(), MediaType.parse("audio/wav")))
			.addFormDataPart("model", "whisper-1")
			.build();

		Request request = new Request.Builder()
			.url("https://api.openai.com/v1/audio/transcriptions")
			.header("Authorization", "Bearer " + apiKey)
			.post(body)
			.build();

		Response response = client.newCall(request).execute();
		String json = response.body().string();

		return new ObjectMapper().readTree(json).get("text").asText();
	}

	private String callGPT4oTranslation(String text) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		String jsonBody = "{"
			+ "\"model\":\"gpt-4o\","
			+ "\"messages\":["
			+ "{\"role\":\"system\",\"content\":\"Translate between English and Korean exactly without explanations.\"},"
			+ "{\"role\":\"user\",\"content\":\"" + text + "\"}"
			+ "],"
			+ "\"temperature\":0"
			+ "}";

		Request request = new Request.Builder()
			.url("https://api.openai.com/v1/chat/completions")
			.header("Authorization", "Bearer " + apiKey)
			.post(RequestBody.create(jsonBody, mediaType))
			.build();

		Response response = client.newCall(request).execute();
		String json = response.body().string();

		return new ObjectMapper().readTree(json)
			.get("choices").get(0).get("message").get("content").asText();
	}

	private byte[] callOpenAITTS(String text) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		String jsonBody = "{"
			+ "\"model\":\"tts-1\","
			+ "\"input\":\"" + text + "\","
			+ "\"voice\":\"alloy\","
			+ "\"response_format\":\"mp3\""
			+ "}";

		Request request = new Request.Builder()
			.url("https://api.openai.com/v1/audio/speech")
			.header("Authorization", "Bearer " + apiKey)
			.post(RequestBody.create(jsonBody, mediaType))
			.build();

		Response response = client.newCall(request).execute();
		return response.body().bytes();
	}
}
