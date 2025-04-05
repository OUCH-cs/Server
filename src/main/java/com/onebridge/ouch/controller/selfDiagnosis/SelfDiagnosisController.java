package com.onebridge.ouch.controller.selfDiagnosis;

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
import com.onebridge.ouch.dto.MessageResponse;
import com.onebridge.ouch.dto.selfDiagnosis.request.AddSymptomsToDiagnosisRequest;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisCreateRequest;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisUpdateRequest;
import com.onebridge.ouch.dto.selfDiagnosis.response.DiagnosisCreateResponseDetailed;
import com.onebridge.ouch.dto.selfDiagnosis.response.DiagnosisUpdateResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisByUserIdResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetSymptomsOfDiagnosisResponse;
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
	public DiagnosisCreateResponseDetailed createDiagnosis(@RequestBody @Valid DiagnosisCreateRequest request) {
		return selfDiagnosisService.createDiagnosis(request);
	}

	//(자가진단표)id로 자가진단표 조회
	@GetMapping("/{diagnosisId}")
	public GetDiagnosisResponse getDiagnosisById(@PathVariable Long diagnosisId) {
		return selfDiagnosisService.getDiagnosis(diagnosisId);
	}

	//사용자 모든 자가진단표 조회
	@GetMapping("/get-all/{userId}")
	public List<GetDiagnosisByUserIdResponse> getAllDiagnosisByUserId(@PathVariable Long userId) {
		return selfDiagnosisService.getAllDiagnosisByUserId(userId);
	}

	//자가진단표 삭제
	@DeleteMapping("/{diagnosisId}")
	public ResponseEntity<MessageResponse> deleteDiagnosis(@PathVariable Long diagnosisId) {
		selfDiagnosisService.deleteDiagnosis(diagnosisId);
		return ResponseEntity.ok(new MessageResponse("Diagnosis has been deleted."));
	}

	//특정 자가진단표의 증상 목록 조회
	@GetMapping("/get-symptoms/{diagnosisId}")
	public GetSymptomsOfDiagnosisResponse getSymptomsOfDiagnosis(@PathVariable Long diagnosisId) {
		return selfDiagnosisService.getSymptomsOfDiagnosis(diagnosisId);
	}

	//자가진단표 수정
	@PutMapping("/{userId}/{diagnosisId}")
	public DiagnosisUpdateResponse updateDiagnosis(@PathVariable Long diagnosisId, @PathVariable Long userId,
		@RequestBody @Valid DiagnosisUpdateRequest request) {
		return selfDiagnosisService.updateDiagnosis(diagnosisId, userId, request);
	}

	//자가진단표에 증상 추가
	@PostMapping("/{diagnosisId}/add-symptoms")
	public ResponseEntity<ApiResponse<Void>> addSymptomsToSelfDiagnosis(@PathVariable Long diagnosisId,
		@RequestBody @Valid AddSymptomsToDiagnosisRequest request) {
		selfDiagnosisService.addSymptomsToSelfDiagnosis(diagnosisId, request);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
