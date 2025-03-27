package com.hy.ouch.controller.visitHistory;

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
import com.hy.ouch.dto.visitHistory.request.VisitHistoryCreateRequest;
import com.hy.ouch.dto.visitHistory.request.VisitHistoryUpdateRequest;
import com.hy.ouch.dto.visitHistory.response.DateAndHospital;
import com.hy.ouch.dto.visitHistory.response.VisitHistoryCreateResponse;
import com.hy.ouch.dto.visitHistory.response.VisitHistoryUpdateResponse;
import com.hy.ouch.service.visitHistory.VisitHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/visit-history")
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
	@GetMapping("/{userId}/{visitHistoryId}")
	public VisitHistoryUpdateResponse getVisitHistory(@PathVariable Long visitHistoryId) {
		return visitHistoryService.getVisitHistory(visitHistoryId);
	}

	// 특정 사용자의 모든 의료기록 조회 (의료기록 메인 페이지용)
	@GetMapping("/get-all/{userId}")
	public List<DateAndHospital> getUsersAllVisitHistory(@PathVariable Long userId) {
		return visitHistoryService.getUsersAllVisitHistory(userId);
	}

	//특정 의료기록 삭제
	@DeleteMapping("/{userId}/{visitHistoryId}")
	public ResponseEntity<MessageResponse> deleteVisitHistory(@PathVariable Long visitHistoryId) {
		visitHistoryService.deleteVisitHistory(visitHistoryId);
		return ResponseEntity.ok(new MessageResponse("The visit history has been deleted."));
	}

	//특정 의료기록 수정
	@PutMapping("/{userId}/{visitHistoryId}")
	public VisitHistoryUpdateResponse updateVisitHistory(@RequestBody @Valid VisitHistoryUpdateRequest request,
		@PathVariable Long visitHistoryId) {
		return visitHistoryService.updateVisitHistory(request, visitHistoryId);
	}

}
