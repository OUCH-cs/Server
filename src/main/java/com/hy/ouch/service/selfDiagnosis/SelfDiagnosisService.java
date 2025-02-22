package com.hy.ouch.service.selfDiagnosis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.hy.ouch.apiPayload.code.error.DiagnosisErrorCode;
import com.hy.ouch.apiPayload.exception.OuchException;
import com.hy.ouch.converter.SelfDiagnosisConverter;
import com.hy.ouch.domain.SelfDiagnosis;
import com.hy.ouch.domain.Symptom;
import com.hy.ouch.domain.User;
import com.hy.ouch.domain.mapping.SelfSymptom;
import com.hy.ouch.domain.mapping.compositeKey.DiagnosisSymptomPK;
import com.hy.ouch.dto.selfDiagnosis.request.AddSymptomsToDiagnosisRequest;
import com.hy.ouch.dto.selfDiagnosis.request.DiagnosisCreateRequest;
import com.hy.ouch.dto.selfDiagnosis.request.DiagnosisUpdateRequest;
import com.hy.ouch.dto.selfDiagnosis.response.DiagnosisCreateResponse;
import com.hy.ouch.dto.selfDiagnosis.response.GetDiagnosisByUserIdResponse;
import com.hy.ouch.dto.selfDiagnosis.response.GetDiagnosisResponse;
import com.hy.ouch.dto.selfDiagnosis.response.GetSymptomsOfDiagnosisResponse;
import com.hy.ouch.repository.selfDiagnosis.SelfDiagnosisRepository;
import com.hy.ouch.repository.symptom.SymptomRepository;
import com.hy.ouch.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SelfDiagnosisService {

	private final SelfDiagnosisRepository selfDiagnosisRepository;
	private final SymptomRepository symptomRepository;
	private final UserRepository userRepository;
	private final SelfDiagnosisConverter selfDiagnosisConverter;
	private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	@Transactional
	public DiagnosisCreateResponse createDiagnosis(DiagnosisCreateRequest request) {

		//일단 증상 리스트는 비워둔 채로 SelfDiagnosis 객체 생성
		SelfDiagnosis selfDiagnosis = SelfDiagnosis.builder()
			.user(userRepository.findById(request.getUserId())
				.orElseThrow(() -> new OuchException(DiagnosisErrorCode.USER_NOT_FOUND)))
			.contents(request.getContents())
			.selfSymptomList(new ArrayList<>())
			.build();

		//dto 로 받은 selfSymptom(리스트)의 각 요소가 Symptom table 에 존재하는지 확인
		for (String symptom : request.getSelfSymptoms()) { //(단순 문자열로 된) 리스트를 돌면서

			Symptom foundSymptom = symptomRepository.findByName(symptom) //증상이 Symptom table 에 존재하면
				.orElseThrow(() -> new OuchException(DiagnosisErrorCode.SYMPTOM_NOT_FOUND));

			//Symptom id 찾아두기
			Long symptomId = foundSymptom.getId();

			//SelfSymptom 객체 생성
			SelfSymptom symptom1 = SelfSymptom.builder()
				.selfDiagnosis(selfDiagnosis)
				.symptom(foundSymptom)
				.diagnosisSymptomPk(new DiagnosisSymptomPK(selfDiagnosis.getId(),
					symptomId))
				.build();

			//SelfDiagnosis 의 selfSymptomList 에 해당 증상 추가
			selfDiagnosis.getSelfSymptomList().add(symptom1);
		}

		//SelfDiagnosis table 에 저장
		selfDiagnosisRepository.save(selfDiagnosis);

		return selfDiagnosisConverter.diagnosis2DiagnosisCreateResponse(selfDiagnosis);
	}

	@Transactional(readOnly = true)
	public GetDiagnosisResponse getDiagnosis(Long diagnosisId) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findById(diagnosisId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		return selfDiagnosisConverter.diagnosis2GetDiagnosisResponse(diagnosis);
	}

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

	@Transactional
	public void deleteDiagnosis(Long diagnosisId) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findById(diagnosisId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		selfDiagnosisRepository.delete(diagnosis);
	}

	@Transactional(readOnly = true)
	public GetSymptomsOfDiagnosisResponse getSymptomsOfDiagnosis(Long diagnosisId) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findById(diagnosisId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		return selfDiagnosisConverter.diagnosis2GetSymptomsOfDiagnosisResponse(diagnosis);
	}

	@Transactional //setter 를 사용하지 않고 toBuilder 를 사용하여 업데이트(조금 깁니다..)
	//SelfDiagnosis entity 클래스에 update 메서드를 추가하는 방식으로 하면 더 짧아지는데 그렇게 할까요?
	public void updateDiagnosis(Long diagnosisId, DiagnosisUpdateRequest request) {
		SelfDiagnosis diagnosis = selfDiagnosisRepository.findById(diagnosisId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		SelfDiagnosis updatedDiagnosis = diagnosis.toBuilder()
			.user(userRepository.findById(request.getUserId())
				.orElseThrow(() -> new OuchException(DiagnosisErrorCode.USER_NOT_FOUND)))
			.contents(request.getContents())
			.selfSymptomList(new ArrayList<>())
			.build();

		for (String symptom : request.getSelfSymptoms()) { //(단순 문자열로 된) 리스트를 돌면서

			Symptom foundSymptom = symptomRepository.findByName(symptom) //증상이 Symptom table 에 존재하면
				.orElseThrow(() -> new OuchException(DiagnosisErrorCode.SYMPTOM_NOT_FOUND));

			//Symptom id 찾아두기
			Long symptomId = foundSymptom.getId();

			//SelfSymptom 객체 생성
			SelfSymptom symptom1 = SelfSymptom.builder()
				.selfDiagnosis(updatedDiagnosis)
				.symptom(foundSymptom)
				.diagnosisSymptomPk(new DiagnosisSymptomPK(updatedDiagnosis.getId(),
					symptomId))
				.build();

			//SelfDiagnosis 의 selfSymptomList 에 해당 증상 추가
			updatedDiagnosis.getSelfSymptomList().add(symptom1);

		}

		selfDiagnosisRepository.save(updatedDiagnosis);
	}

	@Transactional
	public void addSymptomsToSelfDiagnosis(Long diagnosisId, AddSymptomsToDiagnosisRequest request) {

		SelfDiagnosis diagnosis = selfDiagnosisRepository.findById(diagnosisId)
			.orElseThrow(() -> new OuchException(DiagnosisErrorCode.DIAGNOSIS_NOT_FOUND));

		for (String symptom : request.getSymptoms()) {

			Symptom foundSymptom = symptomRepository.findByName(symptom)
				.orElseThrow(() -> new OuchException(DiagnosisErrorCode.SYMPTOM_NOT_FOUND));

			Long symptomId = foundSymptom.getId();

			SelfSymptom symptom1 = SelfSymptom.builder()
				.selfDiagnosis(diagnosis)
				.symptom(foundSymptom)
				.diagnosisSymptomPk(new DiagnosisSymptomPK(diagnosis.getId(),
					symptomId))
				.build();

			diagnosis.getSelfSymptomList().add(symptom1);

		}
	}
}
