package com.onebridge.ouch.service.visitHistory;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.apiPayload.code.error.CommonErrorCode;
import com.onebridge.ouch.apiPayload.code.error.VisitHistoryErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.converter.VisitHistoryConverter;
import com.onebridge.ouch.domain.Summary;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.mapping.VisitHistory;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryCreateRequest;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryUpdateRequest;
import com.onebridge.ouch.dto.visitHistory.response.DateAndHospital;
import com.onebridge.ouch.dto.visitHistory.response.GetVisitHistoryResponse;
import com.onebridge.ouch.repository.department.DepartmentRepository;
import com.onebridge.ouch.repository.hospital.HospitalRepository;
import com.onebridge.ouch.repository.summary.SummaryRepository;
import com.onebridge.ouch.repository.user.UserRepository;
import com.onebridge.ouch.repository.visitHistory.VisitHistoryRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitHistoryService {

	private final UserRepository userRepository;
	private final HospitalRepository hospitalRepository;
	private final DepartmentRepository departmentRepository;
	private final VisitHistoryRepository visitHistoryRepository;
	private final VisitHistoryConverter visitHistoryConverter;
	private final SummaryRepository summaryRepository;

	//의료 기록 생성
	@Transactional
	public void createVisitHistory(VisitHistoryCreateRequest request, Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		// Summary 생성
		Summary summary = Summary.builder()
			.contents_summary(request.getTreatmentSummary())
			.build();

		VisitHistory visitHistory = visitHistoryConverter.visitHistoryCreateRequestToVisitHistory(request, user,
			summary);

		visitHistoryRepository.save(visitHistory);
	}

	//특정 의료기록 조회
	@Transactional
	public GetVisitHistoryResponse getVisitHistory(Long visitHistoryId, Long userId) {
		VisitHistory visitHistory = visitHistoryRepository.findByIdAndUserId(visitHistoryId, userId)
			.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.VISIT_HISTORY_NOT_FOUND));

		return visitHistoryConverter.visitHistoryToGetVisitHistoryResponse(visitHistory);
	}

	//특정 사용자의 모든 의료기록 조회
	@Transactional
	public List<DateAndHospital> getUsersAllVisitHistory(Long userId) {
		List<VisitHistory> visitHistory = visitHistoryRepository.findAllByUserId(userId);
		return visitHistoryConverter.visitHistoryToGetUsersAllVisitHistoryResponse(visitHistory);
	}

	//특정 의료기록 삭제
	@Transactional
	public void deleteVisitHistory(Long visitHistoryId, Long userId) {
		VisitHistory visitHistory = visitHistoryRepository.findByIdAndUserId(visitHistoryId, userId)
			.orElseThrow(
				() -> new OuchException(VisitHistoryErrorCode.VISIT_HISTORY_NOT_FOUND));

		visitHistoryRepository.delete(visitHistory);
	}

	//특정 의료기록 수정
	@Transactional
	public void updateVisitHistory(@Valid VisitHistoryUpdateRequest request,
		Long visitHistoryId, Long userId) {
		VisitHistory visitHistory = visitHistoryRepository.findByIdAndUserId(visitHistoryId, userId)
			.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.VISIT_HISTORY_NOT_FOUND));

		Summary summary = visitHistory.getSummary().toBuilder()
			.contents_summary(request.getTreatmentSummary())
			.build();

		VisitHistory updatedVisitHistory = visitHistoryConverter.visitHistoryUpdateRequestToVisitHistory(visitHistory,
			request,
			summary);

		visitHistoryRepository.save(updatedVisitHistory);
	}

}
