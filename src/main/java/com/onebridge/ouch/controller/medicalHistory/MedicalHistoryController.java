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

import com.onebridge.ouch.dto.MessageResponse;
import com.onebridge.ouch.dto.medicalHistory.request.MedicalHistoryCreateRequest;
import com.onebridge.ouch.dto.medicalHistory.request.MedicalHistoryUpdateRequest;
import com.onebridge.ouch.dto.medicalHistory.response.DateAndDisease;
import com.onebridge.ouch.dto.medicalHistory.response.GetMedicalHistoryResponse;
import com.onebridge.ouch.dto.medicalHistory.response.MedicalHistoryCreateResponse;
import com.onebridge.ouch.dto.medicalHistory.response.MedicalHistoryUpdateResponse;
import com.onebridge.ouch.service.medicalHistory.MedicalHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/health-status")
@RequiredArgsConstructor
public class MedicalHistoryController {

	private final MedicalHistoryService medicalHistoryService;

	//건강상태 생성
	@PostMapping("/{userId}")
	public MedicalHistoryCreateResponse createMedicalHistory(@RequestBody @Valid MedicalHistoryCreateRequest request,
		@PathVariable Long userId) {
		return medicalHistoryService.createMedicalHistory(request, userId);
	}

	//특정 건강상태 조회
	@GetMapping("/{userId}/{healthStatusId}")
	public GetMedicalHistoryResponse getMedicalHistory(@PathVariable Long healthStatusId) {
		return medicalHistoryService.getMedicalHistory(healthStatusId);
	}

	//특정 사용자의 모든 건강상태 조회
	@GetMapping("/get-all/{userId}")
	public List<DateAndDisease> getUsersAllMedicalHistory(@PathVariable Long userId) {
		return medicalHistoryService.getUsersAllMedicalHistory(userId);
	}

	//특정 건강상태 수정
	@PutMapping("/{userId}/{healthStatusId}")
	public MedicalHistoryUpdateResponse updateMedicalHistory(@RequestBody @Valid MedicalHistoryUpdateRequest request,
		@PathVariable Long healthStatusId) {
		return medicalHistoryService.updateMedicalHistory(request, healthStatusId);
	}

	//특정 건강상태 삭제
	@DeleteMapping("/{userId}/{healthStatusId}")
	public ResponseEntity<MessageResponse> deleteMedicalHistory(@PathVariable Long healthStatusId) {
		medicalHistoryService.deleteMedicalHistory(healthStatusId);
		return ResponseEntity.ok(new MessageResponse("The health status has been deleted."));
	}
}
