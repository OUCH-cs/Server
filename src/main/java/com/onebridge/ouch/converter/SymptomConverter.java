package com.onebridge.ouch.converter;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.Symptom;
import com.onebridge.ouch.dto.symptom.response.GetSymptomResponse;

@Component
public class SymptomConverter {

	public GetSymptomResponse symptom2GetSymptomsResponse(Symptom symptom) {
		return new GetSymptomResponse(symptom.getId(), symptom.getName());
	}
}
