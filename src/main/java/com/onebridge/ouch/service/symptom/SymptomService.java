package com.onebridge.ouch.service.symptom;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.converter.SymptomConverter;
import com.onebridge.ouch.domain.Symptom;
import com.onebridge.ouch.dto.symptom.response.GetSymptomResponse;
import com.onebridge.ouch.repository.Symptom.SymptomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SymptomService {

	private final SymptomRepository symptomRepository;
	private final SymptomConverter symptomConverter;

	@Transactional
	public List<GetSymptomResponse> getSymptomsList() {
		List<Symptom> symptoms = symptomRepository.findAll();
		return symptomConverter.symptomsToGetSymptomsResponse(symptoms);
	}
}
