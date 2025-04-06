package com.onebridge.ouch.controller.symptom;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.dto.symptom.response.GetSymptomResponse;
import com.onebridge.ouch.service.symptom.SymptomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/symptoms")
@RequiredArgsConstructor
public class SymptomController {

	private final SymptomService symptomService;

	//증상 목록 조회
	@GetMapping
	public ResponseEntity<ApiResponse<List<GetSymptomResponse>>> getSymptomsList() {
		List<GetSymptomResponse> list = symptomService.getSymptomsList();
		return ResponseEntity.ok(ApiResponse.success(list));
	}
}
