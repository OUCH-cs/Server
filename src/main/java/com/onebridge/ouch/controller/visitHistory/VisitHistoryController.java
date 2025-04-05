package com.onebridge.ouch.controller.visitHistory;

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
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryCreateRequest;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryUpdateRequest;
import com.onebridge.ouch.dto.visitHistory.response.DateAndHospital;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryCreateResponse;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryUpdateResponse;
import com.onebridge.ouch.service.visitHistory.VisitHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medical-record")
@RequiredArgsConstructor
public class VisitHistoryController {

	private final VisitHistoryService visitHistoryService;

	// 의료기록 생성
	@PostMapping("/{userId}")
	public VisitHistoryCreateResponse createVisitHistory(@RequestBody @Valid VisitHistoryCreateRequest request,
		@PathVariable Long userId) {
		return visitHistoryService.createVisitHistory(request, userId);
	}

	// 특정 의료기록 조회
	@GetMapping("/{userId}/{medicalRecordId}")
	public VisitHistoryUpdateResponse getVisitHistory(@PathVariable Long medicalRecordId) {
		return visitHistoryService.getVisitHistory(medicalRecordId);
	}

	// 특정 사용자의 모든 의료기록 조회 (의료기록 메인 페이지용)
	@GetMapping("/get-all/{userId}")
	public List<DateAndHospital> getUsersAllVisitHistory(@PathVariable Long userId) {
		return visitHistoryService.getUsersAllVisitHistory(userId);
	}

	//특정 의료기록 삭제
	@DeleteMapping("/{userId}/{medicalRecordId}")
	public ResponseEntity<MessageResponse> deleteVisitHistory(@PathVariable Long medicalRecordId) {
		visitHistoryService.deleteVisitHistory(medicalRecordId);
		return ResponseEntity.ok(new MessageResponse("The medical record has been deleted."));
	}

	//특정 의료기록 수정
	@PutMapping("/{userId}/{medicalRecordId}")
	public VisitHistoryUpdateResponse updateVisitHistory(@RequestBody @Valid VisitHistoryUpdateRequest request,
		@PathVariable Long medicalRecordId) {
		return visitHistoryService.updateVisitHistory(request, medicalRecordId);
	}

}
