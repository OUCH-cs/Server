package com.onebridge.ouch.service.medicalHistory;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.apiPayload.code.error.CommonErrorCode;
import com.onebridge.ouch.apiPayload.code.error.MedicalHistoryErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.converter.MedicalHistoryConverter;
import com.onebridge.ouch.domain.MedicalHistory;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.dto.medicalHistory.request.MedicalHistoryCreateRequest;
import com.onebridge.ouch.dto.medicalHistory.request.MedicalHistoryUpdateRequest;
import com.onebridge.ouch.dto.medicalHistory.response.DateAndDisease;
import com.onebridge.ouch.dto.medicalHistory.response.GetMedicalHistoryResponse;
import com.onebridge.ouch.repository.medicalHistory.MedicalHistoryRepository;
import com.onebridge.ouch.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalHistoryService {

	private final UserRepository userRepository;
	private final MedicalHistoryConverter medicalHistoryConverter;
	private final MedicalHistoryRepository medicalHistoryRepository;

	//건강상태 생성
	@Transactional
	public void createMedicalHistory(MedicalHistoryCreateRequest request, Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		MedicalHistory medicalHistory = medicalHistoryConverter.medicalHistoryCreateRequestToMedicalHistory(request,
			user);

		medicalHistoryRepository.save(medicalHistory);
	}

	//특정 건강상태 조회
	@Transactional
	public GetMedicalHistoryResponse getMedicalHistory(Long medicalHistoryId) {
		MedicalHistory medicalHistory = medicalHistoryRepository.findById(medicalHistoryId)
			.orElseThrow(() -> new OuchException(MedicalHistoryErrorCode.MEDICAL_HISTORY_NOT_FOUND));
		return medicalHistoryConverter.medicalHistoryToGetMedicalHistoryResponse(medicalHistory);
	}

	//특정 사용자의 모든 건강상태 조회
	@Transactional
	public List<DateAndDisease> getUsersAllMedicalHistory(Long userId) {
		List<MedicalHistory> medicalHistory = medicalHistoryRepository.findAllByUserId(userId);
		return medicalHistoryConverter.medicalHistoryToGetUsersAllMedicalHistoryResponse(medicalHistory);
	}

	//특정 건강상태 수정
	@Transactional
	public void updateMedicalHistory(MedicalHistoryUpdateRequest request,
		Long medicalHistoryId) {
		MedicalHistory medicalHistory = medicalHistoryRepository.findById(medicalHistoryId)
			.orElseThrow(() -> new OuchException(MedicalHistoryErrorCode.MEDICAL_HISTORY_NOT_FOUND));

		MedicalHistory updatedMedicalHistory = medicalHistoryConverter
			.medicalHistoryUpdateRequestToMedicalHistory(medicalHistory, request);

		medicalHistoryRepository.save(updatedMedicalHistory);
	}

	//특정 건강상태 삭제
	@Transactional
	public void deleteMedicalHistory(Long medicalHistoryId) {
		MedicalHistory medicalHistory = medicalHistoryRepository.findById(medicalHistoryId)
			.orElseThrow(() -> new OuchException(MedicalHistoryErrorCode.MEDICAL_HISTORY_NOT_FOUND));

		medicalHistoryRepository.delete(medicalHistory);
	}
}
