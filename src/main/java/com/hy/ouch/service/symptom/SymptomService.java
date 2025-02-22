package com.hy.ouch.service.symptom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.ouch.converter.SymptomConverter;
import com.hy.ouch.domain.Symptom;
import com.hy.ouch.dto.symptom.response.GetSymptomResponse;
import com.hy.ouch.repository.symptom.SymptomRepository;

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
