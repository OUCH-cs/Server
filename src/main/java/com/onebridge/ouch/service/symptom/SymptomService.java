package com.onebridge.ouch.service.symptom;

import java.util.ArrayList;
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

		List<GetSymptomResponse> responseList = new ArrayList<>();
		for (Symptom symptom : symptoms) {
			responseList.add(symptomConverter.symptom2GetSymptomsResponse(symptom));
		}

		return responseList;
	}
}
