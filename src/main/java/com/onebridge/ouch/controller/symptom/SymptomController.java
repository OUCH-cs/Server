package com.onebridge.ouch.controller.symptom;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.dto.symptom.response.GetSymptomResponse;
import com.onebridge.ouch.service.symptom.SymptomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "증상 API", description = "증상 API 입니다.")
@RestController
@RequestMapping("/symptoms")
@RequiredArgsConstructor
public class SymptomController {

	private final SymptomService symptomService;

	//증상 목록 조회
	@Operation(summary = "증상 목록 조회 API", description = "증상 목록 조회 API 입니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<List<GetSymptomResponse>>> getSymptomsList() {
		List<GetSymptomResponse> list = symptomService.getSymptomsList();
		return ResponseEntity.ok(ApiResponse.success(list));
	}
}
