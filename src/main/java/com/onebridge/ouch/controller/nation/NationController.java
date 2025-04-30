package com.onebridge.ouch.controller.nation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.dto.nation.response.GetAllNationsResponse;
import com.onebridge.ouch.service.nation.NationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "국적 관련 API", description = "국적 관련 API 입니다.")
@RestController
@RequestMapping("/nations")
@RequiredArgsConstructor
public class NationController {

	private final NationService nationService;

	@Operation(summary = "국가 목록 조회 API", description = "데이터베이스 내 모든 국가 목록을 조회합니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<List<GetAllNationsResponse>>> getAllNations() {
		List<GetAllNationsResponse> nations = nationService.getAllNations();
		return ResponseEntity.ok(ApiResponse.success(nations));
	}
}
