package com.onebridge.ouch.service.medicalRecord;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.apiPayload.code.error.CommonErrorCode;
import com.onebridge.ouch.apiPayload.code.error.MedicalRecordErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.converter.MedicalRecordConverter;
import com.onebridge.ouch.domain.Summary;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.mapping.MedicalRecord;
import com.onebridge.ouch.dto.medicalRecord.request.MedicalRecordCreateRequest;
import com.onebridge.ouch.dto.medicalRecord.request.MedicalRecordUpdateRequest;
import com.onebridge.ouch.dto.medicalRecord.response.DateAndHospital;
import com.onebridge.ouch.dto.medicalRecord.response.GetMedicalRecordResponse;
import com.onebridge.ouch.repository.medicalRecord.MedicalRecordRepository;
import com.onebridge.ouch.repository.summary.SummaryRepository;
import com.onebridge.ouch.repository.user.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

	private final UserRepository userRepository;
	private final MedicalRecordRepository medicalRecordRepository;
	private final MedicalRecordConverter medicalRecordConverter;
	private final SummaryRepository summaryRepository;

	//의료 기록 생성
	@Transactional
	public void createMedicalRecord(MedicalRecordCreateRequest request, Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		// Summary 생성
		Summary summary = Summary.builder()
			.contents_summary(request.getTreatmentSummary())
			.build();

		MedicalRecord medicalRecord = medicalRecordConverter.medicalRecordCreateRequestToMedicalRecord(request, user,
			summary);

		medicalRecordRepository.save(medicalRecord);
	}

	//특정 의료기록 조회
	@Transactional
	public GetMedicalRecordResponse getMedicalRecord(Long medicalRecordId, Long userId) {
		MedicalRecord medicalRecord = medicalRecordRepository.findByIdAndUserId(medicalRecordId, userId)
			.orElseThrow(() -> new OuchException(MedicalRecordErrorCode.MEDICAL_RECORD_NOT_FOUND));

		return medicalRecordConverter.medicalRecordToGetMedicalRecordResponse(medicalRecord);
	}

	//특정 사용자의 모든 의료기록 조회
	@Transactional
	public List<DateAndHospital> getUsersAllMedicalRecord(Long userId) {
		List<MedicalRecord> medicalRecords = medicalRecordRepository.findAllByUserId(userId);
		return medicalRecordConverter.medicalRecordToGetUsersAllMedicalRecordResponse(medicalRecords);
	}

	//특정 의료기록 삭제
	@Transactional
	public void deleteMedicalRecord(Long medicalRecordId, Long userId) {
		MedicalRecord medicalRecord = medicalRecordRepository.findByIdAndUserId(medicalRecordId, userId)
			.orElseThrow(
				() -> new OuchException(MedicalRecordErrorCode.MEDICAL_RECORD_NOT_FOUND));

		medicalRecordRepository.delete(medicalRecord);
	}

	//특정 의료기록 수정
	@Transactional
	public void updateMedicalRecord(@Valid MedicalRecordUpdateRequest request,
		Long medicalRecordId, Long userId) {
		MedicalRecord medicalRecord = medicalRecordRepository.findByIdAndUserId(medicalRecordId, userId)
			.orElseThrow(() -> new OuchException(MedicalRecordErrorCode.MEDICAL_RECORD_NOT_FOUND));

		Summary summary = medicalRecord.getSummary().toBuilder()
			.contents_summary(request.getTreatmentSummary())
			.build();

		MedicalRecord updatedMedicalRecord = medicalRecordConverter.medicalRecordUpdateRequestToMedicalRecord(
			medicalRecord,
			request,
			summary);

		medicalRecordRepository.save(updatedMedicalRecord);
	}

}
