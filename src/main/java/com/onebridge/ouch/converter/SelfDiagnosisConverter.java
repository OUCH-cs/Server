package com.onebridge.ouch.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.SelfDiagnosis;
import com.onebridge.ouch.domain.Symptom;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.mapping.SelfSymptom;
import com.onebridge.ouch.domain.mapping.compositeKey.DiagnosisSymptomPK;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisCreateRequest;
import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisUpdateRequest;
import com.onebridge.ouch.dto.selfDiagnosis.response.DiagnosisUpdateResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisByUserIdResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetSymptomsOfDiagnosisResponse;

@Component
public class SelfDiagnosisConverter {

	public DiagnosisUpdateResponse diagnosisToDiagnosisUpdateResponse(SelfDiagnosis updatedDiagnosis) {
		List<String> symptoms = SymptomListForResponseDto(updatedDiagnosis);
		return new DiagnosisUpdateResponse(updatedDiagnosis.getId(), updatedDiagnosis.getVisitType(), symptoms,
			updatedDiagnosis.getDuration(), updatedDiagnosis.getPainSeverity(), updatedDiagnosis.getAdditionalNote(),
			updatedDiagnosis.getCreatedAt().toString());
	}

	public GetDiagnosisResponse diagnosisToGetDiagnosisResponse(SelfDiagnosis diagnosis) {
		List<String> symptoms = SymptomListForResponseDto(diagnosis);
		return new GetDiagnosisResponse(diagnosis.getUser().getId(), diagnosis.getVisitType(), symptoms,
			diagnosis.getDuration(),
			diagnosis.getPainSeverity(), diagnosis.getAdditionalNote(), diagnosis.getCreatedAt().toString());
	}

	public GetDiagnosisByUserIdResponse diagnosisToGetDiagnosisByUserIdResponse(SelfDiagnosis diagnosis) {
		List<String> symptoms = SymptomListForResponseDto(diagnosis);
		return new GetDiagnosisByUserIdResponse(diagnosis.getId(), diagnosis.getVisitType(), symptoms,
			diagnosis.getDuration(), diagnosis.getPainSeverity(), diagnosis.getAdditionalNote(),
			diagnosis.getCreatedAt().toString());
	}

	public GetSymptomsOfDiagnosisResponse diagnosisToGetSymptomsOfDiagnosisResponse(SelfDiagnosis diagnosis) {
		List<String> symptoms = SymptomListForResponseDto(diagnosis);
		return new GetSymptomsOfDiagnosisResponse(symptoms);
	}

	public SelfDiagnosis DiagnosisCreateRequestToSelfDiagnosis(DiagnosisCreateRequest request, User user) {
		return SelfDiagnosis.builder()
			.user(user)
			.visitType(request.getVisitType())
			.selfSymptomList(new ArrayList<>())
			.duration(request.getDuration())
			.painSeverity(request.getPainSeverity())
			.additionalNote(request.getAdditionalNote())
			.build();
	}

	public SelfSymptom SelfSymptomWithSelfDiagnosis(SelfDiagnosis selfDiagnosis, Symptom foundSymptom) {
		return SelfSymptom.builder()
			.selfDiagnosis(selfDiagnosis)
			.symptom(foundSymptom)
			.diagnosisSymptomPk(new DiagnosisSymptomPK(selfDiagnosis.getId(),
				foundSymptom.getId()))
			.build();
	}

	public SelfDiagnosis DiagnosisUpdateRequestToSelfDiagnosis(SelfDiagnosis diagnosis, User user,
		DiagnosisUpdateRequest request) {
		return diagnosis.toBuilder()
			.user(user)
			.visitType(request.getVisitType())
			.selfSymptomList(new ArrayList<>())
			.duration(request.getDuration())
			.painSeverity(request.getPainSeverity())
			.additionalNote(request.getAdditionalNote())
			.createdAt(diagnosis.getCreatedAt())
			.build();
	}

	public List<String> SymptomListForResponseDto(SelfDiagnosis selfDiagnosis) {
		List<String> symptoms = new ArrayList<>();
		for (SelfSymptom symptom : selfDiagnosis.getSelfSymptomList()) {
			symptoms.add(symptom.getSymptom().getName());
		}
		return symptoms;
	}
}
