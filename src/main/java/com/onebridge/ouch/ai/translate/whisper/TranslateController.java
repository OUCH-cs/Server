// package com.onebridge.ouch.ai.translate.whisper;
//
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.MediaType;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;
// import jakarta.servlet.http.HttpServletResponse;
//
// @RestController
// @RequiredArgsConstructor
// @RequestMapping("/api/translate")
// public class TranslateController {
//     private final OpenAiService openAiService;
//
//     @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//     public void translate(@RequestPart MultipartFile audio, HttpServletResponse response) throws Exception {
//         // 1. Whisper+GPT+TTS
//         byte[] translatedAudio = openAiService.processAudio(audio);
//
//         // 2. 결과 전송 (audio/ogg 또는 audio/webm, audio/mp3 등)
//         response.setContentType("audio/ogg"); // TTS 반환 포맷에 맞게!
//         response.getOutputStream().write(translatedAudio);
//     }
// }
