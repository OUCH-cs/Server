package com.hy.ouch.controller.selfDiagnosis;

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

import com.hy.ouch.dto.MessageResponse;
import com.hy.ouch.dto.selfDiagnosis.request.AddSymptomsToDiagnosisRequest;
import com.hy.ouch.dto.selfDiagnosis.request.DiagnosisCreateRequest;
import com.hy.ouch.dto.selfDiagnosis.request.DiagnosisUpdateRequest;
import com.hy.ouch.dto.selfDiagnosis.response.DiagnosisCreateResponse;
import com.hy.ouch.dto.selfDiagnosis.response.GetDiagnosisByUserIdResponse;
import com.hy.ouch.dto.selfDiagnosis.response.GetDiagnosisResponse;
import com.hy.ouch.dto.selfDiagnosis.response.GetSymptomsOfDiagnosisResponse;
import com.hy.ouch.service.selfDiagnosis.SelfDiagnosisService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/self-diagnosis")
@RequiredArgsConstructor
public class SelfDiagnosisController {

	private final SelfDiagnosisService selfDiagnosisService;

	//자가진단표 생성
	@PostMapping
	public DiagnosisCreateResponse createDiagnosis(@RequestBody @Valid DiagnosisCreateRequest request) {
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
	@PutMapping("/{diagnosisId}")
	public ResponseEntity<MessageResponse> updateDiagnosis(@PathVariable Long diagnosisId,
		@RequestBody @Valid DiagnosisUpdateRequest request) {
		selfDiagnosisService.updateDiagnosis(diagnosisId, request);
		return ResponseEntity.ok(new MessageResponse("Diagnosis has been updated."));
	}

	//자가진단표에 증상 추가
	@PostMapping("/{diagnosisId}/add-symptoms")
	public ResponseEntity<MessageResponse> addSymptomsToSelfDiagnosis(@PathVariable Long diagnosisId,
		@RequestBody @Valid AddSymptomsToDiagnosisRequest request) {
		selfDiagnosisService.addSymptomsToSelfDiagnosis(diagnosisId, request);
		return ResponseEntity.ok(new MessageResponse("Symptoms have been added."));
	}
}
