package com.hy.ouch.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hy.ouch.domain.SelfDiagnosis;
import com.hy.ouch.domain.mapping.SelfSymptom;
import com.hy.ouch.dto.selfDiagnosis.response.DiagnosisCreateResponse;
import com.hy.ouch.dto.selfDiagnosis.response.GetDiagnosisByUserIdResponse;
import com.hy.ouch.dto.selfDiagnosis.response.GetDiagnosisResponse;
import com.hy.ouch.dto.selfDiagnosis.response.GetSymptomsOfDiagnosisResponse;

@Component
public class SelfDiagnosisConverter {

	public DiagnosisCreateResponse diagnosis2DiagnosisCreateResponse(SelfDiagnosis diagnosis) {
		List<String> selfSymotimList = new ArrayList<>();
		for (SelfSymptom symptom : diagnosis.getSelfSymptomList()) {
			selfSymotimList.add(symptom.getSymptom().getName());
		}
		return new DiagnosisCreateResponse(diagnosis.getId(), diagnosis.getUser().getId(), diagnosis.getContents(),
			selfSymotimList);
	}

	public GetDiagnosisResponse diagnosis2GetDiagnosisResponse(SelfDiagnosis diagnosis) {
		List<String> selfSymotimList = new ArrayList<>();
		for (SelfSymptom symptom : diagnosis.getSelfSymptomList()) {
			selfSymotimList.add(symptom.getSymptom().getName());
		}
		return new GetDiagnosisResponse(diagnosis.getUser().getId(), diagnosis.getContents(), selfSymotimList);
	}

	public GetDiagnosisByUserIdResponse diagnosis2GetDiagnosisByUserIdResponse(SelfDiagnosis diagnosis) {
		List<String> selfSymotimList = new ArrayList<>();
		for (SelfSymptom symptom : diagnosis.getSelfSymptomList()) {
			selfSymotimList.add(symptom.getSymptom().getName());
		}
		return new GetDiagnosisByUserIdResponse(diagnosis.getId(), diagnosis.getContents(), selfSymotimList);
	}

	public GetSymptomsOfDiagnosisResponse diagnosis2GetSymptomsOfDiagnosisResponse(SelfDiagnosis diagnosis) {
		List<String> symptoms = new ArrayList<>();
		for (SelfSymptom symptom : diagnosis.getSelfSymptomList()) {
			symptoms.add(symptom.getSymptom().getName());
		}
		return new GetSymptomsOfDiagnosisResponse(symptoms);
	}
}
