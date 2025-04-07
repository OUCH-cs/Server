package com.onebridge.ouch.controller.visitHistory;

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
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryCreateRequest;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryUpdateRequest;
import com.onebridge.ouch.dto.visitHistory.response.DateAndHospital;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryCreateResponse;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryUpdateResponse;
import com.onebridge.ouch.security.userDetail.OuchUserDetails;
import com.onebridge.ouch.service.visitHistory.VisitHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medical-record")
@RequiredArgsConstructor
public class VisitHistoryController {

	private final VisitHistoryService visitHistoryService;

	// 의료기록 생성
	// @PostMapping("/{userId}") //테스트를 위해 주석으로 남겨두겠습니다.
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<VisitHistoryCreateResponse>> createVisitHistory(
		@RequestBody @Valid VisitHistoryCreateRequest request,
		// @PathVariable Long userId
		@AuthenticationPrincipal OuchUserDetails userDetails
	) {
		Long userId = userDetails.getDatabaseId();
		VisitHistoryCreateResponse response = visitHistoryService.createVisitHistory(request, userId);
		return ResponseEntity.ok(ApiResponse.created(response));
	}

	// 특정 의료기록 조회
	@GetMapping("/get/{medicalRecordId}")
	public ResponseEntity<ApiResponse<VisitHistoryUpdateResponse>> getVisitHistory(@PathVariable Long medicalRecordId) {
		VisitHistoryUpdateResponse response = visitHistoryService.getVisitHistory(medicalRecordId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	// 특정 사용자의 모든 의료기록 조회 (의료기록 메인 페이지용)
	// @GetMapping("/get-all/{userId}")
	@GetMapping("/get-all")
	public ResponseEntity<ApiResponse<List<DateAndHospital>>> getUsersAllVisitHistory(
		// @PathVariable Long userId
		@AuthenticationPrincipal OuchUserDetails userDetails
	) {
		Long userId = userDetails.getDatabaseId();
		List<DateAndHospital> list = visitHistoryService.getUsersAllVisitHistory(userId);
		return ResponseEntity.ok(ApiResponse.success(list));
	}

	//특정 의료기록 삭제
	@DeleteMapping("/delete/{medicalRecordId}")
	public ResponseEntity<ApiResponse<Void>> deleteVisitHistory(@PathVariable Long medicalRecordId) {
		visitHistoryService.deleteVisitHistory(medicalRecordId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	//특정 의료기록 수정
	@PutMapping("/update/{medicalRecordId}")
	public ResponseEntity<ApiResponse<VisitHistoryUpdateResponse>> updateVisitHistory(
		@RequestBody @Valid VisitHistoryUpdateRequest request,
		@PathVariable Long medicalRecordId) {
		VisitHistoryUpdateResponse response = visitHistoryService.updateVisitHistory(request, medicalRecordId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
