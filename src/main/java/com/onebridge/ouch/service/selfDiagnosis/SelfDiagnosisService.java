package com.onebridge.ouch.service.selfDiagnosis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.apiPayload.code.error.CommonErrorCode;
import com.onebridge.ouch.apiPayload.code.error.DiagnosisErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.converter.SelfDiagnosisConverter;
import com.onebridge.ouch.domain.SelfDiagnosis;
import com.onebridge.ouch.domain.Symptom;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.mapping.DiagnosisSymptom;
import com.onebridge.ouch.dto.selfDiagnosis.request.AddSymptomsToDiagnosisRequest;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisCreateRequest;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisUpdateRequest;
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
	public void createDiagnosis(DiagnosisCreateRequest request, Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		//일단 증상 리스트는 비워둔 채로 SelfDiagnosis 객체 생성
		SelfDiagnosis selfDiagnosis = selfDiagnosisConverter.diagnosisCreateRequestToSelfDiagnosis(request, user);

		List<String> symptomNames = request.getSymptoms();
		List<Symptom> foundSymptoms = symptomRepository.findByNameIn(symptomNames);

		// 이름으로 빠르게 찾기 위해 Map 으로 변환
		Map<String, Symptom> symptomMap = foundSymptoms.stream()
			.collect(Collectors.toMap(Symptom::getName, s -> s));

		for (String symptomName : symptomNames) {
			Symptom foundSymptom = symptomMap.get(symptomName);
			if (foundSymptom == null) {
				throw new OuchException(DiagnosisErrorCode.SYMPTOM_NOT_FOUND);
			}

			DiagnosisSymptom symptom1 = selfDiagnosisConverter.buildDiagnosisSymptom(selfDiagnosis,
				foundSymptom);
			selfDiagnosis.getDiagnosisSymptomList().add(symptom1);
		}

		selfDiagnosisRepository.save(selfDiagnosis);
	}

	//특정 자가진단표 조회
	@Transactional(readOnly = true)
	public GetDiagnosisResponse getDiagnosis(Long diagnosisId, Long userId) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findByIdAndUserId(diagnosisId, userId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		return selfDiagnosisConverter.diagnosisToGetDiagnosisResponse(diagnosis);
	}

	//특정 사용자의 모든 자가진단표 조회
	@Transactional(readOnly = true)
	public List<GetDiagnosisByUserIdResponse> getAllDiagnosisByUserId(Long userId) {
		List<SelfDiagnosis> diagnosisList = selfDiagnosisRepository.findAllByUserId(userId);

		List<GetDiagnosisByUserIdResponse> responseList = new ArrayList<>();
		for (SelfDiagnosis diagnosis : diagnosisList) {
			responseList.add(selfDiagnosisConverter.diagnosisToGetDiagnosisByUserIdResponse(diagnosis));
		}

		return responseList;
	}

	//특정 자가진단표 삭제
	@Transactional
	public void deleteDiagnosis(Long diagnosisId, Long userId) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findByIdAndUserId(diagnosisId, userId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		selfDiagnosisRepository.delete(diagnosis);
	}

	//특정 자가진단표의 증상 목록 조회
	@Transactional(readOnly = true)
	public GetSymptomsOfDiagnosisResponse getSymptomsOfDiagnosis(Long diagnosisId, Long userId) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findByIdAndUserId(diagnosisId, userId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		return selfDiagnosisConverter.diagnosisToGetSymptomsOfDiagnosisResponse(diagnosis);
	}

	//자가진단표 수정
	@Transactional
	public DiagnosisUpdateResponse updateDiagnosis(Long diagnosisId, Long userId, DiagnosisUpdateRequest request) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findByIdAndUserId(diagnosisId, userId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		SelfDiagnosis updatedDiagnosis = selfDiagnosisConverter.diagnosisUpdateRequestToSelfDiagnosis(diagnosis, user,
			request);

		List<String> symptomNames = request.getSymptoms();
		List<Symptom> foundSymptoms = symptomRepository.findByNameIn(symptomNames);

		// 이름으로 빠르게 찾기 위해 Map 으로 변환
		Map<String, Symptom> symptomMap = foundSymptoms.stream()
			.collect(Collectors.toMap(Symptom::getName, s -> s));

		for (String symptomName : symptomNames) {
			Symptom foundSymptom = symptomMap.get(symptomName);
			if (foundSymptom == null) {
				throw new OuchException(DiagnosisErrorCode.SYMPTOM_NOT_FOUND);
			}

			DiagnosisSymptom symptom1 = selfDiagnosisConverter.buildDiagnosisSymptom(updatedDiagnosis,
				foundSymptom);
			updatedDiagnosis.getDiagnosisSymptomList().add(symptom1);
		}

		selfDiagnosisRepository.save(updatedDiagnosis);

		return selfDiagnosisConverter.diagnosisToDiagnosisUpdateResponse(updatedDiagnosis);
	}

	//특정 자가진단표에 증상 추가
	@Transactional
	public void addSymptomsToSelfDiagnosis(Long diagnosisId, AddSymptomsToDiagnosisRequest request, Long userId) {

		SelfDiagnosis diagnosis = selfDiagnosisRepository.findByIdAndUserId(diagnosisId, userId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		for (String symptom : request.getSymptoms()) {

			if (diagnosis.getDiagnosisSymptomList()
				.stream()
				.anyMatch(diagnosisSymptom -> diagnosisSymptom.getSymptom().getName().equals(symptom))) {
				throw new OuchException(DiagnosisErrorCode.SYMPTOM_ALREADY_ADDED);
			}

			Symptom foundSymptom = symptomRepository.findByName(symptom)
				.orElseThrow(() -> new OuchException(DiagnosisErrorCode.SYMPTOM_NOT_FOUND));

			DiagnosisSymptom symptom1 = selfDiagnosisConverter.buildDiagnosisSymptom(diagnosis, foundSymptom);

			diagnosis.getDiagnosisSymptomList().add(symptom1);

		}
	}
}


