package com.onebridge.ouch.controller.language;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.service.language.LanguageService;
import com.onebridge.ouch.dto.language.response.GetAllLanguagesResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "언어 관련 API", description = "언어 관련 API 입니다.")
@RestController
@RequestMapping("/languages")
@RequiredArgsConstructor
public class LanguageController {

	private final LanguageService languageService;

	@Operation(summary = "언어 목록 조회 API", description = "데이터베이스 내 모든 언어 목록을 조회합니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<List<GetAllLanguagesResponse>>> getAllLanguages() {
		List<GetAllLanguagesResponse> languages = languageService.getAllLanguages();
		return ResponseEntity.ok(ApiResponse.success(languages));
	}
}
