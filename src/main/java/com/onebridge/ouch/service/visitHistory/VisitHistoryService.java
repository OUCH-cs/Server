package com.onebridge.ouch.service.visitHistory;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.apiPayload.code.error.VisitHistoryErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.converter.VisitHistoryConverter;
import com.onebridge.ouch.domain.Department;
import com.onebridge.ouch.domain.Hospital;
import com.onebridge.ouch.domain.Summary;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.mapping.VisitHistory;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryCreateRequest;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryUpdateRequest;
import com.onebridge.ouch.dto.visitHistory.response.DateAndHospital;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryCreateResponse;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryUpdateResponse;
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
	public VisitHistoryCreateResponse createVisitHistory(VisitHistoryCreateRequest request, Long userId) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.USER_NOT_FOUND));

		Hospital hospital = hospitalRepository.findByName(request.getVisitingHospital())
			.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.HOSPITAL_NOT_FOUND));

		Department department = departmentRepository.findByName(request.getMedicalSubject())
			.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.DEPARTMENT_NOT_FOUND));

		// 1. VisitHistory 먼저 저장
		VisitHistory visitHistory = visitHistoryConverter.visitHistoryCreateRequestToVisitHistory(request, user,
			hospital, department);

		// 2. Summary 생성
		Summary summary = visitHistoryConverter.visitHistoryCreateRequestToSummary(request, visitHistory);

		summary = summaryRepository.save(summary); // Summary 저장

		visitHistory.assignSummary(summary);

		visitHistoryRepository.save(visitHistory); // 다시 저장 (연관관계 반영)

		return visitHistoryConverter.visitHistoryToVisitHistoryCreateResponse(visitHistory);
	}

	//특정 의료기록 조회
	@Transactional
	public VisitHistoryUpdateResponse getVisitHistory(Long visitHistoryId) {
		VisitHistory visitHistory = visitHistoryRepository.findById(visitHistoryId)
			.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.VISIT_HISTORY_NOT_FOUND));

		return visitHistoryConverter.visitHistoryToGetVisitHistoryResponse(visitHistory);
	}

	//특정 사용자의 모든 의료기록 조회
	@Transactional
	public List<DateAndHospital> getUsersAllVisitHistory(Long userId) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.USER_NOT_FOUND));

		List<VisitHistory> visitHistory = visitHistoryRepository.findAllByUserId(userId);
		return visitHistoryConverter.visitHistoryToGetUsersAllVisitHistoryResponse(visitHistory);
	}

	//특정 의료기록 삭제
	@Transactional
	public void deleteVisitHistory(Long visitHistoryId) {
		VisitHistory visitHistory = visitHistoryRepository.findById(visitHistoryId)
			.orElseThrow(
				() -> new OuchException(VisitHistoryErrorCode.VISIT_HISTORY_NOT_FOUND));

		visitHistoryRepository.delete(visitHistory);
	}

	//특정 의료기록 수정
	//VisitHistory entity 클래스에 update 메서드를 추가하는 방식으로 바꿀까요?
	@Transactional
	public VisitHistoryUpdateResponse updateVisitHistory(@Valid VisitHistoryUpdateRequest request,
		Long visitHistoryId) {
		VisitHistory visitHistory = visitHistoryRepository.findById(visitHistoryId)
			.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.VISIT_HISTORY_NOT_FOUND));

		Hospital hospital = hospitalRepository.findByName(request.getVisitingHospital())
			.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.HOSPITAL_NOT_FOUND));

		Department department = departmentRepository.findByName(request.getMedicalSubject()
		).orElseThrow(() -> new OuchException(VisitHistoryErrorCode.DEPARTMENT_NOT_FOUND));

		Summary summary = visitHistory.getSummary();

		VisitHistory updatedVisitHistory = visitHistoryConverter.visitHistoryUpdateRequestToVisitHistory(request,
			visitHistory, hospital, department);

		Summary updatedSummary = visitHistoryConverter.visitHistoryUpdateRequestToSummary(request, updatedVisitHistory,
			summary);

		summary = summaryRepository.save(updatedSummary); // Summary 저장

		visitHistory.assignSummary(summary);

		visitHistoryRepository.save(updatedVisitHistory); // 다시 저장 (연관관계 반영)

		return visitHistoryConverter.visitHistoryToVisitHistoryUpdateResponse(visitHistory);
	}

}
