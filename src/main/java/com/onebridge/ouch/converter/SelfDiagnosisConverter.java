package com.onebridge.ouch.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.SelfDiagnosis;
import com.onebridge.ouch.domain.Symptom;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.mapping.DiagnosisSymptom;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisCreateRequest;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisUpdateRequest;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisByUserIdResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetSymptomsOfDiagnosisResponse;

@Component
public class SelfDiagnosisConverter {

	public GetDiagnosisResponse diagnosisToGetDiagnosisResponse(SelfDiagnosis diagnosis) {
		List<String> symptoms = symptomListForResponseDto(diagnosis);
		return new GetDiagnosisResponse(diagnosis.getUser().getId(), diagnosis.getVisitType(), symptoms,
			diagnosis.getDuration(),
			diagnosis.getPainSeverity(), diagnosis.getAdditionalNote(), diagnosis.getCreatedAt().toString());
	}

	public GetDiagnosisByUserIdResponse diagnosisToGetDiagnosisByUserIdResponse(SelfDiagnosis diagnosis) {
		List<String> symptoms = symptomListForResponseDto(diagnosis);
		return new GetDiagnosisByUserIdResponse(diagnosis.getId(), diagnosis.getVisitType(), symptoms,
			diagnosis.getDuration(), diagnosis.getPainSeverity(), diagnosis.getAdditionalNote(),
			diagnosis.getCreatedAt().toString());
	}

	public GetSymptomsOfDiagnosisResponse diagnosisToGetSymptomsOfDiagnosisResponse(SelfDiagnosis diagnosis) {
		List<String> symptoms = symptomListForResponseDto(diagnosis);
		return new GetSymptomsOfDiagnosisResponse(symptoms);
	}

	public SelfDiagnosis diagnosisCreateRequestToSelfDiagnosis(DiagnosisCreateRequest request, User user) {
		return SelfDiagnosis.builder()
			.user(user)
			.visitType(request.getVisitType())
			.diagnosisSymptomList(new ArrayList<>())
			.duration(request.getDuration())
			.painSeverity(request.getPainSeverity())
			.additionalNote(request.getAdditionalNote())
			.build();
	}

	public DiagnosisSymptom buildDiagnosisSymptom(SelfDiagnosis selfDiagnosis, Symptom foundSymptom) {
		return DiagnosisSymptom.builder()
			.selfDiagnosis(selfDiagnosis)
			.symptom(foundSymptom)
			.build();
	}

	public SelfDiagnosis diagnosisUpdateRequestToSelfDiagnosis(SelfDiagnosis diagnosis, User user,
		DiagnosisUpdateRequest request) {
		return diagnosis.toBuilder()
			.visitType(request.getVisitType())
			.diagnosisSymptomList(new ArrayList<>())
			.duration(request.getDuration())
			.painSeverity(request.getPainSeverity())
			.additionalNote(request.getAdditionalNote())
			.build();
	}

	public List<String> symptomListForResponseDto(SelfDiagnosis selfDiagnosis) {
		List<String> symptoms = new ArrayList<>();
		for (DiagnosisSymptom symptom : selfDiagnosis.getDiagnosisSymptomList()) {
			symptoms.add(symptom.getSymptom().getName());
		}
		return symptoms;
	}
}
