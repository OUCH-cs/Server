package com.onebridge.ouch.controller.Language;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.service.language.LanguageService;
import com.onebridge.ouch.dto.language.response.GetAllLanguagesResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/languages")
@RequiredArgsConstructor
public class LanguageController {

	private final LanguageService languageService;

	@GetMapping // 전체 언어 목록 조회
	public ResponseEntity<ApiResponse<List<GetAllLanguagesResponse>>> getAllLanguages() {
		List<GetAllLanguagesResponse> languages = languageService.getAllLanguages();
		return ResponseEntity.ok(ApiResponse.success(languages));
	}
}
