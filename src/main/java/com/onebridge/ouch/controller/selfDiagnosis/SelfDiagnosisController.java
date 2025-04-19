package com.onebridge.ouch.controller.selfDiagnosis;

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
	public ResponseEntity<ApiResponse<DiagnosisCreateResponseDetailed>> createDiagnosis( //request dto 에 userid 지우기
		@AuthenticationPrincipal OuchUserDetails userDetails,
		@RequestBody @Valid DiagnosisCreateRequest request
	) {
		Long userId = userDetails.getDatabaseId();
		// Long userId = request.getUserId(); //userId 로 바꾸기
		DiagnosisCreateResponseDetailed response = selfDiagnosisService.createDiagnosis(request, userId);
		return ResponseEntity.ok(ApiResponse.created(response));
	}

	//(자가진단표)id로 자가진단표 조회
	@GetMapping("/{diagnosisId}")
	// @GetMapping("/{diagnosisId}/{userId}")
	public ResponseEntity<ApiResponse<GetDiagnosisResponse>> getDiagnosisById(
		@AuthenticationPrincipal OuchUserDetails userDetails,
		@PathVariable Long diagnosisId
		// @PathVariable Long userId
	) {
		Long userId = userDetails.getDatabaseId();
		GetDiagnosisResponse response = selfDiagnosisService.getDiagnosis(diagnosisId, userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	// //사용자 모든 자가진단표 조회
	// @GetMapping("/{userId}") //테스트를 위해 주석으로 남겨두겠습니다.
	// // @GetMapping
	// public ResponseEntity<ApiResponse<List<GetDiagnosisByUserIdResponse>>> getAllDiagnosisByUserId(
	// 	// @AuthenticationPrincipal OuchUserDetails userDetails
	// 	@PathVariable Long userId
	// ) {
	// 	// Long userId = userDetails.getDatabaseId();
	// 	List<GetDiagnosisByUserIdResponse> list = selfDiagnosisService.getAllDiagnosisByUserId(userId);
	// 	return ResponseEntity.ok(ApiResponse.success(list));
	// }

	//자가진단표 삭제
	// @DeleteMapping("/{diagnosisId}")
	@DeleteMapping("/{diagnosisId}/{userId}")
	public ResponseEntity<ApiResponse<Void>> deleteDiagnosis(@PathVariable Long diagnosisId,
		// @AuthenticationPrincipal OuchUserDetails userDetails,
		@PathVariable Long userId
	) {
		// Long userId = userDetails.getDatabaseId();
		selfDiagnosisService.deleteDiagnosis(diagnosisId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	//특정 자가진단표의 증상 목록 조회
	// @GetMapping("/{diagnosisId}/symptoms")
	@GetMapping("/{diagnosisId}/symptoms/{userId}")
	public ResponseEntity<ApiResponse<GetSymptomsOfDiagnosisResponse>> getSymptomsOfDiagnosis(
		@PathVariable Long diagnosisId,
		// @AuthenticationPrincipal OuchUserDetails userDetails,
		@PathVariable Long userId
	) {
		// Long userId = userDetails.getDatabaseId();
		GetSymptomsOfDiagnosisResponse response = selfDiagnosisService.getSymptomsOfDiagnosis(diagnosisId, userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	//자가진단표 수정
	@PutMapping("/{diagnosisId}/{userId}") //테스트를 위해 주석으로 남겨두겠습니다.
	// @PutMapping("/{diagnosisId}")
	public ResponseEntity<ApiResponse<DiagnosisUpdateResponse>> updateDiagnosis(@PathVariable Long diagnosisId,
		@PathVariable Long userId,
		// @AuthenticationPrincipal OuchUserDetails userDetails,
		@RequestBody @Valid DiagnosisUpdateRequest request) {
		// Long userId = userDetails.getDatabaseId();
		DiagnosisUpdateResponse response = selfDiagnosisService.updateDiagnosis(diagnosisId, userId, request);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	//자가진단표에 증상 추가
	// @PostMapping("/{diagnosisId}/symptoms")
	@PostMapping("/{diagnosisId}/symptoms/{userId}")
	public ResponseEntity<ApiResponse<Void>> addSymptomsToSelfDiagnosis(@PathVariable Long diagnosisId,
		@RequestBody @Valid AddSymptomsToDiagnosisRequest request,
		// @AuthenticationPrincipal OuchUserDetails userDetails,
		@PathVariable Long userId
	) {
		// Long userId = userDetails.getDatabaseId();
		selfDiagnosisService.addSymptomsToSelfDiagnosis(diagnosisId, request, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
