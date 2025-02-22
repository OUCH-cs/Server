package com.hy.ouch.controller.symptom;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hy.ouch.dto.symptom.response.GetSymptomResponse;
import com.hy.ouch.service.symptom.SymptomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/symptoms")
@RequiredArgsConstructor
public class SymptomController {

	private final SymptomService symptomService;

	//증상 목록 조회
	@GetMapping
	public List<GetSymptomResponse> getSymptomsList() {
		return symptomService.getSymptomsList();
	}
}
