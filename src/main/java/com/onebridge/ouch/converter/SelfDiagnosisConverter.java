package com.onebridge.ouch.converter;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.SelfDiagnosis;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.dto.selfDiagnosis.request.SelfDiagnosisRequest;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisByUserIdResponse;
import com.onebridge.ouch.dto.selfDiagnosis.response.GetDiagnosisResponse;

@Component
public class SelfDiagnosisConverter {

	public GetDiagnosisResponse diagnosisToGetDiagnosisResponse(SelfDiagnosis diagnosis) {
		//List<String> symptoms = symptomListForResponseDto(diagnosis);
		return new GetDiagnosisResponse(diagnosis.getUser().getId(), diagnosis.getVisitType(), diagnosis.getSymptom(),
			diagnosis.getDuration(),
			diagnosis.getPainSeverity(), diagnosis.getAdditionalNote(), diagnosis.getCreatedAt().toString());
	}

	public GetDiagnosisByUserIdResponse diagnosisToGetDiagnosisByUserIdResponse(SelfDiagnosis diagnosis) {
		//List<String> symptoms = symptomListForResponseDto(diagnosis);
		return new GetDiagnosisByUserIdResponse(diagnosis.getId(), diagnosis.getVisitType(), diagnosis.getSymptom(),
			diagnosis.getDuration(), diagnosis.getPainSeverity(), diagnosis.getAdditionalNote(),
			diagnosis.getCreatedAt().toString());
	}

	// public GetSymptomsOfDiagnosisResponse diagnosisToGetSymptomsOfDiagnosisResponse(SelfDiagnosis diagnosis) {
	// 	List<String> symptoms = symptomListForResponseDto(diagnosis);
	// 	return new GetSymptomsOfDiagnosisResponse(symptoms);
	// }

	public SelfDiagnosis diagnosisCreateRequestToSelfDiagnosis(SelfDiagnosisRequest request, User user) {
		return SelfDiagnosis.builder()
			.user(user)
			.visitType(request.getVisitType())
			.symptom(request.getSymptom())
			.duration(request.getDuration())
			.painSeverity(request.getPainSeverity())
			.additionalNote(request.getAdditionalNote())
			.build();
	}

	// public DiagnosisSymptom buildDiagnosisSymptom(SelfDiagnosis selfDiagnosis, Symptom foundSymptom) {
	// 	return DiagnosisSymptom.builder()
	// 		.selfDiagnosis(selfDiagnosis)
	// 		.symptom(foundSymptom)
	// 		.build();
	// }

	// public List<String> symptomListForResponseDto(SelfDiagnosis selfDiagnosis) {
	// 	List<String> symptoms = new ArrayList<>();
	// 	for (DiagnosisSymptom symptom : selfDiagnosis.getDiagnosisSymptomList()) {
	// 		symptoms.add(symptom.getSymptom().getName());
	// 	}
	// 	return symptoms;
	// }
}
