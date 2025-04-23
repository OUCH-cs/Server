package com.onebridge.ouch.controller.medicalHistory;

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
import com.onebridge.ouch.dto.medicalHistory.request.MedicalHistoryCreateRequest;
import com.onebridge.ouch.dto.medicalHistory.request.MedicalHistoryUpdateRequest;
import com.onebridge.ouch.dto.medicalHistory.response.DateAndDisease;
import com.onebridge.ouch.dto.medicalHistory.response.GetMedicalHistoryResponse;
import com.onebridge.ouch.security.authorization.UserId;
import com.onebridge.ouch.service.medicalHistory.MedicalHistoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "건강 상태 API", description = "건강 상태 API")
@RestController
@RequestMapping("/health-status")
@RequiredArgsConstructor
public class MedicalHistoryController {

	private final MedicalHistoryService medicalHistoryService;

	//건강상태 생성
	@Operation(summary = "건강상태 생성 API", description = "건강상태 생성 API 입니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createMedicalHistory(
		@RequestBody @Valid MedicalHistoryCreateRequest request,
		@UserId Long userId
	) {
		medicalHistoryService.createMedicalHistory(request, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	//특정 건강상태 조회
	@Operation(summary = "건강상태 조회 API", description = "건강상태 조회 API 입니다.")
	@GetMapping("/{healthStatusId}")
	public ResponseEntity<ApiResponse<GetMedicalHistoryResponse>> getMedicalHistory(@PathVariable Long healthStatusId,
		@UserId Long userId
	) {
		GetMedicalHistoryResponse response = medicalHistoryService.getMedicalHistory(healthStatusId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	//특정 사용자의 모든 건강상태 조회
	@Operation(summary = "특정 사용자의 모든 건강상태 조회 API", description = "특정 사용자의 모든 건강상태 조회 API 입니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<List<DateAndDisease>>> getUsersAllMedicalHistory(
		@UserId Long userId
	) {
		List<DateAndDisease> list = medicalHistoryService.getUsersAllMedicalHistory(userId);
		return ResponseEntity.ok(ApiResponse.success(list));
	}

	//특정 건강상태 수정
	@Operation(summary = "건강상태 수정 API", description = "건강상태 수정 API 입니다.")
	@PutMapping("/{healthStatusId}")
	public ResponseEntity<ApiResponse<Void>> updateMedicalHistory(
		@RequestBody @Valid MedicalHistoryUpdateRequest request,
		@PathVariable Long healthStatusId,
		@UserId Long userId
	) {
		medicalHistoryService.updateMedicalHistory(request, healthStatusId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	//특정 건강상태 삭제
	@Operation(summary = "건강상태 삭제 API", description = "건강상태 삭제 API 입니다.")
	@DeleteMapping("/{healthStatusId}")
	public ResponseEntity<ApiResponse<Void>> deleteMedicalHistory(@PathVariable Long healthStatusId,
		@UserId Long userId
	) {
		medicalHistoryService.deleteMedicalHistory(healthStatusId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
