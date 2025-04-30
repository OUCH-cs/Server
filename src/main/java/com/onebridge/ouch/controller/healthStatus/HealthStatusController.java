package com.onebridge.ouch.controller.healthStatus;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.dto.healthStatus.request.HealthStatusCreateRequest;
import com.onebridge.ouch.dto.healthStatus.request.HealthStatusUpdateRequest;
import com.onebridge.ouch.dto.healthStatus.response.DateAndDisease;
import com.onebridge.ouch.dto.healthStatus.response.GetHealthStatusResponse;
import com.onebridge.ouch.security.authorization.UserId;
import com.onebridge.ouch.service.healthStatus.HealthStatusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "건강 상태 API", description = "건강 상태 API")
@RestController
@RequestMapping("/health-status")
@RequiredArgsConstructor
public class HealthStatusController {

	private final HealthStatusService healthStatusService;

	//건강상태 생성
	@Operation(summary = "건강상태 생성 API", description = "건강상태 생성 API 입니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createHealthStatus(
		@RequestBody @Valid HealthStatusCreateRequest request,
		@UserId Long userId
	) {
		healthStatusService.createHealthStatus(request, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	//특정 건강상태 조회
	@Operation(summary = "건강상태 조회 API", description = "건강상태 조회 API 입니다.")
	@GetMapping("/{healthStatusId}")
	public ResponseEntity<ApiResponse<GetHealthStatusResponse>> getHealthStatus(@PathVariable Long healthStatusId,
		@UserId Long userId
	) {
		GetHealthStatusResponse response = healthStatusService.getHealthStatus(healthStatusId, userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	/*
	//특정 사용자의 모든 건강상태 조회
	@Operation(summary = "특정 사용자의 모든 건강상태 조회 API", description = "특정 사용자의 모든 건강상태 조회 API 입니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<List<DateAndDisease>>> getUsersAllHealthStatus(
		@UserId Long userId
	) {
		List<DateAndDisease> list = healthStatusService.getUsersAllHealthStatus(userId);
		return ResponseEntity.ok(ApiResponse.success(list));
	}
	*/ // 건강 상태 한 개만 보유

	//특정 건강상태 수정
	@Operation(summary = "건강상태 수정 API", description = "건강상태 수정 API 입니다.")
	@PutMapping("/{healthStatusId}")
	public ResponseEntity<ApiResponse<Void>> updateHealthStatus(
		@RequestBody @Valid HealthStatusUpdateRequest request,
		@PathVariable Long healthStatusId,
		@UserId Long userId
	) {
		healthStatusService.updateHealthStatus(request, healthStatusId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	//특정 건강상태 삭제
	@Operation(summary = "건강상태 삭제 API", description = "건강상태 삭제 API 입니다.")
	@DeleteMapping("/{healthStatusId}")
	public ResponseEntity<ApiResponse<Void>> deleteHealthStatus(@PathVariable Long healthStatusId,
		@UserId Long userId
	) {
		healthStatusService.deleteHealthStatus(healthStatusId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
