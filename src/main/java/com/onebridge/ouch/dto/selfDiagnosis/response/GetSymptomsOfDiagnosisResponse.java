package com.onebridge.ouch.dto.selfDiagnosis.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetSymptomsOfDiagnosisResponse {

	private List<String> symptoms;
}
