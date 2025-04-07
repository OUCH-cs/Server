package com.onebridge.ouch.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.Symptom;
import com.onebridge.ouch.dto.symptom.response.GetSymptomResponse;

@Component
public class SymptomConverter {

	public GetSymptomResponse symptom2GetSymptomsResponse(Symptom symptom) {
		return new GetSymptomResponse(symptom.getId(), symptom.getName());
	}

	public List<GetSymptomResponse> symptoms2GetSymptomsResponse(List<Symptom> symptoms) {
		return symptoms.stream().map(this::symptom2GetSymptomsResponse).toList();
	}
}
