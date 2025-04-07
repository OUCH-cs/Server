package com.onebridge.ouch.service.selfDiagnosis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.apiPayload.code.error.DiagnosisErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.converter.SelfDiagnosisConverter;
import com.onebridge.ouch.domain.SelfDiagnosis;
import com.onebridge.ouch.domain.Symptom;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.mapping.SelfSymptom;
import com.onebridge.ouch.dto.selfDiagnosis.request.AddSymptomsToDiagnosisRequest;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisCreateRequest;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisUpdateRequest;
import com.onebridge.ouch.dto.selfDiagnosis.response.DiagnosisCreateResponseDetailed;
import com.onebridge.ouch.dto.selfDiagnosis.response.DiagnosisUpdateResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisByUserIdResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetSymptomsOfDiagnosisResponse;
import com.onebridge.ouch.repository.Symptom.SymptomRepository;
import com.onebridge.ouch.repository.selfDiagnosis.SelfDiagnosisRepository;
import com.onebridge.ouch.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SelfDiagnosisService {

	private final SelfDiagnosisRepository selfDiagnosisRepository;
	private final SymptomRepository symptomRepository;
	private final UserRepository userRepository;
	private final SelfDiagnosisConverter selfDiagnosisConverter;

	//자가진단표 생성
	@Transactional
	public DiagnosisCreateResponseDetailed createDiagnosis(DiagnosisCreateRequest request) {

		User user = userRepository.findById(request.getUserId())
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.USER_NOT_FOUND));

		//일단 증상 리스트는 비워둔 채로 SelfDiagnosis 객체 생성
		SelfDiagnosis selfDiagnosis = selfDiagnosisConverter.DiagnosisCreateRequest2SelfDiagnosis(request, user);

		//dto 로 받은 selfSymptom(리스트)의 각 요소가 Symptom table 에 존재하는지 확인
		for (String symptom : request.getSymptoms()) { //(단순 문자열로 된) 리스트를 돌면서

			Symptom foundSymptom = symptomRepository.findByName(symptom) //증상이 Symptom table 에 존재하면
				.orElseThrow(() -> new OuchException(DiagnosisErrorCode.SYMPTOM_NOT_FOUND));

			//SelfSymptom 객체 생성
			SelfSymptom symptom1 = selfDiagnosisConverter.SelfSymptomWithSelfDiagnosis(selfDiagnosis, foundSymptom);

			//SelfDiagnosis 의 selfSymptomList 에 해당 증상 추가
			selfDiagnosis.getSelfSymptomList().add(symptom1);
		}

		//SelfDiagnosis table 에 저장
		selfDiagnosisRepository.save(selfDiagnosis);

		return selfDiagnosisConverter.diagnosis2DiagnosisCreateResponseDetailed(selfDiagnosis);
	}

	//특정 자가진단표 조회
	@Transactional(readOnly = true)
	public GetDiagnosisResponse getDiagnosis(Long diagnosisId) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findById(diagnosisId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		return selfDiagnosisConverter.diagnosis2GetDiagnosisResponse(diagnosis);
	}

	//특정 사용자의 모든 자가진단표 조회
	@Transactional(readOnly = true)
	public List<GetDiagnosisByUserIdResponse> getAllDiagnosisByUserId(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.USER_NOT_FOUND));

		List<SelfDiagnosis> diagnosisList = selfDiagnosisRepository.findAllByUserId(userId);

		List<GetDiagnosisByUserIdResponse> responseList = new ArrayList<>();
		for (SelfDiagnosis diagnosis : diagnosisList) {
			responseList.add(selfDiagnosisConverter.diagnosis2GetDiagnosisByUserIdResponse(diagnosis));
		}

		return responseList;
	}

	//특정 자가진단표 삭제
	@Transactional
	public void deleteDiagnosis(Long diagnosisId) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findById(diagnosisId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		selfDiagnosisRepository.delete(diagnosis);
	}

	//특정 자가진단표의 증상 목록 조회
	@Transactional(readOnly = true)
	public GetSymptomsOfDiagnosisResponse getSymptomsOfDiagnosis(Long diagnosisId) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findById(diagnosisId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		return selfDiagnosisConverter.diagnosis2GetSymptomsOfDiagnosisResponse(diagnosis);
	}

	//자가진단표 수정
	//SelfDiagnosis entity 클래스에 update 메서드를 추가하는 방식으로 바꿀까요?
	@Transactional
	public DiagnosisUpdateResponse updateDiagnosis(Long diagnosisId, Long userId, DiagnosisUpdateRequest request) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findById(diagnosisId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.USER_NOT_FOUND));

		SelfDiagnosis updatedDiagnosis = selfDiagnosisConverter.DiagnosisUpdateRequest2SelfDiagnosis(diagnosis, user,
			request);

		for (String symptom : request.getSymptoms()) { //(단순 문자열로 된) 리스트를 돌면서

			Symptom foundSymptom = symptomRepository.findByName(symptom) //증상이 Symptom table 에 존재하면
				.orElseThrow(() -> new OuchException(DiagnosisErrorCode.SYMPTOM_NOT_FOUND));

			//SelfSymptom 객체 생성
			SelfSymptom symptom1 = selfDiagnosisConverter.SelfSymptomWithSelfDiagnosis(updatedDiagnosis, foundSymptom);

			//SelfDiagnosis 의 selfSymptomList 에 해당 증상 추가
			updatedDiagnosis.getSelfSymptomList().add(symptom1);

		}

		selfDiagnosisRepository.save(updatedDiagnosis);

		return SelfDiagnosisConverter.diagnosis2DiagnosisUpdateResponse(updatedDiagnosis);
	}

	//특정 자가진단표에 증상 추가
	@Transactional
	public void addSymptomsToSelfDiagnosis(Long diagnosisId, AddSymptomsToDiagnosisRequest request) {

		SelfDiagnosis diagnosis = selfDiagnosisRepository.findById(diagnosisId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		for (String symptom : request.getSymptoms()) {

			if (diagnosis.getSelfSymptomList()
				.stream()
				.anyMatch(selfSymptom -> selfSymptom.getSymptom().getName().equals(symptom))) {
				throw new OuchException(DiagnosisErrorCode.SYMPTOM_ALREADY_ADDED);
			}

			Symptom foundSymptom = symptomRepository.findByName(symptom)
				.orElseThrow(() -> new OuchException(DiagnosisErrorCode.SYMPTOM_NOT_FOUND));

			SelfSymptom symptom1 = selfDiagnosisConverter.SelfSymptomWithSelfDiagnosis(diagnosis, foundSymptom);

			diagnosis.getSelfSymptomList().add(symptom1);

		}
	}
}


