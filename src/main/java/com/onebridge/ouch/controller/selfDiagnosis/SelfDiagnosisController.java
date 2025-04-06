package com.onebridge.ouch.controller.selfDiagnosis;

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
import com.onebridge.ouch.dto.selfDiagnosis.request.AddSymptomsToDiagnosisRequest;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisCreateRequest;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisUpdateRequest;
import com.onebridge.ouch.dto.selfDiagnosis.response.DiagnosisCreateResponseDetailed;
import com.onebridge.ouch.dto.selfDiagnosis.response.DiagnosisUpdateResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisByUserIdResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetSymptomsOfDiagnosisResponse;
import com.onebridge.ouch.security.userDetail.OuchUserDetails;
import com.onebridge.ouch.service.selfDiagnosis.SelfDiagnosisService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/self-diagnosis")
@RequiredArgsConstructor
public class SelfDiagnosisController {

	private final SelfDiagnosisService selfDiagnosisService;

	//자가진단표 생성
	@PostMapping
	public ResponseEntity<ApiResponse<DiagnosisCreateResponseDetailed>> createDiagnosis(
		@RequestBody @Valid DiagnosisCreateRequest request) {
		DiagnosisCreateResponseDetailed response = selfDiagnosisService.createDiagnosis(request);
		return ResponseEntity.ok(ApiResponse.created(response));
	}

	//(자가진단표)id로 자가진단표 조회
	@GetMapping("/{diagnosisId}")
	public ResponseEntity<ApiResponse<GetDiagnosisResponse>> getDiagnosisById(@PathVariable Long diagnosisId) {
		GetDiagnosisResponse response = selfDiagnosisService.getDiagnosis(diagnosisId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	//사용자 모든 자가진단표 조회
	// @GetMapping("/get-all/{userId}") //테스트를 위해 주석으로 남겨두겠습니다.
	@GetMapping("/get-all")
	public ResponseEntity<ApiResponse<List<GetDiagnosisByUserIdResponse>>> getAllDiagnosisByUserId(
		@AuthenticationPrincipal OuchUserDetails userDetails
		// @PathVariable Long userId
	) {
		Long userId = userDetails.getDatabaseId();
		List<GetDiagnosisByUserIdResponse> list = selfDiagnosisService.getAllDiagnosisByUserId(userId);
		return ResponseEntity.ok(ApiResponse.success(list));
	}

	//자가진단표 삭제
	@DeleteMapping("/{diagnosisId}")
	public ResponseEntity<ApiResponse<Void>> deleteDiagnosis(@PathVariable Long diagnosisId) {
		selfDiagnosisService.deleteDiagnosis(diagnosisId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	//특정 자가진단표의 증상 목록 조회
	@GetMapping("/get-symptoms/{diagnosisId}")
	public ResponseEntity<ApiResponse<GetSymptomsOfDiagnosisResponse>> getSymptomsOfDiagnosis(
		@PathVariable Long diagnosisId) {
		GetSymptomsOfDiagnosisResponse response = selfDiagnosisService.getSymptomsOfDiagnosis(diagnosisId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	//자가진단표 수정
	// @PutMapping("/{userId}/{diagnosisId}") //테스트를 위해 주석으로 남겨두겠습니다.
	@PutMapping("/{diagnosisId}")
	public ResponseEntity<ApiResponse<DiagnosisUpdateResponse>> updateDiagnosis(@PathVariable Long diagnosisId,
		// @PathVariable Long userId,
		@AuthenticationPrincipal OuchUserDetails userDetails,
		@RequestBody @Valid DiagnosisUpdateRequest request) {
		Long userId = userDetails.getDatabaseId();
		DiagnosisUpdateResponse response = selfDiagnosisService.updateDiagnosis(diagnosisId, userId, request);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	//자가진단표에 증상 추가
	@PostMapping("/{diagnosisId}/add-symptoms")
	public ResponseEntity<ApiResponse<Void>> addSymptomsToSelfDiagnosis(@PathVariable Long diagnosisId,
		@RequestBody @Valid AddSymptomsToDiagnosisRequest request) {
		selfDiagnosisService.addSymptomsToSelfDiagnosis(diagnosisId, request);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
