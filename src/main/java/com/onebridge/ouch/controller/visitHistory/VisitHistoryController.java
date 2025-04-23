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

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryCreateRequest;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryUpdateRequest;
import com.onebridge.ouch.dto.visitHistory.response.DateAndHospital;
import com.onebridge.ouch.dto.visitHistory.response.GetVisitHistoryResponse;
import com.onebridge.ouch.security.authorization.UserId;
import com.onebridge.ouch.service.visitHistory.VisitHistoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "의료기록 API", description = "의료기록 API 입니다.")
@RestController
@RequestMapping("/medical-record")
@RequiredArgsConstructor
public class VisitHistoryController {

	private final VisitHistoryService visitHistoryService;

	// 의료기록 생성
	@Operation(summary = "의료기록 생성 API", description = "의료기록을 생성 API 입니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createVisitHistory(
		@RequestBody @Valid VisitHistoryCreateRequest request,
		@UserId Long userId
	) {
		visitHistoryService.createVisitHistory(request, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	// 특정 의료기록 조회
	@Operation(summary = "의료기록 조회 API", description = "의료기록을 조회 API 입니다.")
	@GetMapping("/{medicalRecordId}")
	public ResponseEntity<ApiResponse<GetVisitHistoryResponse>> getVisitHistory(@PathVariable Long medicalRecordId,
		@UserId Long userId
	) {
		GetVisitHistoryResponse response = visitHistoryService.getVisitHistory(medicalRecordId, userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	// 특정 사용자의 모든 의료기록 조회 (의료기록 메인 페이지용)
	@Operation(summary = "특정 사용자의 모든 의료기록 조회 API", description = "특정 사용자의 모든 의료기록 조회 API 입니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<List<DateAndHospital>>> getUsersAllVisitHistory(
		@UserId Long userId
	) {
		List<DateAndHospital> list = visitHistoryService.getUsersAllVisitHistory(userId);
		return ResponseEntity.ok(ApiResponse.success(list));
	}

	//특정 의료기록 삭제
	@Operation(summary = "의료기록 삭제 API", description = "의료기록을 삭제 API 입니다.")
	@DeleteMapping("/{medicalRecordId}")
	public ResponseEntity<ApiResponse<Void>> deleteVisitHistory(@PathVariable Long medicalRecordId,
		@UserId Long userId
	) {
		visitHistoryService.deleteVisitHistory(medicalRecordId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	//특정 의료기록 수정
	@Operation(summary = "의료기록 수정 API", description = "의료기록을 수정 API 입니다.")
	@PutMapping("/{medicalRecordId}")
	public ResponseEntity<ApiResponse<Void>> updateVisitHistory(
		@RequestBody @Valid VisitHistoryUpdateRequest request,
		@PathVariable Long medicalRecordId,
		@UserId Long userId
	) {
		visitHistoryService.updateVisitHistory(request, medicalRecordId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
