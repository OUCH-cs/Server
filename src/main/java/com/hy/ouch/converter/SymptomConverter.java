package com.hy.ouch.converter;

import org.springframework.stereotype.Component;

import com.hy.ouch.domain.Symptom;
import com.hy.ouch.dto.symptom.response.GetSymptomResponse;

@Component
public class SymptomConverter {

	public GetSymptomResponse symptom2GetSymptomsResponse(Symptom symptom) {
		return new GetSymptomResponse(symptom.getId(), symptom.getName());
	}
}
