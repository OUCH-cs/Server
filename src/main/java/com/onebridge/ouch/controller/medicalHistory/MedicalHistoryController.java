package com.onebridge.ouch.controller.medicalHistory;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.onebridge.ouch.dto.medicalHistory.response.MedicalHistoryCreateResponse;
import com.onebridge.ouch.dto.medicalHistory.response.MedicalHistoryUpdateResponse;
import com.onebridge.ouch.security.userDetail.OuchUserDetails;
import com.onebridge.ouch.service.medicalHistory.MedicalHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/health-status")
@RequiredArgsConstructor
public class MedicalHistoryController {

	private final MedicalHistoryService medicalHistoryService;

	//건강상태 생성
	@PostMapping
	public ResponseEntity<ApiResponse<MedicalHistoryCreateResponse>> createMedicalHistory(
		@RequestBody @Valid MedicalHistoryCreateRequest request,
		@AuthenticationPrincipal OuchUserDetails userDetails
	) {
		Long userId = userDetails.getDatabaseId();
		MedicalHistoryCreateResponse response = medicalHistoryService.createMedicalHistory(request, userId);
		return ResponseEntity.ok(ApiResponse.created(response));
	}

	//특정 건강상태 조회
	@GetMapping("/{healthStatusId}")
	public ResponseEntity<ApiResponse<GetMedicalHistoryResponse>> getMedicalHistory(@PathVariable Long healthStatusId,
		@AuthenticationPrincipal OuchUserDetails userDetails
	) {
		Long userId = userDetails.getDatabaseId();
		GetMedicalHistoryResponse response = medicalHistoryService.getMedicalHistory(healthStatusId, userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	//특정 사용자의 모든 건강상태 조회
	@GetMapping
	public ResponseEntity<ApiResponse<List<DateAndDisease>>> getUsersAllMedicalHistory(
		@AuthenticationPrincipal OuchUserDetails userDetails
	) {
		Long userId = userDetails.getDatabaseId();
		List<DateAndDisease> list = medicalHistoryService.getUsersAllMedicalHistory(userId);
		return ResponseEntity.ok(ApiResponse.success(list));
	}

	//특정 건강상태 수정
	@PutMapping("/{healthStatusId}")
	public ResponseEntity<ApiResponse<MedicalHistoryUpdateResponse>> updateMedicalHistory(
		@RequestBody @Valid MedicalHistoryUpdateRequest request,
		@PathVariable Long healthStatusId,
		@AuthenticationPrincipal OuchUserDetails userDetails
	) {
		Long userId = userDetails.getDatabaseId();
		MedicalHistoryUpdateResponse response = medicalHistoryService.updateMedicalHistory(request, healthStatusId,
			userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	//특정 건강상태 삭제
	@DeleteMapping("/{healthStatusId}")
	public ResponseEntity<ApiResponse<Void>> deleteMedicalHistory(@PathVariable Long healthStatusId,
		@AuthenticationPrincipal OuchUserDetails userDetails
	) {
		Long userId = userDetails.getDatabaseId();
		medicalHistoryService.deleteMedicalHistory(healthStatusId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
