package com.hy.ouch.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.hy.ouch.apiPayload.ApiResponse;
import com.hy.ouch.domain.Language;
import com.hy.ouch.service.LanguageService;
import com.hy.ouch.web.dto.LanguageDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {

	private final LanguageService languageService;

	@PostMapping
	public ResponseEntity<ApiResponse<Long>> addLanguage(@Valid @RequestBody LanguageDto languageDto) {
		Long languageId = languageService.addLanguage(languageDto);
		return ResponseEntity.ok(ApiResponse.success(languageId));
	}

	@PatchMapping("/{languageId}")
	public ResponseEntity<ApiResponse<Void>> updateLanguage(@PathVariable("languageId") Long id,
		@RequestBody LanguageDto languageDto) {
		languageService.updateLanguage(id, languageDto);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@DeleteMapping("/{languageId}")
	public ResponseEntity<ApiResponse<Void>> deleteLanguage(@PathVariable("languageId") Long id) {
		languageService.deleteLanguage(id);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@GetMapping // 전체 언어 목록 조회
	public ResponseEntity<ApiResponse<List<LanguageDto>>> getAllLanguages() {
		List<LanguageDto> languages = languageService.getAllLanguages();
		return ResponseEntity.ok(ApiResponse.success(languages));
	}
}
