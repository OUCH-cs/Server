package com.onebridge.ouch.controller.medicalRecord;

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
import com.onebridge.ouch.dto.medicalRecord.request.MedicalRecordCreateRequest;
import com.onebridge.ouch.dto.medicalRecord.request.MedicalRecordUpdateRequest;
import com.onebridge.ouch.dto.medicalRecord.response.DateAndHospital;
import com.onebridge.ouch.dto.medicalRecord.response.GetMedicalRecordResponse;
import com.onebridge.ouch.security.authorization.UserId;
import com.onebridge.ouch.service.medicalRecord.MedicalRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "의료기록 API", description = "의료기록 API 입니다.")
@RestController
@RequestMapping("/medical-record")
@RequiredArgsConstructor
public class MedicalRecordController {

	private final MedicalRecordService medicalRecordService;

	// 의료기록 생성
	@Operation(summary = "의료기록 생성 API", description = "의료기록을 생성 API 입니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createMedicalRecord(
		@RequestBody @Valid MedicalRecordCreateRequest request,
		@UserId Long userId
	) {
		medicalRecordService.createMedicalRecord(request, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	// 특정 의료기록 조회
	@Operation(summary = "의료기록 조회 API", description = "의료기록을 조회 API 입니다.")
	@GetMapping("/{medicalRecordId}")
	public ResponseEntity<ApiResponse<GetMedicalRecordResponse>> getMedicalRecord(@PathVariable Long medicalRecordId,
		@UserId Long userId
	) {
		GetMedicalRecordResponse response = medicalRecordService.getMedicalRecord(medicalRecordId, userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	// 특정 사용자의 모든 의료기록 조회 (의료기록 메인 페이지용)
	@Operation(summary = "특정 사용자의 모든 의료기록 조회 API", description = "특정 사용자의 모든 의료기록 조회 API 입니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<List<DateAndHospital>>> getUsersAllMedicalRecord(
		@UserId Long userId
	) {
		List<DateAndHospital> list = medicalRecordService.getUsersAllMedicalRecord(userId);
		return ResponseEntity.ok(ApiResponse.success(list));
	}

	//특정 의료기록 삭제
	@Operation(summary = "의료기록 삭제 API", description = "의료기록을 삭제 API 입니다.")
	@DeleteMapping("/{medicalRecordId}")
	public ResponseEntity<ApiResponse<Void>> deleteMedicalRecord(@PathVariable Long medicalRecordId,
		@UserId Long userId
	) {
		medicalRecordService.deleteMedicalRecord(medicalRecordId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	//특정 의료기록 수정
	@Operation(summary = "의료기록 수정 API", description = "의료기록을 수정 API 입니다.")
	@PutMapping("/{medicalRecordId}")
	public ResponseEntity<ApiResponse<Void>> updateMedicalRecord(
		@RequestBody @Valid MedicalRecordUpdateRequest request,
		@PathVariable Long medicalRecordId,
		@UserId Long userId
	) {
		medicalRecordService.updateMedicalRecord(request, medicalRecordId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
